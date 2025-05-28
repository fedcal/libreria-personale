package com.bibliotecapersonale.config;

import com.bibliotecapersonale.model.entity.Libro;
import com.bibliotecapersonale.model.entity.PdfMetadata;
import com.bibliotecapersonale.model.enums.TipoFileEnum;
import com.bibliotecapersonale.repository.LibroRepository;
import com.bibliotecapersonale.repository.PdfMetadataRepository;
import com.bibliotecapersonale.utils.DateParserUtil;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class PdfBatchConfig {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final PdfMetadataRepository metadataRepository;
  private final LibroRepository libroRepository;

  @Value("${libreriapersonale.folder}")
  private String rootFolder;

  @Bean(name = "scanAndSavePdfMetaJob")
  public Job scanAndSavePdfMetaJob() {
    return new JobBuilder("scanAndSavePdfMetaJob", jobRepository)
            .start(scanAndSaveStep())
            .build();
  }

  @Bean
  public Step scanAndSaveStep() {
    return new StepBuilder("scanAndSaveStep", jobRepository)
            .tasklet(scanAndSaveTasklet(), transactionManager)
            .build();
  }

  @Bean
  public Tasklet scanAndSaveTasklet() {
    return (contribution, chunkContext) -> {
      List<File> pdfFiles = new ArrayList<>();
      scanFolderRecursively(new File(rootFolder), pdfFiles);

      for (File file : pdfFiles) {
        try (PDDocument document = PDDocument.load(file)) {
          PDDocumentInformation info = document.getDocumentInformation();

          String path = file.getAbsolutePath();

          if (metadataRepository.existsByFilePath(path)) {
            continue;
          }

          Calendar creationCal = info.getCreationDate();
          Calendar modificationCal = info.getModificationDate();

          Date creationDateRaw = (creationCal != null) ? creationCal.getTime() : null;
          Date modificationDateRaw = (modificationCal != null) ? modificationCal.getTime() : null;

          Optional<Instant> creationDate = Optional.empty();
          Optional<Instant> modificationDate = Optional.empty();

          if (creationDateRaw != null && creationDateRaw.getTime() != 0) {
            creationDate = DateParserUtil.parseToInstant(creationDateRaw.toString());
          } else {
            creationDate = DateParserUtil.parseToInstant(Instant.now()
                    .toString());
          }

          if (modificationDateRaw != null && modificationDateRaw.getTime() != 0) {
            modificationDate = DateParserUtil.parseToInstant(modificationDateRaw.toString());
          } else {
            modificationDate = DateParserUtil.parseToInstant(Instant.now()
                    .toString());
          }

          PdfMetadata metadata = PdfMetadata.builder()
                  .author(info.getAuthor())
                  .title(path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.')))
                  .subject(info.getSubject())
                  .creator(info.getCreator())
                  .producer(info.getProducer())
                  .creationDate(creationDate.isPresent() ? creationDate.get() : null)
                  .modificationDate(modificationDate.isPresent() ? modificationDate.get() : null)
                  .filePath(path)
                  .tipoFile(TipoFileEnum.fromExtension(path.substring(path.lastIndexOf('.') + 1)))
                  .build();

          PdfMetadata savedMetadata = metadataRepository.save(metadata);
          libroRepository.save(
                  new Libro(null, path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.')),
                          savedMetadata));

        } catch (IOException e) {
          System.err.println("Errore nel leggere il file PDF: " + file.getName());
          e.printStackTrace();
        }
      }

      return RepeatStatus.FINISHED;
    };
  }

  private void scanFolderRecursively(File folder, List<File> pdfFiles) {
    File[] files = folder.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          scanFolderRecursively(file, pdfFiles);
        } else if (file.isFile() && file.getName()
                .toLowerCase()
                .endsWith(".pdf")) {
          pdfFiles.add(file);
        }
      }
    }
  }
}

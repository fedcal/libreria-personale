package com.bibliotecapersonale.config;

import com.bibliotecapersonale.model.entity.Libro;
import com.bibliotecapersonale.model.entity.PdfMetadata;
import com.bibliotecapersonale.repository.LibroRepository;
import com.bibliotecapersonale.repository.PdfMetadataRepository;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

          if (metadataRepository.existsByFilePath(path)) continue;

          PdfMetadata metadata = PdfMetadata.builder()
                  .author(info.getAuthor())
                  .title(info.getTitle())
                  .subject(info.getSubject())
                  .creator(info.getCreator())
                  .producer(info.getProducer())
                  .creationDate(info.getCreationDate() != null ? info.getCreationDate().getTime().toString() : null)
                  .modificationDate(info.getModificationDate() != null ? info.getModificationDate().getTime().toString() : null)
                  .filePath(path)
                  .build();

          PdfMetadata savedMetadata = metadataRepository.save(metadata);
          libroRepository.save(new Libro(null, savedMetadata));

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
        } else if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
          pdfFiles.add(file);
        }
      }
    }
  }
}

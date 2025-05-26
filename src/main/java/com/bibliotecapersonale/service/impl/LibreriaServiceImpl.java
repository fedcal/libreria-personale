package com.bibliotecapersonale.service.impl;

import com.bibliotecapersonale.model.dto.GenericResponse;
import com.bibliotecapersonale.model.dto.MetadatiPdfDto;
import com.bibliotecapersonale.service.LibreriaService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LibreriaServiceImpl implements LibreriaService {

  @Value("${libreriapersonale.folder}")
  private String folder;

  @Override
  public GenericResponse<List<MetadatiPdfDto>> inizializzaAggiornaLibreria() {
    File rootFolder = new File(folder);
    List<MetadatiPdfDto> listaMetadati = new ArrayList<>();

    if (rootFolder.exists() && rootFolder.isDirectory()) {
      scanFolder(rootFolder,listaMetadati);
    } else {
      System.out.println("Percorso non valido o non è una cartella");
    }

    return GenericResponse.<List<MetadatiPdfDto>>builder()
            .message("Aggiorna Libreria")
            .data(listaMetadati)
            .build();
  }

  private void scanFolder(File rootFolder, List<MetadatiPdfDto> listaMetadati) {
    File[] filesAndDirs = rootFolder.listFiles();

    if (filesAndDirs != null) {
      for (File file : filesAndDirs) {
        if (file.isDirectory()) {
          // Chiamata ricorsiva per la sottocartella
          scanFolder(file, listaMetadati);
        } else if (file.isFile() && file.getName()
                .toLowerCase()
                .endsWith(".pdf")) {
          try (PDDocument documento = PDDocument.load(file)) {
            PDDocumentInformation info = documento.getDocumentInformation();
            listaMetadati.add(MetadatiPdfDto.builder()
                    .nomeFile(file.getName())
                    .info(info)
                    .pathDocumento(file.getAbsolutePath())
                    .build());
          } catch (IOException e) {
            System.err.println("Errore nel leggere il file PDF: " + file.getName());
            e.printStackTrace();
          }
        }
      }
    }
  }
}

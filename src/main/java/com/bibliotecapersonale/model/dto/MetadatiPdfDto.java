package com.bibliotecapersonale.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetadatiPdfDto {

  private String nomeFile;
  private PDDocumentInformation info;
  private String pathDocumento;

  @Override
  public String toString() {
    return "File: " + nomeFile + "\n" +
            "Titolo: " + info.getTitle() + "\n" +
            "Autore: " + info.getAuthor() + "\n" +
            "Soggetto: " + info.getSubject() + "\n" +
            "Parole chiave: " + info.getKeywords() + "\n" +
            "Creato il: " + info.getCreationDate() + "\n" +
            "Modificato il: " + info.getModificationDate() + "\n" +
            "Path: " + pathDocumento;
  }

}

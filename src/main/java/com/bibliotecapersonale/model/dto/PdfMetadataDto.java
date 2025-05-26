package com.bibliotecapersonale.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfMetadataDto {
  private Long idMetadata;
  private String author;
  private String title;
  private String subject;
  private String creator;
  private String producer;
  private String creationDate;
  private String modificationDate;
  private String filePath;
  private LibroDto libro;

}

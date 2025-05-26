package com.bibliotecapersonale.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdf_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_metadata")
  private Long idMetadata;

  @Column(name = "autore")
  private String author;

  @Column(name = "titolo")
  private String title;

  @Column(name = "soggetto")
  private String subject;

  @Column(name = "creatore")
  private String creator;

  @Column(name = "produttore")
  private String producer;

  @Column(name = "data_creazione")
  private String creationDate;

  @Column(name = "data_modifica")
  private String modificationDate;

  @Column(name = "percorso_file")
  private String filePath;

  @OneToOne(mappedBy = "pdfMetadata")
  private Libro libro;

}

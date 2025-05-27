package com.bibliotecapersonale.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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

  @Column(name = "autore", columnDefinition = "VARCHAR(500)")
  private String author;

  @Column(name = "titolo", columnDefinition = "VARCHAR(500)")
  private String title;

  @Column(name = "soggetto", columnDefinition = "VARCHAR(5000)")
  private String subject;

  @Column(name = "creatore", columnDefinition = "VARCHAR(500)")
  private String creator;

  @Column(name = "produttore", columnDefinition = "VARCHAR(500)")
  private String producer;

  @Column(name = "data_creazione")
  private Instant creationDate;

  @Column(name = "data_modifica")
  private Instant modificationDate;

  @Column(name = "percorso_file", columnDefinition = "VARCHAR(500)")
  private String filePath;

  @OneToOne(mappedBy = "pdfMetadata")
  private Libro libro;

}

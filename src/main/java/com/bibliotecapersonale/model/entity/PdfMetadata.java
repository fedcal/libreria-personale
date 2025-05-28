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

  @Column(name = "autore", columnDefinition = "TEXT")
  private String author;

  @Column(name = "titolo", columnDefinition = "TEXT")
  private String title;

  @Column(name = "soggetto", columnDefinition = "TEXT")
  private String subject;

  @Column(name = "creatore", columnDefinition = "TEXT")
  private String creator;

  @Column(name = "produttore", columnDefinition = "TEXT")
  private String producer;

  @Column(name = "data_creazione")
  private Instant creationDate;

  @Column(name = "data_modifica")
  private Instant modificationDate;

  @Column(name = "percorso_file", columnDefinition = "TEXT")
  private String filePath;

  @OneToOne(mappedBy = "pdfMetadata")
  private Libro libro;

}

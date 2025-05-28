package com.bibliotecapersonale.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "libro")
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_libro")
  private Long idLibro;

  @Column(name = "titolo", columnDefinition = "TEXT")
  private String title;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_metadata", referencedColumnName = "id_metadata")
  private PdfMetadata pdfMetadata;
}

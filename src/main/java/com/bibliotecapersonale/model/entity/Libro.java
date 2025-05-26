package com.bibliotecapersonale.model.entity;

import jakarta.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_metadata", referencedColumnName = "id_metadata")
    private PdfMetadata pdfMetadata;
}

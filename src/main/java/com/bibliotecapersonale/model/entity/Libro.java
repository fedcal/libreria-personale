package com.bibliotecapersonale.model.entity;

import jakarta.persistence.*;

@Entity(name = "libro")
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long idLibro;
}

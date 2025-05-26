package com.bibliotecapersonale.repository;

import com.bibliotecapersonale.model.entity.PdfMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfMetadataRepository extends JpaRepository<PdfMetadata, Long> {
  boolean existsByFilePath(String filePath);
}

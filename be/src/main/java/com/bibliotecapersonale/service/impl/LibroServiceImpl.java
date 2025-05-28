package com.bibliotecapersonale.service.impl;

import com.bibliotecapersonale.model.dto.GenericResponse;
import com.bibliotecapersonale.service.LibroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LibroServiceImpl implements LibroService {

  @Value("${libreriapersonale.folder}")
  private String folder;

  @Override
  public GenericResponse<String> getPathLibreria() {
    return GenericResponse.<String>builder()
            .codeMessage("1")
            .message("OK")
            .data(folder)
            .build();
  }
}

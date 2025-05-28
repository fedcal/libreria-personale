package com.bibliotecapersonale.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GenericResponse<T> {

  private String message;
  private String codeMessage;
  private T data;
}

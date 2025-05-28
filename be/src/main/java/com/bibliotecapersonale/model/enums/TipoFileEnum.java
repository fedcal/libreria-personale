package com.bibliotecapersonale.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoFileEnum {

  EPUB("epub"), //tra i più utilizzati
  PDF("pdf"), //tra i più utilizzati
  MOBI("mobi"), //tra i più utilizzati
  AZW("azw"),
  AZW3("azw3"),
  KFX("kfx"),
  FB2("fb2"),
  DJVU("djvu"), //tra i più utilizzati
  CBR("cbr"),
  CBZ("cbz"),
  TXT("txt"),
  RTF("rtf"),
  DOC("DOC"),
  DOCX("docx");

  private final String fieldName;

  public static TipoFileEnum fromExtension(String extension) {
    for (TipoFileEnum tipo : values()) {
      if (tipo.fieldName.equalsIgnoreCase(extension)) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("Estensione file non supportata: " + extension);
  }
}

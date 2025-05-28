package com.bibliotecapersonale.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DateParserUtil {

  // Lista di formati di date di input da provare (aggiungi altri se serve)
  private static final List<DateTimeFormatter> DATE_FORMATTERS = new ArrayList<>();

  // Formatter per output in formato italiano e fuso orario Europe/Rome
  private static final DateTimeFormatter ITALIAN_DATE_FORMATTER = DateTimeFormatter
          .ofPattern("dd/MM/yyyy HH:mm:ss")
          .withLocale(Locale.ITALIAN)
          .withZone(ZoneId.of("Europe/Rome"));

  static {
    // Formati possibili di input - aggiungi/modifica secondo necessità
    DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH)); // Tue Sep 29 19:42:16 CEST 2020
    DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss",
            Locale.ITALIAN)); // 29/09/2020 19:42:16
    DATE_FORMATTERS.add(DateTimeFormatter.ISO_INSTANT); // 2020-09-29T17:42:16Z
    DATE_FORMATTERS.add(DateTimeFormatter.ISO_OFFSET_DATE_TIME); // 2020-09-29T19:42:16+02:00
    // Aggiungi altri formati comuni che ti servono
  }

  /**
   * Prova a convertire una stringa di data in Instant. Supporta diversi formati configurati.
   */
  public static Optional<Instant> parseToInstant(String dateStr) {
    if (dateStr == null || dateStr.isBlank()) {
      return Optional.empty();
    }

    for (DateTimeFormatter formatter : DATE_FORMATTERS) {
      try {
        // Prova a fare il parsing in ZonedDateTime, poi converte in Instant
        ZonedDateTime zdt = ZonedDateTime.parse(dateStr, formatter);
        return Optional.of(zdt.toInstant());
      } catch (DateTimeParseException e1) {
        // Provo con LocalDateTime + ZoneId Europe/Rome se parsing in ZonedDateTime fallisce
        try {
          return Optional.of(
                  java.time.LocalDateTime.parse(dateStr, formatter)
                          .atZone(ZoneId.of("Europe/Rome"))
                          .toInstant());
        } catch (DateTimeParseException e2) {
          // Continua con il prossimo formatter
        }
      }
    }
    // Nessun formato ha funzionato
    return Optional.empty();
  }

  /**
   * Format Instant in stringa "dd/MM/yyyy HH:mm:ss" italiano, fuso Europe/Rome
   */
  public static String formatInstantItalian(Instant instant) {
    if (instant == null) {
      return null;
    }
    return ITALIAN_DATE_FORMATTER.format(instant);
  }
}

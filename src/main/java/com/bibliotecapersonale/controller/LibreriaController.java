package com.bibliotecapersonale.controller;

import com.bibliotecapersonale.model.dto.GenericResponse;
import com.bibliotecapersonale.model.dto.MetadatiPdfDto;
import com.bibliotecapersonale.service.LibreriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libreria")
@AllArgsConstructor
@Tag(name = "Controller Libreria",
        description = "Operazioni base sulla libreria")
public class LibreriaController {

  private final LibreriaService libreriaService;

  @Operation(summary = "Inizializza o aggiorna libreria",
          description = "Path per inizializzare o aggiornare la libreria")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operazione andata a buon fine"),
          @ApiResponse(responseCode = "500", description = "Errore di sistema")
  })
  @GetMapping(value = "/inizializza-aggiorna", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenericResponse<List<MetadatiPdfDto>>> inizializzaAggiornaLibreria() {
    return ResponseEntity.ok(libreriaService.inizializzaAggiornaLibreria());
  }
}

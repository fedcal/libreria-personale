package com.bibliotecapersonale.controller;

import com.bibliotecapersonale.model.dto.GenericResponse;
import com.bibliotecapersonale.service.LibroService;
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

@RestController
@RequestMapping("/libro")
@AllArgsConstructor
@Tag(name = "Bot Alimentazione Chat Controller",
        description = "Gestione della chat")
public class LibroController {

  private final LibroService libroService;

  @Operation(summary = "Chat normale",
          description = "Invio di un messaggio al bot Alimentazione sfruttando l'LLM non addestrato")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operazione andata a buon fine"),
          @ApiResponse(responseCode = "500", description = "Errore di sistema")
  })
  @GetMapping(value = "/path-libreria", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenericResponse<String>> getPathLibreria() {
    return ResponseEntity.ok(libroService.getPathLibreria());
  }


}

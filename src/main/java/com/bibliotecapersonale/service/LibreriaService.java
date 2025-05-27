package com.bibliotecapersonale.service;

import com.bibliotecapersonale.model.dto.GenericResponse;
import com.bibliotecapersonale.model.dto.MetadatiPdfDto;

import java.util.List;

public interface LibreriaService {
    GenericResponse<List<MetadatiPdfDto>> inizializzaAggiornaLibreria();

}

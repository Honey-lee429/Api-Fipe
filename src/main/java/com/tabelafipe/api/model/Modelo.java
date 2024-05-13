package com.tabelafipe.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelo(
        List<Dados> modelos
) {
}

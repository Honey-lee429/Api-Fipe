package com.tabelafipe.api.port;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> clazz);

    <T> List<T> obterLista(String json, Class<T> clazz) throws JsonProcessingException;

}

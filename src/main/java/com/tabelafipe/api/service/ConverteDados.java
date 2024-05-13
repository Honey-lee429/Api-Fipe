package com.tabelafipe.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.tabelafipe.api.port.IConverteDados;

import java.util.List;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> clazz) throws JsonProcessingException {
        CollectionType collectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
        return mapper.readValue(json, collectionType);
    }

}

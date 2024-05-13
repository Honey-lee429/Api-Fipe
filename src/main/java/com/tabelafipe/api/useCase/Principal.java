package com.tabelafipe.api.useCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tabelafipe.api.model.Automovel;
import com.tabelafipe.api.model.Dados;
import com.tabelafipe.api.model.Modelo;
import com.tabelafipe.api.service.ConsumoApi;
import com.tabelafipe.api.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados converteDados = new ConverteDados();

    Scanner scanner = new Scanner(System.in);

    public void runPrincipal() throws JsonProcessingException {
        System.out.println("Esolha uma opcao:" +
                "1 - Carro," +
                "2 - Moto," +
                "3 - Caminhao");

        var auto = scanner.nextInt();
        String url = switch (auto) {
            case 1 -> URL_BASE + "carros/marcas";
            case 2 -> URL_BASE + "motos/marcas";
            case 3 -> URL_BASE + "caminhoes/marcas";
            default -> URL_BASE + "carros";
        };

        var json = consumoApi.obterDadosApi(url);
        System.out.println(json);

        System.out.println("Escolha o codigo do modelo:");
        var marca = scanner.next();
        var endereco = url + "/" + marca + "/modelos";
        json = consumoApi.obterDadosApi(endereco);
        var brand = converteDados.obterDados(json, Modelo.class); // não precisa usar o obterLista, pois o record Modelo já é uma representação de lista
        brand.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo)) // compara os dados e ordena por ordem do codigo
                .forEach(System.out::println);

        scanner.nextLine();

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = scanner.nextLine();
        List<Dados> modelosFiltrados = brand.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase())).toList();
        modelosFiltrados.forEach(System.out::println);


        System.out.println("Digite o codigo do modelo");
        var modeloCodigo = scanner.nextLine();
        endereco = endereco + "/" + modeloCodigo + "/anos";
        json = consumoApi.obterDadosApi(endereco);
        List<Dados> ano = converteDados.obterLista(json, Dados.class); // [Dados[codigo=32000-1, nome=32000 Gasolina], Dados[codigo=2023-1, nome=2023 Gasolina]]
        List<Automovel> tabela = new ArrayList<>();

        for (int i = 0; i < ano.size(); i++) {
            var enderecoAnos = endereco + "/" + ano.get(i).codigo();
            json = consumoApi.obterDadosApi(enderecoAnos);
            var automovel = converteDados.obterDados(json, Automovel.class);
            tabela.add(automovel);

        }

        tabela.forEach(System.out::println);

    }
}

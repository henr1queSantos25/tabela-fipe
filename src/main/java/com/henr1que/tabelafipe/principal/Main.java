package com.henr1que.tabelafipe.principal;

import com.henr1que.tabelafipe.model.DadosItem;
import com.henr1que.tabelafipe.model.Dados;
import com.henr1que.tabelafipe.model.Veiculo;
import com.henr1que.tabelafipe.service.ConsumoAPI;
import com.henr1que.tabelafipe.service.ConverteDados;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private String leitura;


    private final String ENDERECO_API = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("\nBem-vindo ao Tabela Fipe!");

        System.out.println("\n### OPÇÕES ###:");
        System.out.println("1 - CARRO\n2 - MOTO\n3 - CAMINHÃO");
        System.out.println("ESCOLHA UMA DAS OPÇÕES ACIMA:");

        var opcao = scanner.nextInt();
        String link;

        switch (opcao) {
            case 1 -> link = ENDERECO_API + "carros/marcas";
            case 2 -> link = ENDERECO_API + "motos/marcas";
            case 3 -> link = ENDERECO_API + "caminhoes/marcas";
            default -> {
                System.out.println("Opção inválida. Tente novamente.");
                link = "";
                exibeMenu();
            }
        }

        String json = consumo.obterDados(link);

        var marcas = conversor.converterDados(json, DadosItem[].class);

        System.out.println("\n### MARCAS DISPONÍVEIS ###:");
        Arrays.stream(marcas).forEach(m -> {
            System.out.println(STR."Código: \{m.codigo()} - Nome: \{m.nome()}");
        });

        System.out.println("\nDigite o código da marca desejada:");
        opcao = scanner.nextInt();

        link = link + "/" + opcao + "/modelos";
        json = consumo.obterDados(link);

        var modelos = conversor.converterDados(json, Dados.class);

        System.out.println("\n### MODELOS DISPONÍVEIS ###:");
        modelos.modelos().forEach(m -> {
            System.out.println("Código: " + m.codigo() + " - Nome: " + m.nome());
        });

        System.out.println("\nDigite um trecho do nome do veículo para consulta:");
        leitura = scanner.next();

        List<DadosItem> veiculosFiltrados = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(leitura.toLowerCase()))
                .collect(Collectors.toList());

        if (veiculosFiltrados.isEmpty()) {
            System.out.println("Nenhum veículo encontrado com o termo: " + leitura);
            return;
        } else {
            System.out.println("\n### VEÍCULOS ENCONTRADOS ###:");
            veiculosFiltrados.forEach(v -> {
                System.out.println("Código: " + v.codigo() + " - Nome: " + v.nome());
            });
        }

        System.out.println("\nDigite o código do veículo para consultar os valores:");
        opcao = scanner.nextInt();

        link = link + "/" + opcao + "/anos";
        json = consumo.obterDados(link);

        var anos = conversor.converterDados(json, DadosItem[].class);

        System.out.println("\n### RESULTADOS POR ANO ###");
        System.out.println("=".repeat(80));


        for (DadosItem m : anos) {
            json = consumo.obterDados(link + "/" + m.codigo());
            var veiculo = conversor.converterDados(json, Veiculo.class);

            System.out.println("┌" + "─".repeat(80) + "┐");
            System.out.printf("│ %-20s: %-56s │%n", "Modelo", veiculo.modelo());
            System.out.printf("│ %-20s: %-56d │%n", "Ano", veiculo.anoModelo());
            System.out.printf("│ %-20s: %-56s │%n", "Valor", veiculo.valor());
            System.out.printf("│ %-20s: %-56s │%n", "Combustível", veiculo.combustivel());
            System.out.printf("│ %-20s: %-56s │%n", "Código FIPE", veiculo.codigoFipe());
            System.out.println("└" + "─".repeat(80) + "┘");
            System.out.println();
        }

        System.out.println("=".repeat(80));


    }


}

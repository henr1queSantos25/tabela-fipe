package com.henr1que.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonAlias("Valor") String valor,
                      @JsonAlias("Modelo") String modelo,
                      @JsonAlias("AnoModelo") Integer anoModelo,
                      @JsonAlias("Combustivel") String combustivel,
                      @JsonAlias("CodigoFipe") String codigoFipe) {
}

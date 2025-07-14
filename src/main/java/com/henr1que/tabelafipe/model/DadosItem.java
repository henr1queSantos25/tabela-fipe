package com.henr1que.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosItem(@JsonAlias("nome") String nome,
                        @JsonAlias("codigo") String codigo){
}

package com.henr1que.tabelafipe.service;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}

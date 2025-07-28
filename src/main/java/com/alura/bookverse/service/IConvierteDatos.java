package com.alura.bookverse.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

package com.alura.bookverse.model;

public enum Idioma {
    INGLES("en", "Inglés"),
    ESPANOL("es", "Español"),
    FRANCES("fr", "Francés"),
    ALEMAN("de", "Alemán"),
    ITALIANO("it", "Italiano");

    private String categoriaAPI;
    private String categoria;

    Idioma(String categoriaAPI, String categoria) {
        this.categoriaAPI = categoriaAPI;
        this.categoria = categoria;
    }

    public static Idioma fromCategoriaAPI(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.categoriaAPI.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Código de idioma no encontrado: " + codigo);
    }

    public static Idioma fromCategoria(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.categoria.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Código de idioma no encontrado: " + codigo);
    }

}

package com.api.fleche.enums;

public enum Genero {
    CISGÊNERO("CISGÊNERO"),
    TRANSGÊNERO("TRANSGÊNERO"),
    HOMOSSEXUAL("HOMOSSEXUAL"),
    NÃO_BINÁRIO("NÃO BINÁRIO"),
    NEUTRO("NETRUO"),
    OUTRO("OUTRO");

    private String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}

package com.api.fleche.enums;

public enum Genero {
    HETEROSSEXUAL("HETEROSSEXUAL"),
    TRANSSEXUAL("TRANSSEXUAL"),
    HOMOSSEXUAL("HOMOSSEXUAL"),
    BISSEXUAL("BISSEXUAL");

    private String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}

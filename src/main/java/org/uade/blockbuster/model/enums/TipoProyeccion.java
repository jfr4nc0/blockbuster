package org.uade.blockbuster.model.enums;

public enum TipoProyeccion {
    DOS_D("2D"),
    TRES_D("3D"),
    TRES_D_MAX("3DMAX"),
    CUATRO_D("4D"),
    CINCO_D("5D");

    private String tipo;

    private TipoProyeccion(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}

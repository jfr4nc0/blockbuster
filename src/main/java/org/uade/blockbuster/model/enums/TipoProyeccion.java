package org.uade.blockbuster.model.enums;

import java.util.List;

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

    public static List<String> getAllProyecciones() {
        return List.of(
                DOS_D.getTipo(),
                TRES_D.getTipo(),
                CUATRO_D.getTipo(),
                CINCO_D.getTipo()
        );
    }
}

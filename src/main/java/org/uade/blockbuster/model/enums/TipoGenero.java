package org.uade.blockbuster.model.enums;

import java.util.List;

public enum TipoGenero {
    DRAMA,
    ROMANCE,
    TERROR,
    BIOGRAFIA,
    SUSPENSO;

    public static List<String> getAllGeneros() {
        return List.of(
                DRAMA.name(),
                ROMANCE.name(),
                TERROR.name(),
                BIOGRAFIA.name(),
                SUSPENSO.name()
        );
    }
}

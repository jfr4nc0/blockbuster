package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Descuento;

import java.util.ArrayList;
import java.util.Collection;

public class DescuentoController {
    private static volatile DescuentoController INSTANCE;
    private Collection<Descuento> descuentos;

    private DescuentoController() {
        this.descuentos = new ArrayList<Descuento>();
    }

    public static DescuentoController getInstance() {
        DescuentoController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (DescuentoController.class) {
            if (INSTANCE == null) {
                INSTANCE = new DescuentoController();
            }
            return INSTANCE;
        }
    }
}

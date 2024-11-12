package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Venta;

import java.util.ArrayList;
import java.util.Collection;

public class VentasController {
    private static volatile VentasController INSTANCE;
    private Collection<Venta> ventas;

    private VentasController() {
        this.ventas = new ArrayList<Venta>();
    }

    public static VentasController getInstance() {
        VentasController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (VentasController.class) {
            if (INSTANCE == null) {
                INSTANCE = new VentasController();
            }
            return INSTANCE;
        }
    }



}

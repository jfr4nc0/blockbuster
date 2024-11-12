package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Sucursal;

import java.util.ArrayList;
import java.util.Collection;

public class SucursalController {
    private static volatile SucursalController INSTANCE;
    private Collection<Sucursal> sucursales;

    private SucursalController() {
        this.sucursales = new ArrayList<Sucursal>();
    }

    public static SucursalController getInstance() {
        SucursalController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (SucursalController.class) {
            if (INSTANCE == null) {
                INSTANCE = new SucursalController();
            }
            return INSTANCE;
        }
    }
}

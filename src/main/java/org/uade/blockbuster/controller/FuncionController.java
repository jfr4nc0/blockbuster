package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Funcion;

import java.util.ArrayList;
import java.util.Collection;

public class FuncionController {
    private static volatile FuncionController INSTANCE;
    private Collection<Funcion> funciones;

    private FuncionController() {
        this.funciones = new ArrayList<Funcion>();
    }

    public static FuncionController getInstance() {
        FuncionController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (FuncionController.class) {
            if (INSTANCE == null) {
                INSTANCE = new FuncionController();
            }
            return INSTANCE;
        }
    }
}

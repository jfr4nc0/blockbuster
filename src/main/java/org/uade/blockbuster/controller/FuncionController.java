package org.uade.blockbuster.controller;

import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Funcion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

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

    public Funcion buscarFuncionById(int funcionId) throws NotFoundException {
        return buscarFuncion(funcion -> funcion.getFuncionId() == funcionId)
                .orElseThrow(() -> new NotFoundException("No existe una funcion con el id: " + funcionId));
    }

    private Optional<Funcion> buscarFuncion(Predicate<Funcion> predicate) {
        return funciones.stream()
                .filter(predicate)
                .findFirst();
    }
}

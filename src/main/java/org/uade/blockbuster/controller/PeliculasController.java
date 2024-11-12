package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Pelicula;

import java.util.ArrayList;
import java.util.Collection;

public class PeliculasController {
    private static volatile PeliculasController INSTANCE;
    private Collection<Pelicula> peliculas;

    private PeliculasController() {
        this.peliculas = new ArrayList<Pelicula>();
    }

    public static PeliculasController getInstance() {
        PeliculasController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (PeliculasController.class) {
            if (INSTANCE == null) {
                INSTANCE = new PeliculasController();
            }
            return INSTANCE;
        }
    }
}

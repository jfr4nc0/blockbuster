package org.uade.blockbuster.controller;

import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Pelicula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

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

    public Pelicula buscarPeliculaById(int peliculaId) throws NotFoundException {
        return buscarPelicula(pelicula -> pelicula.getPeliculaId() == peliculaId)
                .orElseThrow(() -> new NotFoundException("No se encontro un pelicula con el id " + peliculaId));
    }

    private Optional<Pelicula> buscarPelicula(Predicate<Pelicula> predicate) {
        return peliculas.stream().filter(predicate).findFirst();
    }
}

package org.uade.blockbuster.controller;

import lombok.SneakyThrows;
import org.uade.blockbuster.controller.dto.PeliculaDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.enums.TipoGenero;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public void agregarPelicula(PeliculaDto peliculaDto) {
        validarNuevaPelicula(peliculaDto);


    }

    private void validarNuevaPelicula(PeliculaDto peliculaDto) {
        if (Objects.isNull(peliculaDto.getNombrePelicula())) throw new IllegalArgumentException("El nombre pelicula no puede ser nulo");
        if (Objects.isNull(peliculaDto.getDirector())) throw new IllegalArgumentException("El director no puede ser nulo");
        if (Objects.isNull(peliculaDto.getGenero())) throw new IllegalArgumentException("El genero no puede ser nulo");
        if (existePelicula(peliculaDto.getNombrePelicula(), peliculaDto.getDirector(), peliculaDto.getGenero())) throw new IllegalArgumentException("El pelicula ya existe");
    }

    public boolean existePelicula(String titulo, String director, String genero) {
        return peliculas.stream()
                .anyMatch(pelicula -> pelicula.getNombrePelicula().equals(titulo) &&
                        pelicula.getDirector().equals(director) &&
                        pelicula.getGenero().equals(TipoGenero.valueOf(genero)));
    }

    public Pelicula buscarPeliculaById(int peliculaId) throws NotFoundException {
        return buscarPelicula(pelicula -> pelicula.getPeliculaId() == peliculaId)
                .orElseThrow(() -> new NotFoundException("No se encontro un pelicula con el id " + peliculaId));
    }

    private Optional<Pelicula> buscarPelicula(Predicate<Pelicula> predicate) {
        return peliculas.stream().filter(predicate).findFirst();
    }
}

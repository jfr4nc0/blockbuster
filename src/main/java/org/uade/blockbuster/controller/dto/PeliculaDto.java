package org.uade.blockbuster.controller.dto;

import java.util.Collection;

public class PeliculaDto {
    private int peliculaId;
    private String genero;
    private String nombrePelicula;
    private int duracionEnMinutos;
    private String director;
    private Collection<String> actores;
    private String tipoProyeccion;

    public PeliculaDto(int peliculaId, String genero, String nombrePelicula, int duracionEnMinutos, String director, Collection<String> actores, String tipoProyeccion) {
        this.peliculaId = peliculaId;
        this.genero = genero;
        this.nombrePelicula = nombrePelicula;
        this.duracionEnMinutos = duracionEnMinutos;
        this.director = director;
        this.actores = actores;
        this.tipoProyeccion = tipoProyeccion;
    }

    public PeliculaDto(String genero, String nombrePelicula, int duracionEnMinutos, String director, Collection<String> actores, String tipoProyeccion) {
        this.genero = genero;
        this.nombrePelicula = nombrePelicula;
        this.duracionEnMinutos = duracionEnMinutos;
        this.director = director;
        this.actores = actores;
        this.tipoProyeccion = tipoProyeccion;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public String getDirector() {
        return director;
    }

    public Collection<String> getActores() {
        return actores;
    }

    public String getTipoProyeccion() {
        return tipoProyeccion;
    }

    public int getPeliculaId() {
        return peliculaId;
    }
}

package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoGenero;
import org.uade.blockbuster.model.enums.TipoProyeccion;

import java.util.Collection;

public class Pelicula {
    private int peliculaId;
    private TipoGenero genero;
    private String nombrePelicula;
    private int duracionEnMinutos;
    private String director;
    private Collection<String> actores;
    private TipoProyeccion tipoProyeccion;
    private CondicionesDescuento condicionesDescuento;

    public TipoGenero getGenero() {
        return genero;
    }

    public void setGenero(TipoGenero genero) {
        this.genero = genero;
    }

    public CondicionesDescuento getCondicionesDescuento() {
        return condicionesDescuento;
    }

    public int getPeliculaId() {
        return peliculaId;
    }
}

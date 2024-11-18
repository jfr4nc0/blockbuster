package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.uade.blockbuster.model.enums.TipoGenero;
import org.uade.blockbuster.model.enums.TipoProyeccion;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class Pelicula {
    private int peliculaId;
    private TipoGenero genero;
    private String nombrePelicula;
    private int duracionEnMinutos;
    private String director;
    private Collection<String> actores;
    private TipoProyeccion tipoProyeccion;
}

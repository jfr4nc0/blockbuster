package org.uade.blockbuster.model;

import java.util.Date;
import java.util.List;

public class Funcion {
    private int funcionId;
    private Pelicula pelicula;
    private String horario;
    private Date fecha;
    private List<Entrada> entradas;
    private Sala sala;

    public Pelicula getPelicula() {
        return pelicula;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public int getSalaId() {
        //TODO
        return 0;
    }

    public int getSucursalId() {
        //TODO
        return 0;
    }

    public int getPeliculaId() {
        //TODO
        return 0;
    }

    public int getCantidadDeAsientosDisponibles() {
        //TODO
        return 0;
    }

    public int getFuncionId() {
        //TODO
        return 0;
    }

    public Date getFecha() {
        return fecha;
    }

    public Double calcularMontoPorEntradaDeLaPelicula() {
        //TODO
        return 0.00;
    }


}

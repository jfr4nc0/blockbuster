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

    public String getHorario() {
        return horario;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public int getSalaId() {
        return 0;
    }

    public int getSucursalId() {
        return 0;
    }

    public int getPeliculaId() {
        return pelicula.getPeliculaId();
    }

    public int getCantidadDeAsientosDisponibles() {
        return sala.getAsientos() - entradas.stream().mapToInt(Entrada::getNroAsiento).sum();
    }

    public int getFuncionId() {
        return funcionId;
    }

    public Date getFecha() {
        return fecha;
    }

    public Double calcularMontoPorEntradaDeLaPelicula() {
        //TODO
        return 0.00;
    }


}

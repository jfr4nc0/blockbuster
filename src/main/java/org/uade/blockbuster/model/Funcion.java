package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
public class Funcion {
    private int funcionId;
    private Pelicula pelicula;
    private LocalTime horario;
    private LocalDate fecha;
    private Set<Entrada> entradas;
    private Sala sala;

    public Pelicula getPelicula() {
        return pelicula;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Set<Entrada> getEntradas() {
        return entradas;
    }

    public int getSalaId() {
        return sala.getSalaId();
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

    public LocalDate getFecha() {
        return fecha;
    }

    public double calcularMontoPorEntradaDeLaPelicula() {
        return entradas.stream().mapToDouble(Entrada::getPrecio).findFirst().getAsDouble();
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "funcionId=" + funcionId +
                ", pelicula=" + pelicula +
                ", horario='" + horario + '\'' +
                ", fecha=" + fecha +
                ", entradas=" + entradas +
                ", sala=" + sala +
                '}';
    }
}

package org.uade.blockbuster.controller;

import org.uade.blockbuster.controller.dto.EntradaDto;
import org.uade.blockbuster.controller.dto.FuncionDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.Funcion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public FuncionDto toDto(Funcion funcion) {
        return new FuncionDto(
                funcion.getFuncionId(),
                funcion.getPelicula().getNombrePelicula(),
                funcion.getFecha().toString(),
                funcion.getHorario(),
                funcion.getSala().getSalaId());
    }

    public EntradaDto toDto(Entrada entrada) {
        return new EntradaDto(
                entrada.getPrecio(),
                entrada.getNroAsiento()
        );
    }

    public List<EntradaDto> toDtos(List<Entrada> entradas) {
        return entradas.stream()
                .map(this::toDto)
                .toList();
    }
}

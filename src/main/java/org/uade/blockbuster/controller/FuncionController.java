package org.uade.blockbuster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.controller.dto.EntradaDto;
import org.uade.blockbuster.controller.dto.FuncionDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.Funcion;
import org.uade.blockbuster.model.enums.TipoGenero;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FuncionController {
    private static volatile FuncionController INSTANCE;
    private Collection<Funcion> funciones;

    private static final Logger log = LoggerFactory.getLogger(FuncionController.class);

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

    public int obtenerAsientosDisponiblesPorFuncion(int funcionId) {
        return funciones.stream()
                .filter(funcion -> funcion.getFuncionId() == funcionId)
                .mapToInt(Funcion::getCantidadDeAsientosDisponibles)
                .sum();
    }

    public List<FuncionDto> getListaFunciones(LocalDate date) {
        return funciones.stream()
                .map(funcion -> {
                    try {
                        return this.toDto(funcion);
                    } catch (NotFoundException e) {
                        log.error("Error al mappear to FunctionDto functionId: {}, error: ", funcion.getFuncionId(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public int peliculaMasVista() {
        return funciones.stream()
                .collect(Collectors.groupingBy(Funcion::getPeliculaId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public List<Funcion> buscarFuncionesPorPelicula(int peliculaId) {
        return funciones.stream()
                .filter(funcion -> funcion.getPeliculaId() == peliculaId)
                .toList();
    }

    public List<Funcion> buscarFuncionesPorGeneroDePelicula(TipoGenero genero) {
        return funciones.stream()
                .filter(funcion -> {
                    try {
                        return PeliculasController.getInstance().buscarPeliculaById(funcion.getPeliculaId()).getGenero().equals(genero);
                    } catch (NotFoundException e) {
                        log.error("No se encontro una pelicula con el genero: {}, error: ", genero, e.getMessage());
                        return false;
                    }
                })
                .toList();
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

    public FuncionDto toDto(Funcion funcion) throws NotFoundException {
        return new FuncionDto(
                funcion.getFuncionId(),
                PeliculasController.getInstance().buscarPeliculaById(funcion.getPeliculaId()).getNombrePelicula(),
                funcion.getFecha().toString(),
                funcion.getHorario(),
                funcion.getSalaId());
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

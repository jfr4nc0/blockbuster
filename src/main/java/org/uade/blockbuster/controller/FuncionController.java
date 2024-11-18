package org.uade.blockbuster.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.controller.dto.EntradaDto;
import org.uade.blockbuster.controller.dto.FuncionDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.Funcion;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.Sala;
import org.uade.blockbuster.model.enums.TipoGenero;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class FuncionController {
    private static volatile FuncionController INSTANCE;
    private Collection<Funcion> funciones;

    private FuncionController() {
        this.funciones = new ArrayList<Funcion>();
        
        cargaInicial();
    }

    private void cargaInicial() {
        try {
            agregarFuncion(1, LocalTime.now(), LocalDate.now(), 2000, 1, 1);
            agregarFuncion(2, LocalTime.now(), LocalDate.now(), 2500, 1, 2);
            agregarFuncion(2, LocalTime.now(), LocalDate.now(), 3000, 2, 1);
            agregarFuncion(3, LocalTime.now(), LocalDate.now(), 2200, 1, 3);
            agregarFuncion(3, LocalTime.now(), LocalDate.now(), 2800, 2, 2);
        } catch (NotFoundException e) {
            log.error("Error al cargar funciones: " + e.getMessage());
        }
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

    public int agregarFuncion(FuncionDto funcionDto) throws NotFoundException {
        return agregarFuncion(
                funcionDto.getPeliculaId(),
                funcionDto.getHorario(),
                funcionDto.getFecha(),
                funcionDto.getPrecioEntrada(),
                funcionDto.getSucursalId(),
                funcionDto.getSalaId()
        );
    }

    public int agregarFuncion(int peliculaId, LocalTime horario, LocalDate fecha, double precioEntrada, int sucursalId, int salaId) throws NotFoundException {
        validarNuevaFuncion(peliculaId, horario, fecha, precioEntrada, sucursalId, salaId);

        Sala sala = SucursalController.getInstance().buscarSalaPorSalaId(sucursalId, salaId);
        Pelicula pelicula = PeliculasController.getInstance().buscarPeliculaById(peliculaId);
        Set<Entrada> entradas = crearEntradas(sala.getAsientos(), precioEntrada);

        Funcion funcion = new Funcion(calcularFuncionId(), pelicula, horario, fecha, entradas, sala);
        entradas.stream()
                .forEach(entrada -> entrada.setFuncion(funcion));

        funciones.add(funcion);
        log.info("Se agrego la funcion: ", funcion);

        return funcion.getFuncionId();
    }

    private void validarNuevaFuncion(int peliculaId, LocalTime horario, LocalDate fecha, double precioEntrada, int sucursalId, int salaId) throws NotFoundException {
        if (Objects.isNull(peliculaId)) throw new IllegalArgumentException("El peliculaId no puede ser nulo");
        if (Objects.isNull(horario)) throw new IllegalArgumentException("El horario no puede ser nulo");
        if (Objects.isNull(fecha)) throw new IllegalArgumentException("La fecha no puede ser nulo");
        if (Objects.isNull(precioEntrada)) throw new IllegalArgumentException("El precio de entrada no puede ser nulo");
        if (Objects.isNull(sucursalId)) throw new IllegalArgumentException("El salaId no puede ser nulo");
        if (Objects.isNull(salaId)) throw new IllegalArgumentException("El salaId no puede ser nulo");

        Sala sala = SucursalController.getInstance().buscarSalaPorSalaId(sucursalId, salaId);
        List<Funcion> funcionesExistentesMismaFecha = buscarFuncionesBySalaId(sala.getSucursalId(), sala.getSalaId()).stream()
                .filter(funcionSub -> funcionSub.getFecha().isEqual(funcionSub.getFecha()))
                .toList();

        Pelicula peliculaPorAgendar = PeliculasController.getInstance().buscarPeliculaById(peliculaId);

        if (Objects.nonNull(funcionesExistentesMismaFecha) && !funcionesExistentesMismaFecha.isEmpty()) {
            List<LocalTime> horariosFinFuncionAgendadas = funcionesExistentesMismaFecha.stream()
                    .map(funcionSub -> getHorarioFinDeFuncion(funcionSub))
                    .collect(Collectors.toList());

            LocalTime horarioFinFuncionPorAgendar = horario.plusMinutes(peliculaPorAgendar.getDuracionEnMinutos() + 15);

            boolean horarioDisponible = horariosFinFuncionAgendadas.stream()
                    .noneMatch(horarioFin ->
                            horarioFin.isAfter(horario) &&
                            horarioFin.isBefore(horarioFinFuncionPorAgendar)
                    );

            if (!horarioDisponible) throw new IllegalArgumentException("El horario no esta disponible");
        }
    }

    private static LocalTime getHorarioFinDeFuncion(Funcion funcion) {
        return funcion.getHorario().plusMinutes(funcion.getPelicula().getDuracionEnMinutos() + 15);
    }

    private int calcularFuncionId() {
        return funciones.size() + 1;
    }

    private Set<Entrada> crearEntradas(int asientos, double precioEntrada) {
        Set<Entrada> entradas = new HashSet<>();
        for (int i = 0; i < asientos; i++) {
            Entrada entrada = new Entrada(i, precioEntrada);
            entradas.add(entrada);
        }
        return entradas;
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

    public boolean existeFuncionById(int funcionId){
        return existeFuncion(funcion -> funcion.getFuncionId() == funcionId);
    }

    public boolean existeFuncionByPeliculaId(int peliculaId){
        return existeFuncion(funcion -> funcion.getPeliculaId() == peliculaId);
    }

    public boolean existeFuncion(Predicate<Funcion> predicate){
        return funciones.stream().anyMatch(predicate);
    }

    public Funcion buscarFuncionById(int funcionId) throws NotFoundException {
        return buscarFuncion(funcion -> funcion.getFuncionId() == funcionId)
                .orElseThrow(() -> new NotFoundException("No existe una funcion con el id: " + funcionId));
    }

    public List<Funcion> buscarFuncionesBySalaId(int sucursalId, int salaId) {
        return funciones.stream()
                .filter(funcion -> funcion.getSucursalId() == sucursalId && funcion.getSalaId() == salaId)
                .toList();
    }

    private Optional<Funcion> buscarFuncion(Predicate<Funcion> predicate) {
        return funciones.stream()
                .filter(predicate)
                .findFirst();
    }

    public FuncionDto toDto(Funcion funcion) throws NotFoundException {
        Pelicula pelicula = PeliculasController.getInstance().buscarPeliculaById(funcion.getPeliculaId());
        return new FuncionDto(
                funcion.getFuncionId(),
                pelicula.getPeliculaId(),
                pelicula.getNombrePelicula(),
                funcion.getFecha(),
                funcion.getHorario(),
                funcion.getSucursalId(),
                funcion.getSalaId(),
                funcion.getEntradas().stream().map(Entrada::getPrecio).findFirst().get());
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

    public Entrada toModel(EntradaDto entradaDto, int funcionId) throws NotFoundException {
        Funcion funcion = buscarFuncionById(funcionId);
        return new Entrada(entradaDto.getNroAsiento(), entradaDto.getPrecio(), funcion);
    }
}

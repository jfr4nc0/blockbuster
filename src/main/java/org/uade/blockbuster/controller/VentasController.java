package org.uade.blockbuster.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.controller.dto.PeliculaDto;
import org.uade.blockbuster.controller.dto.RecaudacionPorPeliculaDto;
import org.uade.blockbuster.controller.dto.TarjetaDescuentoDto;
import org.uade.blockbuster.controller.dto.VentaDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.Funcion;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.TarjetaDescuento;
import org.uade.blockbuster.model.Venta;
import org.uade.blockbuster.model.enums.TipoGenero;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.util.*;
import java.util.stream.Collectors;

public class VentasController {
    private static volatile VentasController INSTANCE;
    private Collection<Venta> ventas;

    private static final Logger log = LoggerFactory.getLogger(VentasController.class);

    private VentasController() {
        this.ventas = new ArrayList<Venta>();
    }

    public static VentasController getInstance() {
        VentasController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (VentasController.class) {
            if (INSTANCE == null) {
                INSTANCE = new VentasController();
            }
            return INSTANCE;
        }
    }

    public void agregarVenta() {
        //TODO
    }

    public void modificarVenta() {
        //TODO
    }

    public void eliminarVenta() {
        //TODO
    }

    public Collection<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(Collection<Venta> ventas) {
        this.ventas = ventas;
    }

    public double recaudacionPorFuncion(int funcionId) throws NotFoundException {
        Funcion funcion = FuncionController.getInstance().buscarFuncionById(funcionId);

        double totalRecaudadoPorVenta = ventas.stream()
                .filter(venta -> venta.getFuncion().equals(funcion))
                .mapToDouble(venta -> DescuentoController.getInstance().procesadorDeDescuentosParaVenta(venta))
                .sum();

        return totalRecaudadoPorVenta;
    }

    public double recaudacionPorPelicula(int peliculaId) throws NotFoundException {
        Pelicula pelicula = PeliculasController.getInstance().buscarPeliculaById(peliculaId);

        double totalRecaudadoPorPelicula = ventas.stream()
                .filter(venta -> venta.getFuncion().getPeliculaId() == pelicula.getPeliculaId())
                .mapToDouble(venta -> DescuentoController.getInstance().procesadorDeDescuentosParaVenta(venta))
                .sum();

        return totalRecaudadoPorPelicula;
    }

    public double recaudacionPorTarjetaDescuento(TipoTarjeta tipoTarjeta) throws NotFoundException {
        double totalRecaudadoPorTarjetaDescuento = ventas.stream()
                .filter(venta -> venta.getTarjetaDescuento().equals(tipoTarjeta))
                .mapToDouble(venta -> DescuentoController.getInstance().procesadorDeDescuentosParaVenta(venta))
                .sum();

        return totalRecaudadoPorTarjetaDescuento;
    }

    public int comboMasVendido() {
        return ventas.stream()
                .map(venta -> CombosController.getInstance().getCombosByCombosId(venta.getListaComboId()))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Combo::getComboId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public Venta buscarVentaPorFuncion(Funcion funcion) throws NotFoundException {
        return ventas.stream()
                .filter(venta -> venta.getFuncion().equals(funcion))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No se encontro una venta para la funcionId: " + funcion.getFuncionId()));
    }

    public Collection<VentaDto> funcionesVendidasPorGenero(TipoGenero tipoGenero) {
        return ventas.stream()
                .filter(venta -> {
                    try {
                        return PeliculasController.getInstance().buscarPeliculaById(venta.getFuncion().getPeliculaId()).getGenero().equals(tipoGenero);
                    } catch (NotFoundException e) {
                        log.error("Not found pelicula for id: {}", venta.getFuncion().getPeliculaId());
                        return false;
                    }
                })
                .map(venta -> {
                    try {
                        return this.toDto(venta);
                    } catch (NotFoundException e) {
                        log.error("Cannot map to VentaDto, venta: {}", venta);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public int diaDeLaSemanaConMenorVentas() {
        return ventas.stream()
                .collect(Collectors.groupingBy(venta -> venta.getFechaVenta().getDayOfWeek().getValue(), Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public VentaDto toDto(Venta venta) throws NotFoundException {
        return new VentaDto(
                venta.getVentaId(),
                venta.getFechaVenta().toString(),
                CombosController.getInstance().getCombosDtoByCombosId(venta.getListaComboId()),
                FuncionController.getInstance().toDto(venta.getFuncion()),
                toDto(venta.getTarjetaDescuento()),
                FuncionController.getInstance().toDtos(venta.getEntradas())
                );
    }

    public TarjetaDescuentoDto toDto(TarjetaDescuento tarjetaDescuento) {
        return new TarjetaDescuentoDto(
                tarjetaDescuento.getTarjetaId(),
                tarjetaDescuento.getTipoTarjeta().toString());
    }

    @SneakyThrows
    public List<RecaudacionPorPeliculaDto> getPeliculasConMayorRecaudacion() {
        return ventas.stream()
                .map(Venta::getPeliculaId)
                .distinct()
                .map(this::getRecaudacionPorPelicula)
                .sorted(Comparator.comparingDouble(RecaudacionPorPeliculaDto::getRecaudacionTotal).reversed())
                .toList();
    }

    private RecaudacionPorPeliculaDto getRecaudacionPorPelicula(int peliculaId) throws NotFoundException {
        double recaudacionTotal = this.recaudacionPorPelicula(peliculaId);
        PeliculaDto peliculaDto = PeliculasController.getInstance().buscarPeliculaDtoById(peliculaId);

        return new RecaudacionPorPeliculaDto(peliculaDto, recaudacionTotal);
    }
}
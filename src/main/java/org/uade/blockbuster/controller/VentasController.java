package org.uade.blockbuster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.controller.dto.ComboDto;
import org.uade.blockbuster.controller.dto.PeliculaDto;
import org.uade.blockbuster.controller.dto.RecaudacionPorPeliculaDto;
import org.uade.blockbuster.controller.dto.TarjetaDescuentoDto;
import org.uade.blockbuster.controller.dto.VentaDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.Funcion;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.TarjetaDescuento;
import org.uade.blockbuster.model.Venta;
import org.uade.blockbuster.model.enums.TipoGenero;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class VentasController {
    private static volatile VentasController INSTANCE;
    private Collection<Venta> ventas;

    private static final Logger log = LoggerFactory.getLogger(VentasController.class);

    private VentasController() {
        this.ventas = new ArrayList<Venta>();

        cargaInicial();
    }

    private void cargaInicial() {
        try {
            agregarVenta(LocalDate.now(), List.of(1, 2), 1, new TarjetaDescuento(54, TipoTarjeta.LA_NACION),
                    List.of(new Entrada(1),new Entrada(2)));
            agregarVenta(LocalDate.now(), List.of(), 1, null,
                    List.of(new Entrada(3),new Entrada(4),new Entrada(5)));
            agregarVenta(LocalDate.now(), List.of(), 2, new TarjetaDescuento(23, TipoTarjeta.CLARIN_365),
                    List.of(new Entrada(1)));
            agregarVenta(LocalDate.now(), List.of(1), 2, null,
                    List.of(new Entrada(2),new Entrada(3)));
            agregarVenta(LocalDate.now(), List.of(2), 3, null,
                    List.of(new Entrada(1),new Entrada(2),new Entrada(3)));
            agregarVenta(LocalDate.now(), List.of(), 4, new TarjetaDescuento(66, TipoTarjeta.UADE),
                    List.of(new Entrada(1),new Entrada(2)));
            agregarVenta(LocalDate.now(), List.of(1), 4, null,
                    List.of(new Entrada(1),new Entrada(2),new Entrada(3)));
        } catch (NotFoundException e) {
            log.error("Error al agregar la venta: " + e.getMessage());
        }
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

    public int agregarVenta(VentaDto ventaDto) throws NotFoundException {
        return agregarVenta(
                ventaDto.getFechaVenta(),
                ventaDto.getCombos().stream().map(ComboDto::getComboId).toList(),
                ventaDto.getFuncion().getFuncionId(),
                DescuentoController.toModel(ventaDto.getTarjetaDescuento()),
                ventaDto.getEntradas().stream().map(entradaDto -> {
                    try {
                        return FuncionController.getInstance().toModel(entradaDto, ventaDto.getFuncion().getFuncionId());
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).toList()
        );
    }

    public int agregarVenta(LocalDate fechaVenta, List<Integer> combosId, int funcionId, TarjetaDescuento tarjetaDescuento, List<Entrada> entradas) throws NotFoundException {
        Funcion funcion = FuncionController.getInstance().buscarFuncionById(funcionId);
        validarNuevaVentaParamsObligatorios(fechaVenta, funcion.getFuncionId(), entradas);

        List<Combo> combos = CombosController.getInstance().getCombosByCombosId(combosId);

        int ventaId = ventas.size() + 1;
        Venta venta = new Venta(ventaId, fechaVenta, combos, funcion, tarjetaDescuento, entradas);

        ventas.add(venta);
        log.info("Se agrego la venta con el id: " + venta.getVentaId());

        return venta.getVentaId();
    }

    private void validarNuevaVentaParamsObligatorios(LocalDate fechaVenta, int funcionId, List<Entrada> entradas) {
        if (Objects.isNull(fechaVenta)) throw new IllegalArgumentException("fechaVenta no puede ser null");
        if (Objects.isNull(funcionId)) throw new IllegalArgumentException("funcionId no puede ser null");
        if (Objects.isNull(entradas) || entradas.isEmpty()) throw new IllegalArgumentException("entradas no pueden ser null");
        if (entradas.stream()
                .filter(entrada -> Objects.nonNull(entrada.getFuncionId()))
                .anyMatch(entrada -> entrada.getFuncionId() != funcionId)) throw new IllegalArgumentException("Existen entradas que no corresponden a la funcion id: " + funcionId);
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
                venta.getFechaVenta(),
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

    public List<RecaudacionPorPeliculaDto> getPeliculasConMayorRecaudacion() {
        return ventas.stream()
                .map(Venta::getPeliculaId)
                .distinct()
                .map(peliculaId -> {
                    try {
                        return getRecaudacionPorPelicula(peliculaId);
                    } catch (NotFoundException e) {
                        log.error("No se encontro pelicula con ID: " + peliculaId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(RecaudacionPorPeliculaDto::getRecaudacionTotal).reversed())
                .toList();
    }

    private RecaudacionPorPeliculaDto getRecaudacionPorPelicula(int peliculaId) throws NotFoundException {
        double recaudacionTotal = this.recaudacionPorPelicula(peliculaId);
        PeliculaDto peliculaDto = PeliculasController.getInstance().buscarPeliculaDtoById(peliculaId);

        return new RecaudacionPorPeliculaDto(peliculaDto, recaudacionTotal);
    }
}
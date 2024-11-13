package org.uade.blockbuster.controller;

import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.Funcion;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.Venta;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class VentasController {
    private static volatile VentasController INSTANCE;
    private Collection<Venta> ventas;

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
                .orElse(null);
    }
}

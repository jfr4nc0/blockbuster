package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class Venta {
    private int ventaId;
    private LocalDate fechaVenta;
    private List<Combo> combos;
    private Funcion funcion;
    private TarjetaDescuento tarjetaDescuento;
    private List<Entrada> entradas;

    public int getFuncionId() {
        return funcion.getFuncionId();
    }

    public int getPeliculaId() {
        return funcion.getPeliculaId();
    }

    public List<Integer> getListaComboId() {
        return combos.stream()
                .map(Combo::getComboId)
                .toList();
    }
}

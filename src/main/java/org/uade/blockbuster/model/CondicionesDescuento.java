package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.uade.blockbuster.model.enums.TipoDescuento;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CondicionesDescuento {
    private int condicionDescuentoId;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private int diaSemana;
    private double porcentaje;
    private TipoTarjeta tipoTarjeta;
    private List<TarjetaDescuento> tarjetasDescuento;
    private TipoDescuento tipoDescuento;

    public double getDescuento() {
        return this.porcentaje / 100.00;
    }
}

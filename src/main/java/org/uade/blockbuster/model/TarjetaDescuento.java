package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.uade.blockbuster.model.enums.TipoTarjeta;

@Getter
@AllArgsConstructor
public class TarjetaDescuento {
    private int tarjetaId;
    private TipoTarjeta tipoTarjeta;
}

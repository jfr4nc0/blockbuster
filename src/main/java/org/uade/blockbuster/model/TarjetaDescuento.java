package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoTarjeta;

public class TarjetaDescuento {
    private int tarjetaId;
    private TipoTarjeta tipoTarjeta;

    public int getTarjetaId() {
        return tarjetaId;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }
}

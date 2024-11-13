package org.uade.blockbuster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Sala;
import org.uade.blockbuster.model.Sucursal;

import java.util.ArrayList;
import java.util.Collection;

public class SucursalController {
    private static volatile SucursalController INSTANCE;
    private Collection<Sucursal> sucursales;

    private static final Logger log = LoggerFactory.getLogger(SucursalController.class);

    private SucursalController() {
        this.sucursales = new ArrayList<Sucursal>();
    }

    public static SucursalController getInstance() {
        SucursalController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (SucursalController.class) {
            if (INSTANCE == null) {
                INSTANCE = new SucursalController();
            }
            return INSTANCE;
        }
    }

    public void agregarSucursal(String denominacion, String direccion) {
        int sucursalId = this.sucursales.size() + 1;
        sucursales.add(new Sucursal(sucursalId, denominacion, direccion));
    }

    public void agregarSala(int sucursalId, String denominacion, int asientos) {
        try {
            Collection<Sala> salas = buscarSalasPorSucursalId(sucursalId);

            int salaId = salas.size() + 1;
            Sala sala = new Sala(salaId, sucursalId, denominacion, asientos);

            salas.add(sala);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private Collection<Sala> buscarSalasPorSucursalId(int sucursalId) throws NotFoundException {
        return this.sucursales.stream()
                .filter(sucursal -> sucursal.getSucursalId() == sucursalId)
                .map(Sucursal::getSalas)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe una sucursal con id: " + sucursalId));
    }
}

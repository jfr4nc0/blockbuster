package org.uade.blockbuster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uade.blockbuster.controller.dto.SalaDto;
import org.uade.blockbuster.controller.dto.SucursalDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Sala;
import org.uade.blockbuster.model.Sucursal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SucursalController {
    private static volatile SucursalController INSTANCE;
    private Collection<Sucursal> sucursales;

    private static final Logger log = LoggerFactory.getLogger(SucursalController.class);

    private SucursalController() {
        this.sucursales = new ArrayList<Sucursal>();

        cargaInicial();
    }

    private void cargaInicial() {
        int sucursalId1 = agregarSucursal("Cinema Devoto", "Quevedo 3365, Devoto, CABA");
        int sucursalId2 = agregarSucursal("Cinemark Palermo", "Beruti 3399, Palermo, CABA");

        try {
            agregarSala(sucursalId1, "A", 130);
            agregarSala(sucursalId1, "B", 110);
            agregarSala(sucursalId1, "C", 100);

            agregarSala(sucursalId2, "A", 130);
            agregarSala(sucursalId2, "B", 110);
            agregarSala(sucursalId2, "C", 100);
        } catch (NotFoundException e) {
            log.error("Error al agregar sala: " + e.getMessage());
        }
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

    public List<Sucursal> getSucursales() {
        return sucursales.stream().toList();
    }

    public int agregarSucursal(String denominacion, String direccion) {
        int sucursalId = this.sucursales.size() + 1;
        sucursales.add(new Sucursal(sucursalId, denominacion, direccion));
        log.info("Se agrego la sucursal id: " + sucursalId);

        return sucursalId;
    }

    public int agregarSala(int sucursalId, String denominacion, int asientos) throws NotFoundException {
        Collection<Sala> salas = buscarSalasPorSucursalId(sucursalId);

        int salaId = salas.size() + 1;
        Sala sala = new Sala(salaId, sucursalId, denominacion, asientos);

        salas.add(sala);
        log.info("Se agrego la sala con id: " + salaId);

        return salaId;
    }

    public Collection<Sala> buscarSalasPorSucursalId(int sucursalId) throws NotFoundException {
        return this.sucursales.stream()
                .filter(sucursal -> sucursal.getSucursalId() == sucursalId)
                .map(Sucursal::getSalas)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe una sucursal con id: " + sucursalId));
    }

    public Sala buscarSalaPorSalaId(int sucursalId, int salaId) throws NotFoundException {
        return buscarSalasPorSucursalId(sucursalId).stream()
                .filter(sala -> sala.getSalaId() == salaId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe una sala con id: " + salaId));
    }

    public List<SucursalDto> getSucursalesDto() {
        return sucursales.stream()
                .map(this::toDto)
                .toList();
    }

    private SucursalDto toDto(Sucursal sucursal) {
        return new SucursalDto(
                sucursal.getSucursalId(),
                sucursal.getDenominacion(),
                sucursal.getDireccion(),
                sucursal.getSalas().stream().map(this::toDto).toList());
    }

    private SalaDto toDto(Sala sala) {
        return new SalaDto(sala.getSalaId(), sala.getDenominacion(), sala.getAsientos());
    }
}

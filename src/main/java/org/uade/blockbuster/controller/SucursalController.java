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
        Sucursal cinemaDevoto = new Sucursal(1, "Cinema Devoto", "Quevedo 3365, Devoto, CABA",
                List.of(
                        new Sala(1, 1, "A", 130),
                        new Sala(2, 1, "B", 110),
                        new Sala(3, 1, "B", 100)
                ));
        Sucursal cinemarkPalermo = new Sucursal(1, "Cinemark Palermo", " Beruti 3399, Palermo, CABA",
                List.of(
                        new Sala(1, 1, "A", 130),
                        new Sala(2, 1, "B", 110),
                        new Sala(3, 1, "B", 100)
                ));

        sucursales.add(cinemaDevoto);
        sucursales.add(cinemarkPalermo);
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

package org.uade.blockbuster.controller;

import org.uade.blockbuster.controller.dto.ComboDto;
import org.uade.blockbuster.model.Combo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CombosController {
    private static volatile CombosController INSTANCE;
    private Collection<Combo> combos;

    public CombosController() {
        combos = new ArrayList<Combo>();

        cargaInicial();
    }

    private void cargaInicial() {
    }

    public static CombosController getInstance() {
        CombosController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (CombosController.class) {
            if (INSTANCE == null) {
                INSTANCE = new CombosController();
            }
            return INSTANCE;
        }
    }

    public void agregarCombo() {
        //TODO
    }

    public void modificarCombo() {
        //TODO
    }

    public void eliminarCombo() {
        //TODO
    }

    public Combo buscarComboById(int comboId) {
        return combos.stream()
                .filter(combo -> combo.getComboId() == comboId)
                .findFirst()
                .orElse(null);
    }

    public List<Combo> getCombosByCombosId(List<Integer> combosId) {
        return combosId.stream()
                .map(comboId -> buscarComboById(comboId))
                .filter(Objects::nonNull)
                .toList();
    }

    public List<ComboDto> getCombosDtoByCombosId(List<Integer> combosId) {
        return getCombosByCombosId(combosId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ComboDto toDto(Combo combo) {
        return new ComboDto(combo.getComboId(), combo.getDescripcion(), combo.getPrecio());
    }
}

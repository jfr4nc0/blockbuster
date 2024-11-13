package org.uade.blockbuster.controller;

import org.uade.blockbuster.controller.dto.ComboDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.CondicionesDescuento;

import java.util.*;
import java.util.stream.Collectors;

public class CombosController {
    private static volatile CombosController INSTANCE;
    private Collection<Combo> combos;

    public CombosController() {
        combos = new ArrayList<Combo>();
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

    public Collection<Combo> getCombos() {
        return combos;
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
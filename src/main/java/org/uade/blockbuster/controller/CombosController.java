package org.uade.blockbuster.controller;

import lombok.extern.slf4j.Slf4j;
import org.uade.blockbuster.controller.dto.ComboDto;
import org.uade.blockbuster.model.Combo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CombosController {
    private static volatile CombosController INSTANCE;
    private Collection<Combo> combos;

    public CombosController() {
        combos = new ArrayList<Combo>();

        cargaInicial();
    }

    private void cargaInicial() {
        agregarCombo("Combo 1", 1000);
        agregarCombo("Combo 2", 1500);
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

    public int agregarCombo(String descripcion, double precio) {
        validateCombo(descripcion, precio);

        int comboId = combos.size() + 1;
        Combo combo = new Combo(comboId, descripcion, precio);
        combos.add(combo);
        log.info("Se agrego el combo id: " + comboId);

        return comboId;
    }

    private void validateCombo(String descripcion, double precio) {
        if (Objects.isNull(descripcion) || descripcion.isBlank()) throw new IllegalArgumentException("La descripcion no puede ser nula o string vacio");
        if (precio <= 0.00) throw new IllegalArgumentException("El precio no puede ser <= 0");
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

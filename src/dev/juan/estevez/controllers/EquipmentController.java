package dev.juan.estevez.controllers;

import java.util.List;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.EquipmentDAO;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class EquipmentController {

    private final EquipmentDAO equipmentDAO;

    public EquipmentController(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    /**
     * Retrieves the equipment for a given client ID.
     *
     * @param id the client ID
     * @return the equipment associated with the client ID
     */
    public Equipment getEquipmentByClientId(int id) {
        return equipmentDAO.getEquipmentByClientId(id);
    }

    /**
     * Retrieves a list of equipments by client ID.
     *
     * @param id the client ID
     * @return a list of equipments
     */
    public List<Equipment> getEquipmentsByClientId(int id) {
        return equipmentDAO.getEquipmentsByClientId(id);
    }
}

package dev.juan.estevez.services.impl;

import java.util.List;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.EquipmentDAO;
import dev.juan.estevez.services.IEquipmentService;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class EquipmentService implements IEquipmentService {

    private final EquipmentDAO equipmentDAO;

    public EquipmentService(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    @Override
    public int updateEquipment(Equipment equipment) {
        if (equipment == null) {
            return 0;
        }

        return equipmentDAO.update(equipment);
    }

    @Override
    public Equipment getById(int id) {
        return equipmentDAO.findById(id);
    }

    @Override
    public List<Equipment> getAllByClientId(int clientId) {
        return equipmentDAO.findAllByClientId(clientId);
    }

    @Override
    public Equipment getByClientId(int clientId) {
        return equipmentDAO.findByClientId(clientId);
    }

    @Override
    public int create(Equipment equipment) {
        if (equipment == null) {
            return 0;
        }

        return equipmentDAO.create(equipment);
    }
}

package dev.juan.estevez.services;

import dev.juan.estevez.models.Equipment;
import java.util.List;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public interface IEquipmentService {

    Equipment getById(int id);

    List<Equipment> getAllByClientId(int clientId);

    Equipment getByClientId(int clientId);

    int updateEquipment(Equipment equipment);

    int create(Equipment equipment);

}

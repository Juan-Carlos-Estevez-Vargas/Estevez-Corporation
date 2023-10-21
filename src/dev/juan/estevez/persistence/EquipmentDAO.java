package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.StringUtils;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class EquipmentDAO implements CrudRepository<Equipment, Integer> {

    private Connection connection = null;

    private static final String SQL_GET_BY_ID = "SELECT * FROM equipos WHERE id_equipo = ?";
    private static final String SQL_GET_BY_CLIENT_ID = "SELECT * FROM equipos WHERE id_cliente = ?";
    private static final String SQL_INSERT = "INSERT INTO equipos (id_cliente, tipo_equipo, marca, modelo, num_serie, observaciones, estatus, ultima_modificacion, VALUES(?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE_EQUIPMENT = "UPDATE equipos SET marca = ?, modelo = ?, num_serie = ?, tipo_equipo = ?, observaciones = ?, WHERE id_equipo = ?";

    public EquipmentDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(Equipment entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT);) {
            pst.setInt(1, entity.getClientID());
            pst.setString(2, entity.getEquipmentType());
            pst.setString(3, entity.getMark());
            pst.setString(4, entity.getModel());
            pst.setString(5, entity.getSerialNumber());
            pst.setString(6, entity.getObservation());
            pst.setString(7, entity.getStatus());
            pst.setString(8, entity.getLastModification());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_UPDATE_USER_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public Equipment findById(Integer id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractEquipmentFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.CLIENT_FETCH_ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public List<Equipment> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public int update(Equipment entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_EQUIPMENT);) {
            pst.setString(1, entity.getMark());
            pst.setString(2, entity.getModel());
            pst.setString(3, entity.getSerialNumber());
            pst.setString(4, entity.getEquipmentType());
            pst.setString(5, entity.getObservation());
            pst.setInt(6, entity.getEquipmentID());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_UPDATE_USER_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    /**
     * Retrieves a list of equipments by client ID.
     *
     * @param id the client ID
     * @return a list of equipments associated with the client ID
     */
    public List<Equipment> findAllByClientId(int id) {
        List<Equipment> equipments = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_CLIENT_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    equipments.add(extractEquipmentFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.CLIENT_FETCH_ERROR_MESSAGE);
        }

        return equipments;
    }

    /**
     * Retrieves the equipment associated with a specific client by client ID.
     *
     * @param id the ID of the client
     * @return the equipment associated with the client, or null if no equipment is
     *         found
     */
    public Equipment findByClientId(int id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_CLIENT_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractEquipmentFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.CLIENT_FETCH_ERROR_MESSAGE);
        }

        return null;
    }

    /**
     * Extracts an Equipment object from a ResultSet.
     *
     * @param rs the ResultSet containing the equipment data
     * @return the extracted Equipment object
     * @throws SQLException if the ResultSet is null
     */
    private Equipment extractEquipmentFromResultSet(ResultSet rs) throws SQLException {
        Equipment equipment = new Equipment();
        equipment.setEquipmentID(rs.getInt(1));
        equipment.setClientID(rs.getInt(2));
        equipment.setEquipmentType(rs.getString(3));
        equipment.setMark(rs.getString(4));
        equipment.setModel(rs.getString(5));
        equipment.setSerialNumber(rs.getString(6));
        equipment.setAdmissionDay(rs.getString(7));
        equipment.setAdmissionMonth(rs.getString(8));
        equipment.setAdmissionYear(rs.getString(9));
        equipment.setObservation(rs.getString(10));
        equipment.setStatus(rs.getString(11));
        equipment.setLastModification(rs.getString(12));
        equipment.setTechnicalComments(rs.getString(13));
        equipment.setTechnicalRevisionOf(rs.getString(14));
        return equipment;
    }
}

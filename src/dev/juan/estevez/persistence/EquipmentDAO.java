package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class EquipmentDAO implements CrudRepository<Equipment, Integer> {

    private Connection connection = null;

    private static final String SQL_GET_BY_ID = "SELECT * FROM equipos WHERE id_equipo = ?";
    private static final String SQL_GET_BY_CLIENT_ID = "SELECT * FROM equipos WHERE id_cliente = ?";
    private static final String SQL_INSERT = "INSERT INTO equipos (id_cliente, tipo_equipo, marca, modelo, num_serie, observaciones, estatus, ultima_modificacion, fecha_ingreso) VALUES(?,?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE_EQUIPMENT = "UPDATE equipos SET marca = ?, modelo = ?, num_serie = ?, tipo_equipo = ?, observaciones = ?, estatus = ?, fecha_actualizacion = ? WHERE id_equipo = ?";

    public EquipmentDAO() {
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(Equipment entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT);) {
            pst.setInt(1, entity.getId());
            pst.setString(2, entity.getType());
            pst.setString(3, entity.getMark());
            pst.setString(4, entity.getModel());
            pst.setString(5, entity.getSerialNumber());
            pst.setString(6, entity.getObservations());
            pst.setString(7, entity.getStatus());
            pst.setString(8, entity.getLastModification());
            pst.setObject(9, LocalDateTime.now());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.CREATE_EQUIPMENT_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public Equipment findById(Integer id) {
        return findEquipmentByField(SQL_GET_BY_ID, id);
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
            pst.setString(4, entity.getType());
            pst.setString(5, entity.getObservations());
            pst.setString(6, entity.getStatus());
            pst.setObject(7, LocalDateTime.now());
            pst.setInt(8, entity.getId());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.UPDATE_EQUIPMENT_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    public List<Equipment> findAllByClientId(int id) {
        return findEquipmentsByField(SQL_GET_BY_CLIENT_ID, id);
    }

    public Equipment findByClientId(int id) {
        return findEquipmentByField(SQL_GET_BY_CLIENT_ID, id);
    }

    private Equipment findEquipmentByField(String sql, Object field) {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setObject(1, field);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.EQUIPMENT_FETCH_ERROR);
        }
        return null;
    }

    private List<Equipment> findEquipmentsByField(String sql, Object field) {
        List<Equipment> equipments = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setObject(1, field);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    equipments.add(extractFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.EQUIPMENTS_FETCH_ERROR);
        }
        return equipments;
    }

    private Equipment extractFromResultSet(ResultSet rs) throws SQLException {
        return Equipment.builder()
            .id(rs.getInt("id_equipo"))
            .clientId(rs.getInt("id_cliente"))
            .type(rs.getString("tipo_equipo"))
            .mark(rs.getString("marca"))
            .model(rs.getString("modelo"))
            .serialNumber(rs.getString("num_serie"))
            .admissionDate((LocalDateTime) rs.getObject("fecha_ingreso"))
            .observations(rs.getString("observaciones"))
            .status(rs.getString("estatus"))
            .lastModification(rs.getString("ultima_modificacion_por"))
            .technicalComments(rs.getString("comentarios_tecnicos"))
            .technicalRevisionOf(rs.getString("revision_tecnica_de"))
            .build();
    }
}

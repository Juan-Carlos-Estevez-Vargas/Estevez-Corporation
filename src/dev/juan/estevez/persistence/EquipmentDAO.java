package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.StringUtils;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class EquipmentDAO {

    private Connection connection = null;

    private static final String SQL_GET_EQIPMENT_BY_CLIENT_ID = "SELECT * FROM equipos WHERE id_cliente = ?";
    
    public EquipmentDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_DB_CONNECTION);
        }
    }

    /**
     * Retrieves the equipment associated with a specific client by client ID.
     *
     * @param  id  the ID of the client
     * @return     the equipment associated with the client, or null if no equipment is found
     */
    public Equipment getEquipmentByClientId(int id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_EQIPMENT_BY_CLIENT_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractEquipmentFromResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.CLIENT_FETCH_ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Extracts an Equipment object from a ResultSet.
     *
     * @param  rs  the ResultSet containing the equipment data
     * @return     the extracted Equipment object
     * @throws SQLException if the ResultSet is null
     */
    private Equipment extractEquipmentFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new SQLException("ResultSet is null.");

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

package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientDAO implements CrudRepository<Client, Integer> {

    private Connection connection = null;

    private static final String SQL_GET_ALL = "SELECT * FROM clientes";
    private static final String SQL_GET_BY_ID = "SELECT * FROM clientes WHERE id_cliente = ?";
    private static final String SQL_GET_BY_EMAIL = "SELECT * FROM clientes WHERE mail_cliente = ?";
    private static final String SQL_REGISTER = "INSERT INTO clientes VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE clientes SET nombre_cliente = ?, mail_cliente = ?, tel_cliente = ?, dir_cliente = ?, ultima_modificacion = ?, fecha_actualizacion = ? WHERE id_cliente = ?";

    public ClientDAO() {
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(Client entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER);) {
            pst.setInt(1, 0); // id_cliente - autoincrement
            pst.setString(2, entity.getName());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getPhone());
            pst.setString(5, entity.getAddress());
            pst.setString(6, entity.getLastModification());
            pst.setObject(6, LocalDateTime.now());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.REGISTER_CLIENT_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public Client findById(Integer id) {
        return findClientByField(SQL_GET_BY_ID, id);
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                clients.add(extractFromResultSet(rs));
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.CLIENTS_FETCH_ERROR);
        }

        return clients;
    }

    @Override
    public int update(Client entity) {
        int recordsUpdated = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE);) {
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getEmail());
            pst.setString(3, entity.getPhone());
            pst.setString(4, entity.getAddress());
            pst.setString(5, entity.getLastModification());
            pst.setObject(6, LocalDateTime.now());
            pst.setInt(7, entity.getId());
            recordsUpdated = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.UPDATE_CLIENT_ERROR);
        }

        return recordsUpdated;
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    public Client findByEmail(String email) {
        return findClientByField(SQL_GET_BY_EMAIL, email);
    }
    
    private Client findClientByField(String sql, Object field) {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setObject(1, field);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.CLIENT_FETCH_ERROR);
        }
        return null;
    }

    private Client extractFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) throw new SQLException("ResultSet is null.");

        return Client.builder()
            .id(rs.getInt("id_cliente"))
            .name(rs.getString("nombre_cliente"))
            .email(rs.getString("mail_cliente"))
            .phone(rs.getString("tel_cliente"))
            .address(rs.getString("dir_cliente"))
            .lastModification(rs.getString("ultima_modificacion_por"))
            .build();
    }
}

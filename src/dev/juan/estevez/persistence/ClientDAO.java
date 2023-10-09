package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Client;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.StringUtils;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientDAO {

    private Connection connection = null;

    private static final String SQL_GET_CLIENTS = "SELECT * FROM clientes";
    private static final String SQL_GET_CLIENT_BY_ID = "SELECT * FROM clientes WHERE id_cliente = ?";
    private static final String SQL_GET_CLIENT_BY_EMAIL = "SELECT * FROM clientes WHERE mail_cliente = ?";
    private static final String SQL_REGISTER_CLIENT = "INSERT INTO clientes VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_CLIENT = "UPDATE clientes SET nombre_cliente = ?, mail_cliente = ?, tel_cliente = ?, dir_cliente = ?, ultima_modificacion = ? WHERE id_cliente = ?";

    public ClientDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_DB_CONNECTION);
        }
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return a list of Client objects representing all the clients
     */
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_CLIENTS);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                clients.add(extractClientFromResultSet(rs));
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_GET_CLIENT_LIST);
        }

        return clients;
    }

    /**
     * Retrieves a client from the database based on their email.
     *
     * @param email the email of the client
     * @return the client with the corresponding email, or null if no client is
     *         found
     */
    public Client getClientByEmail(String email) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_CLIENT_BY_EMAIL)) {
            pst.setString(1, email);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractClientFromResultSet(rs);
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
     * Retrieves a client from the database by their ID.
     *
     * @param id the ID of the client
     * @return the client object if found, otherwise null
     */
    public Client getClientById(int id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_CLIENT_BY_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractClientFromResultSet(rs);
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
     * Registers a client in the system.
     *
     * @param client the client object to be registered
     * @return the number of records inserted in the database
     */
    public int registerClient(Client client) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER_CLIENT);) {
            pst.setInt(1, 0);
            pst.setString(2, client.getClientName());
            pst.setString(3, client.getClientEmail());
            pst.setString(4, client.getClientPhone());
            pst.setString(5, client.getClientAddress());
            pst.setString(6, client.getLastModification());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_REGISTER_CLIENT_ERROR);
        }

        return recordsInserted;
    }

    /**
     * Updates a client in the database with the provided information.
     *
     * @param client the client object containing the updated information
     * @return the number of records inserted in the database
     */
    public int updateClient(Client client) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_CLIENT);) {
            pst.setString(1, client.getClientName());
            pst.setString(2, client.getClientEmail());
            pst.setString(3, client.getClientPhone());
            pst.setString(4, client.getClientAddress());
            pst.setString(5, client.getLastModification());
            pst.setInt(6, client.getClientID());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_UPDATE_USER_ERROR);
        }

        return recordsInserted;
    }

    /**
     * Extracts a Client object from a ResultSet.
     *
     * @param rs the ResultSet containing the client data
     * @return the extracted Client object
     * @throws SQLException if the ResultSet is null
     */
    private Client extractClientFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new SQLException("ResultSet is null.");

        Client client = new Client();
        client.setClientID(rs.getInt(1));
        client.setClientName(rs.getString(2));
        client.setClientEmail(rs.getString(3));
        client.setClientPhone(rs.getString(4));
        client.setClientAddress(rs.getString(5));
        client.setLastModification(rs.getString(6));

        return client;
    }
}

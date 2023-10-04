package dev.juan.estevez.controllers;

import java.sql.SQLException;
import java.util.List;

import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientController {

    private final ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }  

    /**
     * Retrieves all clients from the database.
     *
     * @return  a list of Client objects representing all clients
     * @throws SQLException  if there is an error retrieving the clients
     */
    public List<Client> getAllClients() throws SQLException {
        return clientDAO.getAllClients();
    }

    /**
     * Retrieves a client object based on the provided email address.
     *
     * @param  email  the email address of the client
     * @return        the client object associated with the given email address
     */
    public Client getClientByEmail(String email) {
        return clientDAO.getClientByEmail(email);
    }

    /**
     * Registers a client.
     *
     * @param  client  the client to be registered
     * @return         the result of the registration process
     */
    public int registerClient(Client client) {
        return clientDAO.registerClient(client);
    }

    /**
     * Updates a client in the system.
     *
     * @param  client  the client to be updated
     * @return         the number of rows affected
     */
    public int updateClient(Client client) {
        return clientDAO.updateClient(client);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param  id  the ID of the client to retrieve
     * @return     the client with the specified ID, or null if no client is found
     */
    public Client getClientById(int id) {
        return clientDAO.getClientById(id);
    }

}

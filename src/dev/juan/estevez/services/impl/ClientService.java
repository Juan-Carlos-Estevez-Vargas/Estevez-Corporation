package dev.juan.estevez.services.impl;

import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.services.IClientService;
import java.util.List;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientService implements IClientService {

    private final ClientDAO clientDAO;

    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public int createClient(Client client) {
        return clientDAO.create(client);
    }

    @Override
    public Client getById(int id) {
        return clientDAO.findById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientDAO.findAll();
    }

    @Override
    public void deleteClientById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteClientById'");
    }

    @Override
    public Client getByEmail(String email) {
        return clientDAO.findByEmail(email);
    }

    @Override
    public int updateClient(Client client) {
        return clientDAO.update(client);
    }

}

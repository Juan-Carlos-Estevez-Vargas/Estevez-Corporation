package dev.juan.estevez.services;

import dev.juan.estevez.models.Client;
import java.util.List;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public interface IClientService {

    int createClient(Client client);

    Client getById(int id);

    List<Client> getAll();

    int updateClient(Client client);

    void deleteClientById(int id);

    Client getByEmail(String email);

}

package dev.juan.estevez.services.impl;

import java.util.List;

import dev.juan.estevez.models.Role;
import dev.juan.estevez.persistence.RoleDAO;
import dev.juan.estevez.services.IRoleService;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class RoleService implements IRoleService {

    private final RoleDAO roleDAO;

    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAll() {
        return roleDAO.findAll();
    }

}

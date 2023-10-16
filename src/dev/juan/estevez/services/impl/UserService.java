package dev.juan.estevez.services.impl;

import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.services.IUserService;
import dev.juan.estevez.utils.StringUtils;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class UserService implements IUserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public int updateUser(User user) {
        if (user == null) {
            return 0;
        }
        return userDAO.update(user);
    }

    @Override
    public int createUser(User user) {
        if (user == null) {
            return 0;
        }
        return userDAO.create(user);
    }

    @Override
    public User getById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public void deleteUserById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserById'");
    }

    @Override
    public User getByUsername(String username) {
        if (username.isEmpty()) {
            StringUtils.showEmptyFieldsMessage();
            return null;
        }
        return userDAO.findByUsername(username);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            StringUtils.showEmptyFieldsMessage();
            return null;
        }
        return userDAO.findByUsernameAndPassword(username, password);
    }
}

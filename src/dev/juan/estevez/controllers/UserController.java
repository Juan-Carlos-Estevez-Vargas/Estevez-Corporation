package dev.juan.estevez.controllers;

import java.sql.SQLException;
import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.StringUtils;

public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Retrieves a User object based on the provided username and password.
     *
     * @param  username  the username of the user
     * @param  password  the password of the user
     * @return           the User object if found, null otherwise
     * @throws SQLException if there is an error executing the SQL query
     */
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        if (username.isEmpty() || password.isEmpty()) {
            StringUtils.showEmptyFieldsMessage();
            return null;
        }
    
        return userDAO.getUserByUsernameAndPassword(username, password);
    }    

    /**
     * Retrieves a list of all users.
     *
     * @return a list of User objects representing all users
     * @throws SQLException if there is an error retrieving the users from the database
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    /**
     * Registers a user.
     *
     * @param  user  the user to register
     * @return       the registration result
     */
    public int registerUser(User user) {
        return userDAO.registerUser(user);
    }

    /**
     * Retrieves a user object based on the provided username.
     *
     * @param  username  the username of the user to retrieve
     * @return           the User object corresponding to the provided username
     */
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param  id  the ID of the user
     * @return     the user with the specified ID
     */
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    /**
     * Updates a user in the database.
     *
     * @param  user  the user to be updated
     * @return       the number of rows affected
     */
    public int updateUser(User user) {
        return userDAO.updateUser(user);
    }
}

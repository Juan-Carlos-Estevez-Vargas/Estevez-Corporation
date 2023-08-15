package dev.juan.estevez.controllers;

import java.sql.SQLException;
import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;

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
        User user = userDAO.getUserByUsernameAndPassword(username, password);
        return user != null ? user : null;
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
     * Retrieves the username by the given username.
     *
     * @param  username    the username to retrieve
     * @return             the retrieved username
     */
    public String getUsernameByUsername(String username) {
        return userDAO.getUsernameByUsername(username);
    }
}

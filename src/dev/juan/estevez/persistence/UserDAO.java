package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.Utils;

public class UserDAO {

    private Connection connection = null;

    private static final String SQL_GET_USERS = "SELECT * FROM usuarios";
    private static final String SQL_GET_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
    private static final String SQL_REGISTER_USER = "INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_GET_USERNAME_BY_USERNAME = "SELECT username FROM usuarios WHERE username = ?";

    public UserDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            Utils.handleQueryError(ex, "Error al conectar a la base de datos");
        }
    }

    /**
     * Retrieves a user based on their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object representing the user, or null if no user is found
     */
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_USER_BY_USERNAME_AND_PASSWORD)) {
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setLevelType(rs.getString("tipo_nivel"));
                    user.setStatus(rs.getString("estatus"));
                    user.setUserName(rs.getString("nombre_usuario"));
                }
            }
        } catch (SQLException ex) {
            Utils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
        }

        return user;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects representing all the users in the database.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_USERS);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString(2));
                user.setUserEmail(rs.getString(3));
                user.setUserPhone(rs.getString(4));
                user.setLevelType(rs.getString(7));
                user.setStatus(rs.getString(8));
                user.setRegisterBy(rs.getString(9));
                users.add(user);
            }
        } catch (SQLException ex) {
            Utils.handleQueryError(ex, "Error al obtener el listado usuarios");
        }

        return users;
    }

    /**
     * Registers a new user in the system.
     *
     * @param  user  the User object containing user details
     * @return       the number of records inserted
     */
    public int registerUser(User user) {
        int recordsInserted = 0;

		try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER_USER);) {
			pst.setInt(1, 0);
			pst.setString(2, user.getUserName());
			pst.setString(3, user.getUserEmail());
			pst.setString(4, user.getUserPhone());
			pst.setString(5, user.getUsername());
			pst.setString(6, user.getPassword());
			pst.setString(7, user.getPermissions());
			pst.setString(8, Constants.ACTIVE);
			pst.setString(9, user.getRegisterBy());
			recordsInserted = pst.executeUpdate();
		} catch (SQLException ex) {
            Utils.handleQueryError(ex, Constants.INTERNAL_REGISTER_USER_ERROR);
		}

        return recordsInserted;
	}

    /**
     * Retrieves the username for a given username.
     *
     * @param  username  the username to search for
     * @return           the username if found, null otherwise
     */
    public String getUsernameByUsername(String username) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_USERNAME_BY_USERNAME)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(Constants.USERNAME);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Utils.handleQueryError(ex, Constants.USERNAME_COMPARISON_ERROR_MESSAGE);
            return null;
        }
    } 

}

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
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.enums.States;

public class UserDAO {

    private Connection connection = null;

    private static final String SQL_GET_USERS = "SELECT * FROM usuarios";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM usuarios WHERE id_usuario = ?";
    private static final String SQL_GET_USER_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
    private static final String SQL_GET_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
    private static final String SQL_REGISTER_USER = "INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE usuarios SET nombre_usuario = ?, email = ?, telefono = ?, username = ?, tipo_nivel = ?, estatus = ? WHERE id_usuario = ?";

    public UserDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_DB_CONNECTION);
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
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
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
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_GET_USER_LIST);
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
			pst.setString(8, States.ACTIVE.getValue());
			pst.setString(9, user.getRegisterBy());
			recordsInserted = pst.executeUpdate();
		} catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_REGISTER_USER_ERROR);
		}

        return recordsInserted;
	}

    /**
     * Retrieves a user from the database by their username.
     *
     * @param  username  the username of the user to retrieve
     * @return           the User object corresponding to the username, or null if not found
     */
    public User getUserByUsername(String username) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_USER_BY_USERNAME)) {
            pst.setString(1, username);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
            return null;
        }
    }   

    /**
     * Retrieves a user from the database based on the provided user ID.
     *
     * @param  id  the ID of the user to retrieve
     * @return     the User object representing the retrieved user, or null if no user is found
     */
    public User getUserById(int id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Updates a user in the system.
     *
     * @param  user  the User object that contains the updated user information
     * @return       the number of records that were inserted in the database
     */
    public int updateUser(User user) {
        int recordsInserted = 0;

		try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_USER);) {
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getUserEmail());
			pst.setString(3, user.getUserPhone());
			pst.setString(4, user.getUsername());
			pst.setString(5, user.getPermissions());
			pst.setString(6, user.getStatus());
            pst.setInt(7, user.getUserID());
			recordsInserted = pst.executeUpdate();
		} catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_UPDATE_USER_ERROR);
		}

        return recordsInserted;
	}

    /**
     * Extracts a User object from a ResultSet.
     *
     * @param  rs  the ResultSet containing the user data
     * @return     the extracted User object
     * @throws SQLException  if there is an error accessing the ResultSet
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) throw new SQLException("ResultSet is null.");

        User user = new User();
        user.setUserID(rs.getInt(1));
        user.setUserName(rs.getString(2));
        user.setUserEmail(rs.getString(3));
        user.setUserPhone(rs.getString(4));
        user.setUsername(rs.getString(5));
        user.setLevelType(rs.getString(7));
        user.setStatus(rs.getString(8));
        user.setRegisterBy(rs.getString(9));

        return user;
    }
}

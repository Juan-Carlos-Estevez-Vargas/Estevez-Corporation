package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.Utils;

public class UserDAO {

    private Connection connection = null;

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

        try (PreparedStatement pst = connection.prepareStatement(
                "SELECT * FROM usuarios WHERE username = ? AND password = ?")) {
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
            Utils.handleQueryError(ex, "Error al obtener usuario");
        }

        return user;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return          a list of User objects representing all the users in the database.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection cn = (Connection) DatabaseConnection.connect();
                PreparedStatement pst = (PreparedStatement) cn.prepareStatement("SELECT * FROM usuarios");
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

}

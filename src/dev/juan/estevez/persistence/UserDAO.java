package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.StringUtils;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class UserDAO implements CrudRepository<User, Integer> {

    private Connection connection = null;

    private static final String SQL_GET_ALL = "SELECT * FROM usuarios";
    private static final String SQL_GET_BY_ID = "SELECT * FROM usuarios WHERE id_usuario = ?";
    private static final String SQL_GET_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
    private static final String SQL_GET_BY_USERNAME_AND_PASSWORD = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
    private static final String SQL_REGISTER = "INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE usuarios SET nombre_usuario = ?, email = ?, telefono = ?, username = ?, tipo_nivel = ?, estatus = ? WHERE id_usuario = ?";

    public UserDAO() {
        try {
            this.connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(User entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER);) {
            pst.setInt(1, 0); // id_usuario - autoincrement
            pst.setString(2, entity.getUserName());
            pst.setString(3, entity.getUserEmail());
            pst.setString(4, entity.getUserPhone());
            pst.setString(5, entity.getUsername());
            pst.setString(6, entity.getPassword());
            pst.setString(7, entity.getLevelType());
            pst.setString(8, entity.getStatus());
            pst.setString(9, entity.getRegisterBy());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_REGISTER_USER_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public User findById(Integer id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ID)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.ERROR_GET_USER_LIST);
        }

        return users;
    }

    @Override
    public int update(User entity) {
        int recordsUpdated = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE);) {
            pst.setString(1, entity.getUserName());
            pst.setString(2, entity.getUserEmail());
            pst.setString(3, entity.getUserPhone());
            pst.setString(4, entity.getUsername());
            pst.setString(5, entity.getLevelType());
            pst.setString(6, entity.getStatus());
            pst.setInt(7, entity.getUserID());
            recordsUpdated = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.INTERNAL_UPDATE_USER_ERROR);
        }

        return recordsUpdated;
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    public User findByUsername(String username) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_USERNAME)) {
            pst.setString(1, username);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
        }

        return null;
    }

    public User findByUsernameAndPassword(String username, String password) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_USERNAME_AND_PASSWORD)) {
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, Constants.USER_FETCH_ERROR_MESSAGE);
        }

        return null;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new SQLException("ResultSet is null.");

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

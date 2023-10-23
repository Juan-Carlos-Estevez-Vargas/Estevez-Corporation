package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Role;
import dev.juan.estevez.models.User;
import dev.juan.estevez.models.UserRole;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class UserDAO implements CrudRepository<User, Integer> {

    private Connection connection = null;
    private Role role = null;
    private RoleDAO roleDAO;
    private UserHasRoleDAO userHasRoleDAO;

    private static final String SQL_GET_ALL = "SELECT * FROM usuarios";
    private static final String SQL_GET_BY_ID = "SELECT * FROM usuarios WHERE id_usuario = ?";
    private static final String SQL_GET_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
    private static final String SQL_GET_BY_USERNAME_AND_PASSWORD = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
    private static final String SQL_GET_ROLES_BY_USER = "select * from usuario_por_rol where id_usuario = ?";
    private static final String SQL_REGISTER = "INSERT INTO usuarios (id_usuario, nombre_usuario, email, telefono, username, password, estatus, registrado_por, fecha_ingreso) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE usuarios SET nombre_usuario = ?, email = ?, telefono = ?, username = ?, estatus = ?, fecha_actualizacion = ? WHERE id_usuario = ?";

    public UserDAO() {
        try {
            connection = DatabaseConnection.connect();
            roleDAO = new RoleDAO();
            userHasRoleDAO = new UserHasRoleDAO();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(User entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER, Statement.RETURN_GENERATED_KEYS);) {
            pst.setInt(1, 0); // id_usuario - autoincrement
            pst.setString(2, entity.getName());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getPhone());
            pst.setString(5, entity.getUsername());
            pst.setString(6, entity.getPassword());
            pst.setString(7, entity.getStatus());
            pst.setString(8, entity.getRegisterBy());
            pst.setObject(9, LocalDateTime.now());
            recordsInserted = pst.executeUpdate();

            // Después de la inserción, intenta obtener el último ID insertado
            int lastInsertedId = getLastInsertedId(pst);
            if (lastInsertedId > 0) {
                entity.setId(lastInsertedId);

                UserRole userHasRole = new UserRole();
                userHasRole.setRole(role);
                userHasRole.setUser(entity);
                userHasRoleDAO.create(userHasRole);
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.REGISTER_USER_ERROR);
        }

        return recordsInserted;
    }

    private int getLastInsertedId(PreparedStatement pst) throws SQLException {
        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            return 0; // Si no se pudo obtener el último ID
        }
    }

    public int create(User entity, Role role) {
        this.role = role;
        return create(entity);
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
            StringUtils.handleQueryError(ex, DbConstants.USER_FETCH_ERROR);
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
            StringUtils.handleQueryError(ex, DbConstants.ERROR_GET_USER_LIST);
        }

        return users;
    }

    @Override
    public int update(User entity) {
        int recordsUpdated = 0;
        List<UserRole> userHasRoleList = findRoleListByUser(entity);

        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE);) {
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getEmail());
            pst.setString(3, entity.getPhone());
            pst.setString(4, entity.getUsername());
            pst.setString(5, entity.getStatus());
            pst.setObject(6, LocalDateTime.now());
            pst.setInt(7, entity.getId());
            recordsUpdated = pst.executeUpdate();

            for (UserRole userHasRole : userHasRoleList) {
                if (userHasRole.getRole().getRoleID() != role.getRoleID()) {
                    UserRole userRoleToAdd = new UserRole();
                    userRoleToAdd.setRole(role);
                    userRoleToAdd.setUser(entity);
                    userHasRoleDAO.create(userRoleToAdd);
                }
            }

        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.UPDATE_USER_ERROR);
        }

        return recordsUpdated;
    }

    public int update(User entity, Role role) {
        this.role = role;
        return update(entity);
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
            StringUtils.handleQueryError(ex, DbConstants.USER_FETCH_ERROR);
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
            StringUtils.handleQueryError(ex, DbConstants.USER_FETCH_ERROR);
        }

        return null;
    }

    public List<UserRole> findRoleListByUser(User user) {
        List<UserRole> roles = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ROLES_BY_USER)) {
            pst.setInt(1, user.getId());

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    roles.add(mapResultSetToUserRole(rs, user));
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_GET_USER_ROLES);
        }

        return roles;
    }

    private UserRole mapResultSetToUserRole(ResultSet rs, User user) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setRole(roleDAO.findById(rs.getInt("id_rol")));
        userRole.setUser(user);
        return userRole;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new SQLException("ResultSet is null.");

        User user = new User();
        user.setId(rs.getInt("id_usuario"));
        user.setName(rs.getString("nombre_usuario"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("telefono"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setStatus(rs.getString("estatus"));
        user.setRegisterBy(rs.getString("registrado_por"));

        return user;
    }

}

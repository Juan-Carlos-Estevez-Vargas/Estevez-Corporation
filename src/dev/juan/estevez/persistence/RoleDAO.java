package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.juan.estevez.models.Role;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class RoleDAO implements CrudRepository<Role, Integer> {

    private Connection connection = null;

    private static final String SQL_GET_ALL = "SELECT * FROM roles";
    private static final String SQL_GET_BY_ID_ROLE = "SELECT * FROM roles WHERE id_rol = ?";

    public RoleDAO() {
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(Role entity) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Role findById(Integer id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ID_ROLE)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ROLE_FETCH_ERROR);
        }

        return null;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                roles.add(extractFromResultSet(rs));
            }
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_GET_ROLE_LIST);
        }

        return roles;
    }

    @Override
    public int update(Role entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    private Role extractFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new SQLException("ResultSet is null.");

        Role role = new Role();
        role.setRoleID(rs.getInt("id_rol"));
        role.setRoleName(rs.getString("nombre_rol"));

        return role;
    }

}

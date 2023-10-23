package dev.juan.estevez.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dev.juan.estevez.models.UserRole;
import dev.juan.estevez.persistence.repository.CrudRepository;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class UserHasRoleDAO implements CrudRepository<UserRole, Integer> {

    private Connection connection = null;

    private static final String SQL_REGISTER = "INSERT INTO usuario_por_rol (id, id_usuario, id_rol) VALUES (?,?,?)";

    public UserHasRoleDAO() {
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.ERROR_DB_CONNECTION);
        }
    }

    @Override
    public int create(UserRole entity) {
        int recordsInserted = 0;

        try (PreparedStatement pst = connection.prepareStatement(SQL_REGISTER);) {
            pst.setInt(1, 0);
            pst.setInt(2, entity.getUser().getId());
            pst.setInt(3, entity.getRole().getRoleID());
            recordsInserted = pst.executeUpdate();
        } catch (SQLException ex) {
            StringUtils.handleQueryError(ex, DbConstants.REGISTER_ROLE_HAS_USER_ERROR);
        }

        return recordsInserted;
    }

    @Override
    public UserRole findById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<UserRole> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public int update(UserRole entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}

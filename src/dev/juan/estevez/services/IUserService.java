package dev.juan.estevez.services;

import dev.juan.estevez.models.Role;
import dev.juan.estevez.models.User;
import dev.juan.estevez.models.UserRole;

import java.util.List;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public interface IUserService {

    int createUser(User user, Role userRole);

    User getById(int id);

    List<User> getAllUsers();

    int updateUser(User user, Role userRole);

    void deleteUserById(int id);

    User getByUsername(String username);

    User getByUsernameAndPassword(String username, String password);

    List<UserRole> getRoleListByUser(User user);

}

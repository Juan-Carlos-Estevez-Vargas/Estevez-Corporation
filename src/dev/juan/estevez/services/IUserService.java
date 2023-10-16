package dev.juan.estevez.services;

import dev.juan.estevez.models.User;
import java.util.List;

public interface IUserService {

    int createUser(User user);

    User getById(int id);

    List<User> getAllUsers();

    int updateUser(User user);

    void deleteUserById(int id);

    User getByUsername(String username);

    User getByUsernameAndPassword(String username, String password);

}

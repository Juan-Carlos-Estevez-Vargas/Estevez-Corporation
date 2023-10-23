package dev.juan.estevez.models;

import lombok.Data;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
@Data
public class UserRole {

    private int id;
    private User user;
    private Role role;

    public UserRole() {
    }

    public UserRole(int id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

}

package dev.juan.estevez.models;

import lombok.Data;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
@Data
public class Role {

    private int roleID;
    private String roleName;

    public Role() {
    }

    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

}

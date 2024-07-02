package dev.juan.estevez.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private int id;
    private User user;
    private Role role;

}

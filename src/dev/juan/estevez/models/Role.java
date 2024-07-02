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
public class Role {

    private int roleID;
    private String roleName;

}

package dev.juan.estevez;

import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas
 */
public class Main {

    public static void main(String[] args) {
        LoginView login = new LoginView(new UserService(new UserDAO()));
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

}

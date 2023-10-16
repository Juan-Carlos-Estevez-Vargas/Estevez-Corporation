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
        UserDAO loginDAO = new UserDAO(); 
		UserService loginController = new UserService(loginDAO);
        LoginView login = new LoginView(loginController);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

}

package dev.juan.estevez;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.views.LoginView;

/**
 * Main class to start the application.
 * 
 * @author Juan Carlos Estevez Vargas
 */
public class Main {

    /**
     * The main entry point for the Java application.
     *
     * @param  args  the command line arguments passed to the application
     */
    public static void main(String[] args) {
        UserDAO loginDAO = new UserDAO(); 
		UserController loginController = new UserController(loginDAO);
        LoginView login = new LoginView(loginController);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

}

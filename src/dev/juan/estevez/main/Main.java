package dev.juan.estevez.main;

import dev.juan.estevez.controllers.LoginController;
import dev.juan.estevez.persistence.LoginDAO;
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
     * @return       void
     */
    public static void main(String[] args) {
        LoginDAO loginDAO = new LoginDAO(); 
		LoginController loginController = new LoginController(loginDAO); // Inyectar el DAO en el controlador
        LoginView login = new LoginView(loginController);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

}

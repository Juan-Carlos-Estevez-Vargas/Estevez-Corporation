package clases;

import ventanas.Login;
import ventanas.ManagementUsers;
import ventanas.RegisterUser;
import ventanas.PanelAdministrador;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Login login = new Login();
        //login.setLocationRelativeTo(null);
        //login.setVisible(true);
    	ManagementUsers mu = new ManagementUsers();
    	mu.setLocationRelativeTo(null);
    	mu.setVisible(true);
    }

}

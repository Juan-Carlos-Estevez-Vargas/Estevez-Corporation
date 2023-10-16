package dev.juan.estevez.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas
 */
public class ViewUtils {

    /**
     * Handles the logout process.
     *
     * @param  frame  the JFrame object representing the current frame
     */
    public static void handleLogout(JFrame frame) {
        int confirmationResult = JOptionPane.showConfirmDialog(null, Constants.ARE_YOU_SURE_TO_LOGOUT);
        if (confirmationResult == JOptionPane.OK_OPTION) {
            LoginView loginView = new LoginView(new UserService(new UserDAO()));
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            frame.dispose();
        }
    }

    /**
     * Opens a panel by disposing the current frame and making the panel visible.
     *
     * @param  panel  the panel to be opened
     * @param  frame  the current frame to be disposed
     */
    public static void openPanel(JFrame panel, JFrame frame) {
        frame.dispose();
        panel.setVisible(true);
    }

    /**
     * Opens the specified panel by setting its visibility to true.
     *
     * @param  panel  the JFrame panel to be opened
     */
    public static void openPanel(JFrame panel) {
        panel.setVisible(true);
    }
}

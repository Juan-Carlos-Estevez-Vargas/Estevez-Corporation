package dev.juan.estevez.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.views.LoginView;
import dev.juan.estevez.views.admin.ManagementUsersView;
import dev.juan.estevez.views.admin.RegisterUserView;
import dev.juan.estevez.views.employee.ManagementClients;
import dev.juan.estevez.views.employee.RegisterClient;
import panel.utilities.RestorePassword;

public class ViewUtils {

    /**
     * Opens a view of the specified class.
     *
     * @param  viewClass  the class of the view to be opened
     */
    private static void openView(Class<?> viewClass) {
        try {
            JFrame view = (JFrame) viewClass.getDeclaredConstructor().newInstance();
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the restore password functionality.
     */
    public static void handleRestorePassword() {
        openView(RestorePassword.class);
    }

    /**
     * Handle the registration of a user.
     */
    public static void handleRegisterUser() {
        openView(RegisterUserView.class);
    }

    /**
     * A description of the entire Java function.
     */
    public static void handleManageUser() {
        openView(ManagementUsersView.class);
    }

    /**
     * Handles the registration of a client.
     */
    public static void handleRegisterClient() {
        openView(RegisterClient.class);
    }

    /**
     * Handles the management of clients.
     */
    public static void handleManageClients() {
        openView(ManagementClients.class);
    }

    /**
     * Handles the logout process.
     *
     * @param  frame  the JFrame object representing the current frame
     */
    public static void handleLogout(JFrame frame) {
        int confirmationResult = JOptionPane.showConfirmDialog(null, Constants.ARE_YOU_SURE_TO_LOGOUT);
        if (confirmationResult == JOptionPane.OK_OPTION) {
            LoginView loginView = new LoginView(new UserController(new UserDAO()));
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            frame.dispose();
        }
    }
}

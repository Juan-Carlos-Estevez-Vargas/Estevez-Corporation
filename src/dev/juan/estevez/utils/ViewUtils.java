package dev.juan.estevez.utils;

import javax.swing.JFrame;

import dev.juan.estevez.views.admin.ManagementUsersView;
import dev.juan.estevez.views.admin.RegisterUserView;
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

}

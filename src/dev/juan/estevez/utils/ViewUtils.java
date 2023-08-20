package dev.juan.estevez.utils;

import javax.swing.JFrame;

import dev.juan.estevez.views.admin.ManagementUsersView;
import dev.juan.estevez.views.admin.RegisterUserView;
import panel.utilities.RestorePassword;

public class ViewUtils {

    private static void openView(Class<?> viewClass) {
        try {
            JFrame view = (JFrame) viewClass.getDeclaredConstructor().newInstance();
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleRestorePassword() {
        openView(RestorePassword.class);
    }

    public static void handleRegisterUser() {
        openView(RegisterUserView.class);
    }

    public static void handleManageUser() {
        openView(ManagementUsersView.class);
    }

}

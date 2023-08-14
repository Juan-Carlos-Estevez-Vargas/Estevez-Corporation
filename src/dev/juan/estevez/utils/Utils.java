package dev.juan.estevez.utils;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Utils {

    /**
     * Shows a message dialog indicating the presence of empty fields.
     */
    public static void showEmptyFieldsMessage() {
        JOptionPane.showMessageDialog(null, Constants.EMPTY_FIELDS);
    }

    /**
     * Handles a query error by printing an error message and displaying an error dialog.
     *
     * @param  ex      the SQLException that occurred
     * @param  message the error message to be printed
     */
    public static void handleQueryError(SQLException ex, String message) {
        System.err.println(message + ex);
        JOptionPane.showMessageDialog(null, Constants.INTERNAL_LOGIN_ERROR);
    }
}

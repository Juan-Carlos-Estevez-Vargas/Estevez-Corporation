package dev.juan.estevez.utils;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import dev.juan.estevez.enums.States;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class StringUtils {

    /**
     * Shows a message dialog indicating the presence of empty fields.
     */
    public static void showEmptyFieldsMessage() {
        JOptionPane.showMessageDialog(null, Constants.EMPTY_FIELDS);
    }

    /**
     * Handles a query error by printing an error message and displaying an error
     * dialog.
     *
     * @param ex      the SQLException that occurred
     * @param message the error message to be printed
     */
    public static void handleQueryError(SQLException ex, String message) {
        System.err.println(message);
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Display a message in a dialog box.
     *
     * @param message the message to be displayed
     */
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Generates the status string based on the given status index.
     *
     * @param statusIndex the status index to generate the string for
     * @return the status string ("Activo" or "Inactivo")
     */
    public static String getStatusString(int statusIndex) {
        return (statusIndex == 1) ? States.ACTIVE.getValue() : States.INACTIVE.getValue();
    }

}

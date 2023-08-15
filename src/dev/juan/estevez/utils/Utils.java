package dev.juan.estevez.utils;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Utils {

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
        System.err.println(message + ex);
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Display a message in a dialog box.
     *
     * @param  message  the message to be displayed
     */
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Validates the email field.
     *
     * @param  email      the email to be validated
     * @param  textField  the JTextField to set the background color
     * @return            1 if the email is invalid, 0 otherwise
     */
    public static int validateEmailField(String email, JTextField textField) {
        if (isInvalidEmail(email)) {
            textField.setBackground(Color.red);

            if (email.length() >= 50) {
                showMessage(Constants.EMAIL_LENGTH_LIMIT_MESSAGE);
            }
            if (!isValidEmailFormat(email)) {
                showMessage(Constants.INVALID_EMAIL);
            }

            textField.requestFocus();
            return 1;
        }
        return 0;
    }

    /**
     * Validates the name field.
     *
     * @param  name        the name to be validated
     * @param  textField   the text field to display error
     * @return             0 if the name is valid, 1 if the name is invalid
     */
    public static int validateNameField(String name, JTextField textField) {
        if (isInvalidName(name)) {
			textField.setBackground(Color.red);

			if (isValidNameLength(name)) {
                showMessage(Constants.NAME_LENGTH_LIMIT_MESSAGE);
			}

            textField.requestFocus();
            return 1;
		}
        return 0;
    }

    /**
     * Validates the phone field and updates the UI accordingly.
     *
     * @param  phone         the phone number to be validated
     * @param  textField     the text field to be updated
     * @return               1 if the phone is invalid, 0 otherwise
     */
    public static int validatePhoneField(String phone, JTextField textField) {
        if (isInvalidPhone(phone)) {
			textField.setBackground(Color.red);

			if (isValidPhoneLength(phone)) {
                showMessage(Constants.PHONE_LENGTH_LIMIT_MESSAGE);
			}

            textField.requestFocus();
            return 1;
		}
        return 0;
    }

    /**
     * Checks if an email is invalid based on the following conditions:
     * - The email is empty
     * - The email length is greater than or equal to 50 characters
     * - The email does not have a valid format
     *
     * @param  email  The email to be checked
     * @return        True if the email is invalid, false otherwise
     */
    private static boolean isInvalidEmail(String email) {
        return email.isEmpty() || email.length() >= 50 || !isValidEmailFormat(email);
    }

    /**
     * Checks if the given name is invalid.
     *
     * @param  name  the name to be checked
     * @return       true if the name is invalid, false otherwise
     */
    private static boolean isInvalidName(String name) {
        return name.equals("") || isValidNameLength(name);
    }

    /**
     * Checks if the given phone is invalid.
     *
     * @param  phone  the phone number to be checked
     * @return        true if the phone is invalid, false otherwise
     */
    private static boolean isInvalidPhone(String phone) {
        return phone.equals("") || isValidPhoneLength(phone);
    }

    /**
     * Checks if the given email string is in a valid format.
     *
     * @param  email  the email string to be checked
     * @return        true if the email is in a valid format, false otherwise
     */
    private static boolean isValidEmailFormat(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Checks if the length of the given name is valid.
     *
     * @param  name  the name to be checked
     * @return       true if the name length is greater than or equal to 40 or less than 4, false otherwise
     */
    private static boolean isValidNameLength(String name) {
        return name.length() >= 40 || name.length() < 4;
    }

    /**
     * Checks if the length of the phone number is valid.
     *
     * @param  phone  the phone number to be checked
     * @return        true if the phone number length is greater than or equal to 12 or less than 10, false otherwise
     */
    private static boolean isValidPhoneLength(String phone) {
        return phone.length() >= 12 || phone.length() < 10;
    }

}

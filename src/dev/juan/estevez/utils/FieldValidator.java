package dev.juan.estevez.utils;

import java.awt.Color;
import java.util.function.Predicate;

import javax.swing.JTextField;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class FieldValidator {

    /**
     * Validates a field by checking if the given value meets a specified condition.
     *
     * @param value                 the value to be validated
     * @param textField             the JTextField associated with the field to be validated
     * @param errorMessage          the error message to be displayed if the validation fails
     * @param validationCondition   the condition to be checked for validation
     * @return                      1 if the validation fails, 0 otherwise
     */
    public static int validateField(String value, JTextField textField, String errorMessage,
            Predicate<String> validationCondition) {
        if (validationCondition.test(value)) {
            textField.setBackground(Color.red);
            StringUtils.showMessage(errorMessage);
            textField.requestFocus();
            return 1;
        }
        return 0;
    }

    /**
     * Validates an email field.
     *
     * @param  email      the email to be validated
     * @param  textField  the text field containing the email
     * @return            the result of the validation
     */
    public static int validateEmailField(String email, JTextField textField) {
        return FieldValidator.validateField(email, textField, Constants.INVALID_EMAIL, FieldValidator::isInvalidEmail);
    }

    /**
     * Validates the name field.
     *
     * @param  name      the name to be validated
     * @param  textField the JTextField associated with the name field
     * @return           the result of the validation
     */
    public static int validateNameField(String name, JTextField textField) {
        return FieldValidator.validateField(name, textField, Constants.NAME_LENGTH_LIMIT_MESSAGE,
                FieldValidator::isInvalidName);
    }

    /**
     * Validates a phone field.
     *
     * @param  phone      the phone number to validate
     * @param  textField  the JTextField to display error message
     * @return            the result of the validation
     */
    public static int validatePhoneField(String phone, JTextField textField) {
        return FieldValidator.validateField(phone, textField, Constants.PHONE_LENGTH_LIMIT_MESSAGE,
                FieldValidator::isInvalidPhone);
    }

    /**
     * Validates the username field.
     *
     * @param  username       the username to be validated
     * @param  textField      the JTextField object representing the username field
     * @return                the result of the validation as an integer
     */
    public static int validateUsernameField(String username, JTextField textField) {
        return FieldValidator.validateField(username, textField, Constants.USERNAME_NOT_AVAILABLE_MESSAGE,
                FieldValidator::isInvalidUsername);
    }

    /**
     * Validates an address field.
     *
     * @param  address   the address to be validated
     * @param  textField the text field containing the address
     * @return           the result of the validation
     */
    public static int validateAdressField(String address, JTextField textField) {
        return FieldValidator.validateField(address, textField, Constants.ADDRESS_NOT_AVAILABLE_MESSAGE,
                FieldValidator::isInvalidAddress);
    }

    /**
     * Checks if the given email is invalid.
     *
     * @param  email	the email to be checked
     * @return         	true if the email is invalid, false otherwise
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
     * Checks if the given username is invalid.
     *
     * @param  username   the username to be checked
     * @return            true if the username is invalid, false otherwise
     */
    private static boolean isInvalidUsername(String username) {
        return username.equals("") || isValidUsernameLength(username);
    }

    /**
     * Checks if the given address is invalid.
     *
     * @param  address   the address to be checked
     * @return          true if the address is invalid, false otherwise
     */
    private static boolean isInvalidAddress(String address) {
        return address.equals("") || isValidAddressLength(address);
    }

    /**
     * Checks if the given phone number is invalid.
     *
     * @param  phone  the phone number to be checked
     * @return        true if the phone number is invalid, false otherwise
     */
    private static boolean isInvalidPhone(String phone) {
        return phone.equals("") || isValidPhoneLength(phone);
    }

    /**
     * Check if the given email address is in a valid format.
     *
     * @param  email    the email address to be checked
     * @return          true if the email address is in a valid format, false otherwise
     */
    private static boolean isValidEmailFormat(String email) {
        return email.contains("@") && email.contains(".");
    }

    /**
     * Checks if the length of the given name is valid.
     *
     * @param  name  the name to be checked
     * @return       true if the name length is either greater than or equal to 40 or less than 4, otherwise false
     */
    private static boolean isValidNameLength(String name) {
        return name.length() >= 40 || name.length() < 4;
    }

    /**
     * Checks if the length of the username is valid.
     *
     * @param  username  the username to be checked
     * @return           true if the username length is greater than or equal to 30 or less than 4, false otherwise
     */
    private static boolean isValidUsernameLength(String username) {
        return username.length() >= 30 || username.length() < 4;
    }

    /**
     * Checks if the length of the phone number is valid.
     *
     * @param  phone  the phone number to be checked
     * @return        true if the length is greater than or equal to 12 or less than 10, false otherwise
     */
    private static boolean isValidPhoneLength(String phone) {
        return phone.length() >= 12 || phone.length() < 10;
    }

    /**
     * Checks if the length of the given address is valid.
     *
     * @param  address  the address to be checked
     * @return          true if the address length is greater than or equal to 60 or less than 4, false otherwise
     */
    private static boolean isValidAddressLength(String address) {
        return address.length() >= 60 || address.length() < 4;
    }

}

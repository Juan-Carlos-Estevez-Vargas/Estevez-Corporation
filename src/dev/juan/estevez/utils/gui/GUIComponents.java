package dev.juan.estevez.utils.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.ValidateCharacters;

public final class GUIComponents extends JFrame {

    /**
     * Creates a password field with the specified bounds, color, font, and container.
     *
     * @param  bounds    an array of integers representing the bounds of the password field [x, y, width, height]
     * @param  color     the background color of the password field
     * @param  font      the font of the password field
     * @param  container the panel to which the password field is added
     * @return           the created password field
     */
    public static JPasswordField createPasswordField(int[] bounds, Color color, Font font, JPanel container) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        passwordField.setBackground(color);
        passwordField.setFont(font);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setForeground(Color.WHITE);
        container.add(passwordField);
        return passwordField;
    }

    /**
     * Creates a JLabel with an image and adds it to a JPanel container.
     *
     * @param  imagePath  the path of the image file
     * @param  bounds     an array of integers representing the bounds of the JLabel in the container.
     *                    The array must have four elements in the order: x-coordinate, y-coordinate, width, height.
     * @param  container  the JPanel container to which the JLabel will be added
     * @return            the created JLabel
     */
    public static JLabel createImageLabel(String imagePath, int[] bounds, JPanel container) {
        JLabel label = new JLabel();
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setIcon(new ImageIcon(imagePath));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(label);
        return label;
    }

    /**
     * Creates a JLabel with the specified text, bounds, font, color, and container.
     *
     * @param  text      the text to be displayed on the label
     * @param  bounds    an array of integers representing the x, y, width, and height of the label's bounds
     * @param  font      the font to be used for the label's text
     * @param  color     the color to be used for the label's text
     * @param  container the JPanel container to which the label will be added
     * @return           the created JLabel
     */
    public static JLabel createLabel(String text, int[] bounds, Font font, Color color, JPanel container) {
        JLabel label = new JLabel(text);
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setForeground(color);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setFocusable(false);
        container.add(label);
        return label;
    }

    /**
     * Creates a JTextField with the specified bounds, color, font, and container.
     *
     * @param  bounds    an array of four integers representing the x, y, width, and height of the text field
     * @param  color     the background color of the text field
     * @param  font      the font of the text field
     * @param  container the JPanel container to which the text field will be added
     * @return           the created JTextField
     */
    public static JTextField createTextField(int[] bounds, Color color, Font font, JPanel container) {
        JTextField textField = new JTextField();
        textField.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        textField.setBackground(color);
        textField.setFont(font);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(Color.WHITE);
        textField.requestFocus();
        container.add(textField);
        return textField;
    }

    /**
     * Creates a JButton with the specified text, bounds, color, font, and container.
     *
     * @param  text       the text to be displayed on the button
     * @param  bounds     an array of integers representing the bounds of the button (x, y, width, height)
     * @param  color      the background color of the button
     * @param  font       the font of the button
     * @param  container  the JPanel container to which the button will be added
     * @return            the created JButton
     */
    public static JButton createButton(String text, int[] bounds, Color color, Font font, JPanel container) {
        JButton button = new JButton(text);
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(button);
        return button;
    }

    /**
     * Creates a JButton with the specified icon, bounds, color, and container.
     *
     * @param  icon      the icon to be displayed on the button
     * @param  bounds    an array of integers representing the x, y, width, and height of the button's bounds
     * @param  color     the background color of the button
     * @param  container the JPanel container to which the button will be added
     * @return           the created JButton
     */
    public static JButton createButton(ImageIcon icon, int[] bounds, Color color, JPanel container) {
        JButton button = new JButton();
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(color);
        button.setIcon(icon);
        button.setBorder(null);
        button.setOpaque(true);
        button.setFocusable(false);
        container.add(button);
        return button;
    }

    /**
     * Creates a logout button and adds it to the specified container panel.
     *
     * @param  container  the panel to which the logout button will be added
     * @return            the created logout button
     */
    public static JButton createLogoutButton(JPanel container) {
        JButton btnLogout = new JButton(Constants.LOGOUT_TEXT);
		btnLogout.setBounds(470, 20, 120, 30);
		btnLogout.setFont(new Font("serif", Font.BOLD, 14));
		btnLogout.setBackground(Constants.BUTTON_COLOR);
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setHorizontalAlignment(SwingConstants.CENTER);
		btnLogout.setFocusable(false);
		container.add(btnLogout);    
        return btnLogout;    
    }

    /**
     * Creates a JSeparator with the given bounds, color, and container.
     *
     * @param  bounds    an array of integers representing the x, y, width, and height of the separator
     * @param  color     the background color of the separator
     * @param  container the JPanel container to add the separator to
     * @return           the created JSeparator
     */
    public static JSeparator createSeparator(int[] bounds, Color color, JPanel container) {
        JSeparator separator = new JSeparator();
        separator.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        separator.setBackground(color);
        container.add(separator);
        return separator;
    }
}

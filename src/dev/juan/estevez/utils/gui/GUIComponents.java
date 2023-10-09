package dev.juan.estevez.utils.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.HorizontalCenterComboBoxRenderer;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public final class GUIComponents extends JFrame {

    /**
     * Creates a JPasswordField with the specified bounds and adds it to the
     * specified container.
     *
     * @param bounds    an array of integers representing the x, y, width, and
     *                  height of the password field
     * @param container the JPanel to which the password field will be added
     * @return the created JPasswordField
     */
    public static JPasswordField createPasswordField(int[] bounds, JPanel container) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        passwordField.setBackground(Colors.TEXT_FIELD_COLOR.getValue());
        passwordField.setFont(Fonts.LABEL_FONT.getValue());
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setForeground(Color.WHITE);
        container.add(passwordField);
        return passwordField;
    }

    /**
     * Creates a JLabel with an image and adds it to a JPanel container.
     *
     * @param imagePath the path of the image file
     * @param bounds    an array of integers representing the bounds of the JLabel
     *                  in the container.
     *                  The array must have four elements in the order:
     *                  x-coordinate, y-coordinate, width, height.
     * @param container the JPanel container to which the JLabel will be added
     * @return the created JLabel
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
     * Creates a JLabel with the specified text and bounds, and adds it to the given
     * container.
     *
     * @param text      the text to be displayed on the label
     * @param bounds    an array of integers representing the x, y, width, and
     *                  height of the label
     * @param container the JPanel to which the label will be added
     * @return the created JLabel
     */
    public static JLabel createLabel(String text, int[] bounds, JPanel container) {
        JLabel label = new JLabel(text);
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setForeground(Colors.ERROR_COLOR.getValue());
        label.setFont(Fonts.LABEL_FONT.getValue());
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setFocusable(false);
        container.add(label);
        return label;
    }

    /**
     * Creates a JTextField with the specified bounds and adds it to the given
     * container.
     *
     * @param bounds    an array of integers representing the x, y, width, and
     *                  height of the text field
     * @param container the JPanel container to which the text field will be added
     * @return the created JTextField
     */
    public static JTextField createTextField(int[] bounds, JPanel container) {
        JTextField textField = new JTextField();
        textField.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        textField.setBackground(Colors.TEXT_FIELD_COLOR.getValue());
        textField.setFont(Fonts.LABEL_FONT.getValue());
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(Color.WHITE);
        textField.requestFocus();
        container.add(textField);
        return textField;
    }

    /**
     * Creates a JButton with the specified text, bounds, color, font, and
     * container.
     *
     * @param text      the text to be displayed on the button
     * @param bounds    an array of integers representing the bounds of the button
     *                  (x, y, width, height)
     * @param color     the background color of the button
     * @param font      the font of the button
     * @param container the JPanel container to which the button will be added
     * @return the created JButton
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
     * @param icon      the icon to be displayed on the button
     * @param bounds    an array of integers representing the x, y, width, and
     *                  height of the button's bounds
     * @param color     the background color of the button
     * @param container the JPanel container to which the button will be added
     * @return the created JButton
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
     * @param container the panel to which the logout button will be added
     * @return the created logout button
     */
    public static JButton createLogoutButton(JPanel container) {
        JButton btnLogout = new JButton(Constants.LOGOUT_TEXT);
        btnLogout.setBounds(470, 20, 120, 30);
        btnLogout.setFont(new Font("serif", Font.BOLD, 14));
        btnLogout.setBackground(Colors.BUTTON_COLOR.getValue());
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setHorizontalAlignment(SwingConstants.CENTER);
        btnLogout.setFocusable(false);
        container.add(btnLogout);
        return btnLogout;
    }

    /**
     * Creates a JSeparator with the given bounds and container.
     *
     * @param bounds    an array of integers representing the x, y, width, and
     *                  height of the separator
     * @param container the JPanel container to add the separator to
     * @return the created JSeparator
     */
    public static JSeparator createSeparator(int[] bounds, JPanel container) {
        JSeparator separator = new JSeparator();
        separator.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        separator.setBackground(Colors.ERROR_COLOR.getValue());
        container.add(separator);
        return separator;
    }

    /**
     * Creates a JTable with the given DefaultTableModel and adds it to the
     * specified JPanel container.
     *
     * @param model     the DefaultTableModel used to populate the JTable
     * @param container the JPanel container to which the JTable will be added
     * @return the created JTable
     */
    public static JTable createTable(DefaultTableModel model, JPanel container) {
        JTable table = new JTable(model);
        table.setForeground(Color.BLACK);
        table.setFont(Fonts.CONTENT_TABLE_FONT.getValue());
        table.getTableHeader().setFont(Fonts.HEADER_TABLE_FONT.getValue());
        container.add(table);
        return table;
    }

    /**
     * Creates a scroll panel containing a table and adds it to a container panel.
     *
     * @param table     the table to be added to the scroll panel
     * @param bounds    the bounds of the scroll panel
     * @param container the panel to which the scroll panel will be added
     * @return the created scroll panel
     */
    public static JScrollPane createScrollPanel(JTable table, int[] bounds, JPanel container) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        container.add(scrollPane);
        return scrollPane;
    }

    /**
     * Creates a JScrollPane with the given JTextPane and bounds.
     *
     * @param  pane   the JTextPane to be added to the scroll pane
     * @param  bounds an integer array containing the x, y, width, and height
     *                of the scroll pane
     * @return        the created JScrollPane
     */
    public static JScrollPane createScrollPanel(JTextPane pane, int[] bounds) {
        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        scrollPane.setViewportView(pane);
        return scrollPane;
    }

    /**
     * Creates a JComboBox and adds it to a JPanel container.
     *
     * @param bounds    an array consisting of the x, y, width, and height values of
     *                  the JComboBox
     * @param items     an array of strings that will be used as the items in the
     *                  JComboBox
     * @param container the JPanel container where the JComboBox will be added
     * @return the created JComboBox
     */
    public static JComboBox<String> createComboBox(int[] bounds, String[] items, JPanel container) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        comboBox.setBackground(Colors.TEXT_FIELD_COLOR.getValue());
        comboBox.setFont(Fonts.LABEL_FONT.getValue());
        comboBox.setForeground(Color.WHITE);
        comboBox.setRenderer(new HorizontalCenterComboBoxRenderer());
        container.add(comboBox);
        return comboBox;
    }

    /**
     * Creates a JTextPane and adds it to the specified container panel.
     *
     * @param  bounds     an array of integers representing the bounds of the JTextPane
     * @param  container  the JPanel container to which the JTextPane will be added
     * @return            the created JTextPane
     */
    public static JTextPane createTextPane(int[] bounds, JPanel container) {
        JTextPane textPane = new JTextPane();
        textPane.setFont(Fonts.CONTENT_TABLE_FONT.getValue());
        textPane.setForeground(Color.BLACK);
        container.add(createScrollPanel(textPane, bounds));
        return textPane;
    }
}

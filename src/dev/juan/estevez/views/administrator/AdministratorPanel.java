package dev.juan.estevez.views.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.GeneratePDFReport;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * Vista principal del administrador.
 *
 * @author Juan Carlos Estevez Vargas.
 */
public class AdministratorPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterUser;
	private JLabel labelManageUser;
	private JLabel labelPrintUsers;
	private JPanel panelBack;
	private JButton btnRegisterUser;
	private JButton btnManageUser;
	private JButton btnPrintUsers;
	private JButton btnRestorePass;
	private JButton btnLogout;
	private String user;
	private String nameUser;

	public AdministratorPanel() throws SQLException {
		this.user = LoginView.user;
		this.setSize(630, 280);
		this.setTitle("Administrador - Sesi�n de " + this.user);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
		this.getUserName();
	}

	/**
	 * Retrieves the user name from the database based on the provided username.
	 *
	 * @throws SQLException if there is an error retrieving the user name
	 */
	private void getUserName() throws SQLException {
		try (Connection connection = DatabaseConnection.connect();
				PreparedStatement statement = connection
						.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = ?")) {
			statement.setString(1, user);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					nameUser = resultSet.getString("nombre_usuario");
					this.labelTittle.setText(nameUser);
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error retrieving user name", e);
		}
	}

	/**
	 * Initializes the components of the Java function.
	 *
	 * @param None No parameters are required for this function.
	 * @return None This function does not return anything.
	 */
	public void initComponents() {
		initializeUI();
		setupEventHandlers();
	}

	/**
	 * Initializes the user interface components.
	 *
	 * @param None
	 * @return None
	 */
	private void initializeUI() {
		panelBack = new JPanel();
		panelBack.setBackground(new Color(46, 59, 104));
		panelBack.setLayout(null);
		setContentPane(panelBack);

		labelTittle = createLabel("", /* SwingConstants.CENTER, */ 10, 25, 410, 27, new Color(192, 192, 192),
				new Font("serif", Font.BOLD, 20));
		panelBack.add(labelTittle);

		btnRegisterUser = createButton(40, 80, 120, 100, "src/img/addUser.png", this::handleRegisterUser);
		labelRegisterUser = createLabel("Registrar Usuario", 45, 190, 120, 15, new Color(192, 192, 192),
				new Font("serif", Font.BOLD, 14));
		panelBack.add(btnRegisterUser);
		panelBack.add(labelRegisterUser);

		btnManageUser = createButton(250, 80, 120, 100, "src/img/informationuser.png", this::handleManageUser);
		labelManageUser = createLabel("Gestionar Usuarios", 250, 190, 120, 15, new Color(192, 192, 192),
				new Font("serif", Font.BOLD, 14));
		panelBack.add(btnManageUser);
		panelBack.add(labelManageUser);

		btnPrintUsers = createButton(460, 80, 120, 100, null, this::handlePrintUsers);
		labelPrintUsers = createLabel("Imprimir Usuarios", 460, 190, 120, 15, new Color(192, 192, 192),
				new Font("serif", Font.BOLD, 14));
		panelBack.add(btnPrintUsers);
		panelBack.add(labelPrintUsers);

		btnRestorePass = createButton(430, 20, 40, 30, "src/img/padlock.png", this::handleRestorePassword);
		btnLogout = createButton(470, 20, 120, 30, null, this::handleLogout);
		panelBack.add(btnRestorePass);
		panelBack.add(btnLogout);
	}

	/**
	 * Sets up event handlers for various buttons.
	 */
	private void setupEventHandlers() {
		btnRestorePass.addActionListener(this::handleRestorePassword);
		btnRegisterUser.addActionListener(this::handleRegisterUser);
		btnManageUser.addActionListener(this::handleManageUser);
		btnLogout.addActionListener(this::handleLogout);
		btnPrintUsers.addActionListener(this::handlePrintUsers);
	}

	/**
	 * Creates a JLabel with the specified properties.
	 *
	 * @param text   the text to be displayed on the label
	 * @param x      the x-coordinate of the label's position
	 * @param y      the y-coordinate of the label's position
	 * @param width  the width of the label
	 * @param height the height of the label
	 * @param color  the color of the label's text
	 * @param font   the font used for the label's text
	 * @return the created JLabel
	 */
	private JLabel createLabel(String text, int x, int y, int width, int height, Color color, Font font) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		label.setForeground(color);
		label.setFont(font);
		label.setFocusable(false);
		return label;
	}

	/**
	 * Creates a JButton with the specified position, size, icon, and action
	 * listener.
	 *
	 * @param x              the x-coordinate of the button's top-left corner
	 * @param y              the y-coordinate of the button's top-left corner
	 * @param width          the width of the button
	 * @param height         the height of the button
	 * @param iconPath       the path to the icon image file (null if no icon)
	 * @param actionListener the action listener to be registered with the button
	 * @return the created JButton
	 */
	private JButton createButton(int x, int y, int width, int height, String iconPath, ActionListener actionListener) {
		JButton button = new JButton();
		button.setBounds(x, y, width, height);
		if (iconPath != null) {
			button.setIcon(new ImageIcon(iconPath));
		}
		button.addActionListener(actionListener);
		button.setBorder(null);
		button.setBackground(new Color(46, 59, 104));
		button.setOpaque(true);
		button.setFocusable(false);
		return button;
	}

	/**
	 * Handles the restore password action event.
	 *
	 * @param e the action event object
	 */
	private void handleRestorePassword(ActionEvent e) {
		RestorePassword restorePassword = new RestorePassword();
		restorePassword.setVisible(true);
		dispose();
	}

	/**
	 * A description of the entire Java function.
	 *
	 * @param e the event that triggered the function
	 */
	private void handleRegisterUser(ActionEvent e) {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setVisible(true);
	}

	/**
	 * A description of the entire Java function.
	 *
	 * @param e the ActionEvent parameter
	 * @return no return value
	 */
	private void handleManageUser(ActionEvent e) {
		ManagementUsers managementUsers = new ManagementUsers();
		managementUsers.setVisible(true);
	}

	/**
	 * Handles the logout action event.
	 *
	 * @param e the action event
	 */
	private void handleLogout(ActionEvent e) {
		int confirmationResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de cerrar la sesión?");
		/*if (confirmationResult == JOptionPane.OK_OPTION) {
			Login login = new Login();
			login.setLocationRelativeTo(null);
			login.setVisible(true);
			dispose();
		}*/
	}

	/**
	 * Handles the action event for printing users.
	 *
	 * @param e the action event triggered
	 */
	private void handlePrintUsers(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int response = fc.showSaveDialog(this);

		if (response != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File chosenFile = fc.getSelectedFile();
		String outputPath = chosenFile.getAbsolutePath() + ".pdf";

		try {
			GeneratePDFReport.createPDF(outputPath);
			JOptionPane.showMessageDialog(null, "Listado de usuarios creada correctamente");
		} catch (IOException | DocumentException ex) {
			System.err.println("Error al generar PDF: " + ex);
			JOptionPane.showMessageDialog(null, "¡Error al generar PDF! Contacte al Administrador");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}

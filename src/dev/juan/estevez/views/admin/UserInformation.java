package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class UserInformation extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private int ID, idSuperAdministrador;
	private JLabel labelTittle;
	private JLabel labelName;
	private JLabel labelEmail;
	private JLabel labelPhone;
	private JLabel labelPermissionOf;
	private JLabel labelUsername;
	private JLabel labelStatus;
	private JLabel labelRegisterBy;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtUsername;
	private JTextField txtRegisterBy;
	private JComboBox<String> cmbLevels;
	private JComboBox<String> cmbStatus;
	private JPanel container;
	private JButton btnUpdate;

	public UserInformation() {
		initComponents();
		initializeUserInformation();
	}

	/**
	 * Initializes the user information.
	 */
	private void initializeUserInformation() {
		user = LoginView.user;
		user_update = ManagementUsersView.user_update;

		setupWindowProperties();
		setTitleAndLabels();
		loadUserData();
		loadSuperAdministratorData();
	}

	/**
	 * Sets up the properties of the window.
	 */
	private void setupWindowProperties() {
		setResizable(false);
		setSize(630, 360);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * Sets the title and labels for the user information.
	 */
	private void setTitleAndLabels() {
		setTitle("Información del usuario " + user_update + " - Sesión de " + user);
		labelTittle.setText("Información del usuario " + user_update);
	}

	/**
	 * Load user data from the database based on the given username.
	 *
	 * @param user_update the username to retrieve user data for
	 */
	private void loadUserData() {
		try {
			Connection connection = DatabaseConnection.connect();
			PreparedStatement pst = connection.prepareStatement("SELECT * FROM usuarios WHERE username = ?");
			pst.setString(1, user_update);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				ID = rs.getInt("id_usuario");
				txtName.setText(rs.getString("nombre_usuario"));
				txtEmail.setText(rs.getString("email"));
				txtPhone.setText(rs.getString("telefono"));
				txtUsername.setText(rs.getString("username"));
				txtRegisterBy.setText(rs.getString("registrado_por"));
				cmbLevels.setSelectedItem(rs.getString("tipo_nivel"));
				cmbStatus.setSelectedItem(rs.getString("estatus"));
			}

			rs.close();
			pst.close();
			connection.close();
		} catch (SQLException ex) {
			handleLoadError(ex);
		}
	}

	/**
	 * Loads the data of the super administrator.
	 */
	private void loadSuperAdministratorData() {
		try {
			Connection connection = DatabaseConnection.connect();
			PreparedStatement superAdministratorQuery = connection
					.prepareStatement("SELECT * FROM usuarios WHERE id_usuario = ?");
			superAdministratorQuery.setInt(1, 1);
			ResultSet resultSet = superAdministratorQuery.executeQuery();

			if (resultSet.next()) {
				idSuperAdministrador = resultSet.getInt("id_usuario");
			}

			resultSet.close();
			superAdministratorQuery.close();
			connection.close();
		} catch (SQLException ex) {
			handleLoadError(ex);
		}
	}

	/**
	 * Handles a load error by printing the error message and displaying an error
	 * dialog.
	 *
	 * @param ex the SQLException that occurred
	 */
	private void handleLoadError(SQLException ex) {
		System.err.println("Error en cargar usuario " + ex);
		JOptionPane.showMessageDialog(null, "¡Error al cargar! Contacte al Administrador");
	}

	/**
	 * Initializes the components of the class.
	 */
	public void initComponents() {
		createContainer();
		createLabels();
		createTextFields();
		createComboBoxes();
		createUpdateButton();
	}

	/**
	 * Creates a container panel and sets its background color, layout, and bounds.
	 *
	 * @param None
	 * @return None
	 */
	private void createContainer() {
		container = new JPanel();
		container.setBackground(new Color(46, 59, 104));
		container.setLayout(null);
		container.setBounds(630, 460, 630, 460);
		setContentPane(container);
	}

	/**
	 * Creates the labels for the user interface.
	 *
	 * @param None No parameters are needed for this function.
	 * @return None This function does not return anything.
	 */
	private void createLabels() {
		labelTittle = new JLabel("Información del Usuario");
		labelTittle.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		labelTittle.setForeground(new Color(192, 192, 192));
		labelTittle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTittle.setBounds(110, 10, 400, 30);
		container.add(labelTittle);

		// Create other labels similarly...
	}

	/**
	 * Creates text fields for various purposes.
	 *
	 * @param none
	 * @return none
	 */
	private void createTextFields() {
		txtName = createTextField(20, 70, 280, 30);
		txtEmail = createTextField(20, 130, 280, 30);
		txtPhone = createTextField(20, 190, 230, 30);
		txtUsername = createTextField(360, 70, 230, 30);
		txtRegisterBy = createTextField(360, 190, 230, 30);
		txtRegisterBy.setEnabled(false);
	}

	/**
	 * Creates a new JTextField with the specified position and dimensions.
	 *
	 * @param x      the x-coordinate of the text field
	 * @param y      the y-coordinate of the text field
	 * @param width  the width of the text field
	 * @param height the height of the text field
	 * @return the created JTextField
	 */
	private JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		textField.setBackground(new Color(127, 140, 141));
		textField.setFont(new Font("serif", Font.BOLD, 20));
		textField.setForeground(Color.WHITE);
		container.add(textField);
		return textField;
	}

	/**
	 * Creates the combo boxes.
	 *
	 * @param paramName description of parameter
	 * @return description of return value
	 */
	private void createComboBoxes() {
		cmbStatus = createComboBox(360, 130, 170, 30, new String[] { "Activo", "Inactivo" });
		cmbLevels = createComboBox(20, 250, 170, 30, new String[] { "Administrador", "Capturista", "Técnico" });
	}

	/**
	 * Create a JComboBox and add it to the container.
	 *
	 * @param x      the x coordinate of the JComboBox
	 * @param y      the y coordinate of the JComboBox
	 * @param width  the width of the JComboBox
	 * @param height the height of the JComboBox
	 * @param items  the array of items to be added to the JComboBox
	 * @return the created JComboBox
	 */
	private JComboBox<String> createComboBox(int x, int y, int width, int height, String[] items) {
		JComboBox<String> comboBox = new JComboBox<>(items);
		comboBox.setBounds(x, y, width, height);
		comboBox.setBackground(new Color(127, 140, 141));
		comboBox.setFont(new Font("serif", Font.BOLD, 20));
		comboBox.setForeground(Color.WHITE);
		container.add(comboBox);
		return comboBox;
	}

	/**
	 * Creates and initializes the update button.
	 */
	private void createUpdateButton() {
		btnUpdate = new JButton("Actualizar Usuario");
		btnUpdate.setBounds(380, 250, 210, 35);
		btnUpdate.setFont(new Font("serif", Font.BOLD, 20));
		btnUpdate.setBackground(new Color(8, 85, 224));
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnUpdate.addActionListener(this);
		container.add(btnUpdate);
	}

	/**
	 * A description of the actionPerformed method.
	 *
	 * @param e The ActionEvent object that triggered the event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			updateUser();
		}
	}

	/**
	 * Updates the user information.
	 *
	 * @param paramName description of parameter
	 * @return description of return value
	 */
	private void updateUser() {
		int cmbPermissions, cmbStatus, validation = 0;
		String name, email, phone, username, permissionsString = "", statusString = "";

		email = txtEmail.getText().trim();
		name = txtName.getText().trim();
		username = txtUsername.getText().trim();
		phone = txtPhone.getText().trim();
		cmbPermissions = cmbLevels.getSelectedIndex() + 1;
		cmbStatus = this.cmbStatus.getSelectedIndex() + 1;

		validation += validateField(email, txtEmail, 40, "EMAIL", "@", ".");
		validation += validateField(username, txtUsername, 30, "USERNAME");
		validation += validateField(name, txtName, 35, "NOMBRE");
		validation += validateField(phone, txtPhone, 12, "TELÉFONO");

		if (idSuperAdministrador == ID) {
			JOptionPane.showMessageDialog(null, "No es posible actualizar el super administrador");
			return;
		}

		if (validation == 0) {
			permissionsString = getPermissionString(cmbPermissions);
			statusString = getStatusString(cmbStatus);

			try {
				Connection connection = DatabaseConnection.connect();
				PreparedStatement usernameCheckStatement = connection.prepareStatement(
						"SELECT username FROM usuarios WHERE username = ? AND NOT id_usuario = ?");
				usernameCheckStatement.setString(1, username);
				usernameCheckStatement.setInt(2, ID);
				ResultSet usernameResultSet = usernameCheckStatement.executeQuery();

				if (usernameResultSet.next()) {
					txtUsername.setBackground(Color.red);
					JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible");
				} else {
					updateDatabase(name, email, phone, username, permissionsString, statusString);
					JOptionPane.showMessageDialog(null, "Modificación exitosa!!");
					dispose();
					ManagementUsersView managementUsers = new ManagementUsersView();
					managementUsers.setVisible(true);
				}

				usernameResultSet.close();
				usernameCheckStatement.close();
				connection.close();
			} catch (SQLException ex) {
				System.err.println("Error al actualizar " + ex);
			}
		} else if (validation == 4) {
			JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos");
		}
	}

	/**
	 * Validates a field in a form.
	 *
	 * @param fieldValue           the value of the field
	 * @param textField            the text field associated with the field
	 * @param maxLength            the maximum length allowed for the field value
	 * @param fieldName            the name of the field
	 * @param additionalConditions additional conditions to check for validity of
	 *                             the field value
	 * @return 1 if the field is invalid, 0 otherwise
	 */
	private int validateField(String fieldValue, JTextField textField, int maxLength, String fieldName,
			String... additionalConditions) {
		if (fieldValue.equals("") || fieldValue.length() >= maxLength) {
			textField.setBackground(Color.red);
			if (fieldValue.length() >= maxLength) {
				JOptionPane.showMessageDialog(null,
						"El campo " + fieldName + " no debe contener más de " + maxLength + " caracteres");
				textField.requestFocus();
			}
			for (String condition : additionalConditions) {
				if (!fieldValue.contains(condition)) {
					JOptionPane.showMessageDialog(null, "El campo " + fieldName + " no es válido");
					textField.requestFocus();
					break;
				}
			}
			return 1;
		}
		return 0;
	}

	/**
	 * Retrieves the permission string based on the given permission index.
	 *
	 * @param permissionIndex the index of the permission
	 * @return the corresponding permission string
	 */
	private String getPermissionString(int permissionIndex) {
		switch (permissionIndex) {
			case 1:
				return "Administrador";
			case 2:
				return "Capturista";
			case 3:
				return "Técnico";
			default:
				return "";
		}
	}

	/**
	 * Generates the status string based on the given status index.
	 *
	 * @param statusIndex the status index to generate the string for
	 * @return the status string ("Activo" or "Inactivo")
	 */
	private String getStatusString(int statusIndex) {
		return (statusIndex == 1) ? "Activo" : "Inactivo";
	}

	/**
	 * Updates the database with the given user information.
	 *
	 * @param name              the name of the user
	 * @param email             the email of the user
	 * @param phone             the phone number of the user
	 * @param username          the username of the user
	 * @param permissionsString the permissions string of the user
	 * @param statusString      the status string of the user
	 * @throws SQLException if there is an error updating the database
	 */
	private void updateDatabase(String name, String email, String phone, String username,
			String permissionsString, String statusString) throws SQLException {
		Connection connection = DatabaseConnection.connect();
		PreparedStatement updateStatement = connection.prepareStatement(
				"UPDATE usuarios SET nombre_usuario = ?, email = ?, telefono = ?, username = ?, tipo_nivel = ?, estatus = ? WHERE id_usuario = ?");
		updateStatement.setString(1, name);
		updateStatement.setString(2, email);
		updateStatement.setString(3, phone);
		updateStatement.setString(4, username);
		updateStatement.setString(5, permissionsString);
		updateStatement.setString(6, statusString);
		updateStatement.setInt(7, ID);
		updateStatement.executeUpdate();

		updateStatement.close();
		connection.close();
	}

}

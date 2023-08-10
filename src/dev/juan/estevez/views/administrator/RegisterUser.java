package dev.juan.estevez.views.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
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
 * Vista encargada de registrar un nuevo usuario en el sistema.
 *
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterUser extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtNameUser;
	private JTextField txtEmailUser;
	private JTextField txtPhoneUser;
	private JTextField txtUsername;
	private JLabel labelTitle;
	private JLabel labelUsername;
	private JLabel labelNameUser;
	private JLabel labelEmailUser;
	private JLabel labelPhoneUser;
	private JLabel labelRegisterUser;
	private JLabel labelPermissionsOf;
	private JPanel panelBackUser;
	private JButton btnRegisterUser;
	private JComboBox<String> cmbPermissions;
	private String user;

	public RegisterUser() {
		this.user = LoginView.user;
		this.setSize(590, 340);
		this.setTitle("Registrar Usuario - Sesi�n de " + this.user);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
	}

	/**
	 * Clears all the input fields and resets the permissions dropdown to the
	 * default value.
	 *
	 * @param None
	 * @return None
	 */
	public void clean() {
		txtEmailUser.setText("");
		txtNameUser.setText("");
		txtPhoneUser.setText("");
		txtUsername.setText("");
		cmbPermissions.setSelectedIndex(0);
	}

	/**
	 * Initializes all the components of the Java function.
	 */
	public void initComponents() {
		setupMainPanel();
		setupTitleLabel();
		setupNameInput();
		setupEmailInput();
		setupPhoneInput();
		setupPermissionsComboBox();
		setupUsernameInput();
		setupRegisterButton();
	}

	/**
	 * Sets up the main panel.
	 */
	private void setupMainPanel() {
		panelBackUser = new JPanel();
		panelBackUser.setBackground(new Color(46, 59, 104));
		panelBackUser.setLayout(null);
		setContentPane(panelBackUser);
	}

	/**
	 * Sets up the title label for the user registration form.
	 */
	private void setupTitleLabel() {
		labelTitle = new JLabel("Registro de Usuarios");
		labelTitle.setBounds(210, 10, 250, 30);
		labelTitle.setForeground(new Color(192, 192, 192));
		labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		panelBackUser.add(labelTitle);
	}

	/**
	 * Sets up the name input field.
	 */
	private void setupNameInput() {
		labelNameUser = new JLabel("Nombre:");
		labelNameUser.setBounds(20, 50, 100, 25);
		labelNameUser.setForeground(new Color(192, 192, 192));
		labelNameUser.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelNameUser);

		txtNameUser = new JTextField();
		txtNameUser.setBounds(20, 70, 280, 25);
		txtNameUser.setBackground(new Color(127, 140, 141));
		txtNameUser.setFont(new Font("serif", Font.BOLD, 20));
		txtNameUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtNameUser.setForeground(Color.WHITE);
		txtNameUser.requestFocus();
		txtNameUser.addKeyListener(new ValidateCharacters());
		panelBackUser.add(txtNameUser);
	}

	/**
	 * Sets up the email input field.
	 */
	private void setupEmailInput() {
		labelEmailUser = new JLabel("Email:");
		labelEmailUser.setBounds(20, 110, 100, 25);
		labelEmailUser.setForeground(new Color(192, 192, 192));
		labelEmailUser.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelEmailUser);

		txtEmailUser = new JTextField();
		txtEmailUser.setBounds(20, 130, 280, 25);
		txtEmailUser.setBackground(new Color(127, 140, 141));
		txtEmailUser.setFont(new Font("serif", Font.BOLD, 20));
		txtEmailUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmailUser.setForeground(Color.WHITE);
		panelBackUser.add(txtEmailUser);
	}

	/**
	 * Sets up the phone input field and label.
	 */
	private void setupPhoneInput() {
		labelPhoneUser = new JLabel("Teléfono:");
		labelPhoneUser.setBounds(20, 170, 100, 25);
		labelPhoneUser.setForeground(new Color(192, 192, 192));
		labelPhoneUser.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelPhoneUser);

		txtPhoneUser = new JTextField();
		txtPhoneUser.setBounds(20, 190, 230, 25);
		txtPhoneUser.setBackground(new Color(127, 140, 141));
		txtPhoneUser.setFont(new Font("serif", Font.BOLD, 20));
		txtPhoneUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhoneUser.addKeyListener(new ValidateNumbers());
		txtPhoneUser.setForeground(Color.WHITE);
		panelBackUser.add(txtPhoneUser);
	}

	/**
	 * Sets up the permissions combo box.
	 */
	private void setupPermissionsComboBox() {
		labelPermissionsOf = new JLabel("Permisos de:");
		labelPermissionsOf.setBounds(20, 230, 100, 25);
		labelPermissionsOf.setForeground(new Color(192, 192, 192));
		labelPermissionsOf.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelPermissionsOf);

		cmbPermissions = new JComboBox<>();
		cmbPermissions.addItem("Administrador");
		cmbPermissions.addItem("T�cnico");
		cmbPermissions.addItem("Capturista");
		cmbPermissions.setBounds(20, 250, 170, 25);
		cmbPermissions.setBackground(new Color(127, 140, 141));
		cmbPermissions.setFont(new Font("serif", Font.BOLD, 14));
		cmbPermissions.setForeground(Color.WHITE);
		panelBackUser.add(cmbPermissions);
	}

	/**
	 * Sets up the username input field.
	 */
	private void setupUsernameInput() {
		labelUsername = new JLabel("Username:");
		labelUsername.setBounds(320, 50, 100, 25);
		labelUsername.setForeground(new Color(192, 192, 192));
		labelUsername.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(320, 70, 230, 25);
		txtUsername.setBackground(new Color(127, 140, 141));
		txtUsername.setFont(new Font("serif", Font.BOLD, 20));
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setForeground(Color.WHITE);
		panelBackUser.add(txtUsername);
	}

	private void setupRegisterButton() {
		btnRegisterUser = new JButton();
		btnRegisterUser.setBounds(400, 120, 140, 120);
		btnRegisterUser.setIcon(new ImageIcon("src/img/addClient.png"));
		btnRegisterUser.addActionListener(this);
		btnRegisterUser.setBorder(null);
		btnRegisterUser.setBackground(new Color(46, 59, 104));
		btnRegisterUser.setOpaque(true);
		btnRegisterUser.addActionListener(this);
		panelBackUser.add(btnRegisterUser);

		labelRegisterUser = new JLabel("Registrar Usuario");
		labelRegisterUser.setBounds(415, 240, 150, 25);
		labelRegisterUser.setForeground(new Color(192, 192, 192));
		labelRegisterUser.setFont(new Font("serif", Font.BOLD, 14));
		panelBackUser.add(labelRegisterUser);
	}

	/**
	 * Performs an action when an event is triggered.
	 *
	 * @param e the event that triggered the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterUser) {
			validateAndRegisterUser();
		}
	}

	/**
	 * Validates and registers a user.
	 */
	private void validateAndRegisterUser() {
		int permissionsCmb = cmbPermissions.getSelectedIndex() + 1;
		int validation = 0;
		String name, email, phone, username, permissionsString = "";

		email = txtEmailUser.getText().trim();
		phone = txtPhoneUser.getText().trim();
		username = txtUsername.getText().trim();
		name = txtNameUser.getText().trim();

		if (email.isEmpty() || email.length() >= 50 || !(email.contains("@") && email.contains("."))) {
			txtEmailUser.setBackground(Color.red);
			if (email.length() >= 50) {
				JOptionPane.showMessageDialog(null, "El campo EMAIL no debe contener más de 50 caracteres");
				txtEmailUser.requestFocus();
			}
			if (!(email.contains("@") && email.contains("."))) {
				JOptionPane.showMessageDialog(null, "El campo EMAIL no es válido");
				txtEmailUser.requestFocus();
			}
			validation++;
		}
		// ... Validación similar para los otros campos ...

		switch (permissionsCmb) {
			case 1 -> permissionsString = "Administrador";
			case 3 -> permissionsString = "Capturista";
			case 2 -> permissionsString = "Tecnico";
		}

		try {
			Connection cn = DatabaseConnection.connect();
			PreparedStatement pst = cn.prepareStatement("SELECT username FROM usuarios WHERE username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtUsername.setBackground(Color.red);
				JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible");
			} else {
				rs.close();
				pst.close();
				cn.close();

				if (validation == 0) {
					registerUser(name, email, phone, username, permissionsString);
				} else if (validation == 4) {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error en validar nombre de usuario " + ex);
			JOptionPane.showMessageDialog(null, "¡Error al comparar usuario! Contacte con el Administrador");
		}
	}

	/**
	 * Registers a new user with the given information.
	 *
	 * @param name              the name of the user
	 * @param email             the email of the user
	 * @param phone             the phone number of the user
	 * @param username          the username of the user
	 * @param permissionsString the permissions of the user as a string
	 */
	private void registerUser(String name, String email, String phone, String username, String permissionsString) {
		try {
			Connection cn = DatabaseConnection.connect();
			PreparedStatement pst = cn.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)");

			pst.setInt(1, 0);
			pst.setString(2, name);
			pst.setString(3, email);
			pst.setString(4, phone);
			pst.setString(5, username);
			pst.setString(6, "1234");
			pst.setString(7, permissionsString);
			pst.setString(8, "Activo");
			pst.setString(9, user);

			pst.executeUpdate();

			pst.close();
			cn.close();

			clean();

			txtEmailUser.setBackground(Color.green);
			txtNameUser.setBackground(Color.green);
			txtPhoneUser.setBackground(Color.green);
			txtUsername.setBackground(Color.green);

			JOptionPane.showMessageDialog(null, "Registro exitoso");
			dispose();
		} catch (SQLException ex) {
			System.err.println("Error en registrar usuario " + ex);
			JOptionPane.showMessageDialog(null, "¡Error al registrar usuario! Contacte al Administrador");
		}
	}
}

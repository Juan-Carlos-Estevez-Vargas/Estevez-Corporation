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
import panel.utilities.Login;

/**
 * Vista encargada de registrar un nuevo usuario en el sistema.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class RegisterUser extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables
	 */
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

	/**
	 * Constructor de clase.
	 */
	public RegisterUser() {
		this.user = Login.user;
		this.setSize(590, 340);
		this.setTitle("Registrar Usuario - Sesión de " + this.user);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
	}

	/**
	 * Limpia los campos de texto.
	 */
	public void clean() {
		this.txtEmailUser.setText("");
		this.txtNameUser.setText("");
		this.txtPhoneUser.setText("");
		this.txtUsername.setText("");
		this.cmbPermissions.setSelectedIndex(0);
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel principal.
		 */
		this.panelBackUser = new JPanel();
		this.panelBackUser.setBackground(new Color(46, 59, 104));
		this.panelBackUser.setLayout(null);
		this.setContentPane(this.panelBackUser);

		/**
		 * Título de la ventana.
		 */
		this.labelTitle = new JLabel("Registro de Usuarios");
		this.labelTitle.setBounds(210, 10, 250, 30);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBackUser.add(this.labelTitle);

		/**
		 * Label Nombre.
		 */
		this.labelNameUser = new JLabel("Nombre:");
		this.labelNameUser.setBounds(20, 50, 100, 25);
		this.labelNameUser.setForeground(new Color(192, 192, 192));
		this.labelNameUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelNameUser);

		/**
		 * Campo de texto para ingresar el nombre del usuario a registrar.
		 */
		this.txtNameUser = new JTextField();
		this.txtNameUser.setBounds(20, 70, 280, 25);
		this.txtNameUser.setBackground(new Color(127, 140, 141));
		this.txtNameUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtNameUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtNameUser.setForeground(Color.WHITE);
		this.txtNameUser.requestFocus();
		this.txtNameUser.addKeyListener(new ValidateCharacters());
		this.panelBackUser.add(this.txtNameUser);

		/**
		 * Label Email.
		 */
		this.labelEmailUser = new JLabel("Email:");
		this.labelEmailUser.setBounds(20, 110, 100, 25);
		this.labelEmailUser.setForeground(new Color(192, 192, 192));
		this.labelEmailUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelEmailUser);

		/*
		 * Campo de texto para ingresar el Email del usuario a registrar.
		 */
		this.txtEmailUser = new JTextField();
		this.txtEmailUser.setBounds(20, 130, 280, 25);
		this.txtEmailUser.setBackground(new Color(127, 140, 141));
		this.txtEmailUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmailUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtEmailUser.setForeground(Color.WHITE);
		this.panelBackUser.add(this.txtEmailUser);

		/**
		 * Label Teléfono.
		 */
		this.labelPhoneUser = new JLabel("Teléfono:");
		this.labelPhoneUser.setBounds(20, 170, 100, 25);
		this.labelPhoneUser.setForeground(new Color(192, 192, 192));
		this.labelPhoneUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelPhoneUser);

		/**
		 * Campo de texto para ingresar el teléfono del usuario a registrar.
		 */
		this.txtPhoneUser = new JTextField();
		this.txtPhoneUser.setBounds(20, 190, 230, 25);
		this.txtPhoneUser.setBackground(new Color(127, 140, 141));
		this.txtPhoneUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhoneUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtPhoneUser.addKeyListener(new ValidateNumbers());
		this.txtPhoneUser.setForeground(Color.WHITE);
		this.panelBackUser.add(this.txtPhoneUser);

		/**
		 * Label Permisos de.
		 */
		this.labelPermissionsOf = new JLabel("Permisos de:");
		this.labelPermissionsOf.setBounds(20, 230, 100, 25);
		this.labelPermissionsOf.setForeground(new Color(192, 192, 192));
		this.labelPermissionsOf.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelPermissionsOf);

		/**
		 * ComboBox con los permisos existentes en la aplicación.
		 */
		this.cmbPermissions = new JComboBox<>();
		this.cmbPermissions.addItem("Administrador");
		this.cmbPermissions.addItem("Técnico");
		this.cmbPermissions.addItem("Capturista");
		this.cmbPermissions.setBounds(20, 250, 170, 25);
		this.cmbPermissions.setBackground(new Color(127, 140, 141));
		this.cmbPermissions.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbPermissions.setForeground(Color.WHITE);
		this.panelBackUser.add(this.cmbPermissions);

		/**
		 * Label Username.
		 */
		this.labelUsername = new JLabel("Username:");
		this.labelUsername.setBounds(320, 50, 100, 25);
		this.labelUsername.setForeground(new Color(192, 192, 192));
		this.labelUsername.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelUsername);

		/**
		 * Campo de texto para ingresar el username del usuario a registrar.
		 */
		this.txtUsername = new JTextField();
		this.txtUsername.setBounds(320, 70, 230, 25);
		this.txtUsername.setBackground(new Color(127, 140, 141));
		this.txtUsername.setFont(new Font("serif", Font.BOLD, 20));
		this.txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtUsername.setForeground(Color.WHITE);
		this.panelBackUser.add(this.txtUsername);

		/**
		 * Botón para registrar un usuario en el sistema.
		 */
		this.btnRegisterUser = new JButton();
		this.btnRegisterUser.setBounds(400, 120, 140, 120);
		this.btnRegisterUser.setIcon(new ImageIcon("src/img/addClient.png"));
		this.btnRegisterUser.addActionListener(this);
		this.btnRegisterUser.setBorder(null);
		this.btnRegisterUser.setBackground(new Color(46, 59, 104));
		this.btnRegisterUser.setOpaque(true);
		this.panelBackUser.add(this.btnRegisterUser);

		/**
		 * Label Registrar Usuario.
		 */
		this.labelRegisterUser = new JLabel("Registrar Usuario");
		this.labelRegisterUser.setBounds(415, 240, 150, 25);
		this.labelRegisterUser.setForeground(new Color(192, 192, 192));
		this.labelRegisterUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(this.labelRegisterUser);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Accción para registrar un nuevo usuario en el sistema.
		 */
		if (e.getSource() == this.btnRegisterUser) {
			int permissionsCmb, validation = 0;
			String name, email, phone, username, permissionsString = "";

			/**
			 * Guardamos lo que esta en los txt en las variables que acabamos de crear
			 */
			email = this.txtEmailUser.getText().trim();
			phone = this.txtPhoneUser.getText().trim();
			username = this.txtUsername.getText().trim();
			name = this.txtNameUser.getText().trim();
			permissionsCmb = this.cmbPermissions.getSelectedIndex() + 1; // Guardamos lo que esta en el comboBox

			/**
			 * Validacion de campos para que no queden vacios y no se pasen del rango de
			 * caracteres permitido.
			 */
			if (email.equals("") || email.length() >= 50 || !(email.contains("@") && email.contains("."))) {
				this.txtEmailUser.setBackground(Color.red);
				if (email.length() >= 50) {
					JOptionPane.showMessageDialog(null, "El campo EMAIL no debe contener más de 50 caracteres");
					this.txtEmailUser.requestFocus();
				}
				if (!(email.contains("@") && email.contains("."))) {
					JOptionPane.showMessageDialog(null, "El campo EMAIL no es válido");
					this.txtEmailUser.requestFocus();
				}
				validation++;
			}
			if (username.equals("") || username.length() >= 50) {
				this.txtUsername.setBackground(Color.red);
				if (username.length() >= 50) {
					JOptionPane.showMessageDialog(null, "El campo USERNAME no debe contener más de 50 caracteres");
					this.txtUsername.requestFocus();
				}
				validation++;
			}
			if (name.equals("") || name.length() >= 40 || name.length() < 4) {
				this.txtNameUser.setBackground(Color.red);
				if (name.length() >= 40 || name.length() < 4) {
					JOptionPane.showMessageDialog(null,
							"El campo NOMBRE no debe contener más de 40 caracteres ni menos de 4");
					this.txtNameUser.requestFocus();
				}
				validation++;
			}
			if (phone.equals("") || phone.length() >= 12 || phone.length() < 10) {
				this.txtPhoneUser.setBackground(Color.red);
				if (phone.length() >= 12 || phone.length() < 10) {
					JOptionPane.showMessageDialog(null,
							"El campo TELÉFONO no debe contener más de 12 caracteres ni menos de 10");
					this.txtPhoneUser.requestFocus();
				}
				validation++;
			}

			/**
			 * Convertimos lo que nos retorna el comboBox a String.
			 */
			switch (permissionsCmb) {
			case 1 -> permissionsString = "Administrador";
			case 3 -> permissionsString = "Capturista";
			case 2 -> permissionsString = "Tecnico";
			default -> {
			}
			}

			/**
			 * Validamos que no hayan usernames iguales (No se puede registrar un username
			 * igual a otro).
			 */
			try {
				Connection cn = DatabaseConnection.conectar();
				PreparedStatement pst = cn
						.prepareStatement("SELECT username FROM usuarios WHERE username = '" + username + "'");
				ResultSet rs = pst.executeQuery();

				/**
				 * Si la consulta encontro algo.
				 */
				if (rs.next()) {
					this.txtUsername.setBackground(Color.red);
					JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible");
					rs.close();
					pst.close();
					cn.close();
				} else {
					rs.close();
					pst.close();
					cn.close();

					/**
					 * Si no hubo ningun campo vacio podra registrar usuarios.
					 */
					if (validation == 0) {
						try {
							Connection cn2 = DatabaseConnection.conectar();
							PreparedStatement pst2 = cn2
									.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)");

							/**
							 * Seteamos los valores de los txt a la base de datos (Porque es una sentencia
							 * INSERT).
							 */
							pst2.setInt(1, 0);
							pst2.setString(2, name);
							pst2.setString(3, email);
							pst2.setString(4, phone);
							pst2.setString(5, username);
							pst2.setString(6, "1234");
							pst2.setString(7, permissionsString);
							pst2.setString(8, "Activo");
							pst2.setString(9, user);
							pst2.executeUpdate();

							pst2.close();
							cn2.close();

							/**
							 * Ahora procedemos a limpiar los campos de texto.
							 */
							clean();

							/**
							 * Iluminamos los campos de texto de color verde.
							 */
							this.txtEmailUser.setBackground(Color.green);
							this.txtNameUser.setBackground(Color.green);
							this.txtPhoneUser.setBackground(Color.green);
							this.txtUsername.setBackground(Color.green);
							JOptionPane.showMessageDialog(null, "Registro exitoso");
							this.dispose();

						} catch (SQLException ex) {
							System.err.println("Error en registrar usuario " + ex);
							JOptionPane.showMessageDialog(null,
									"¡¡Error al registrar usuario!! Contacte al Administrador");
						}
					} else if (validation == 4) {
						JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos");
					}
				}
			} catch (SQLException ex) {
				System.err.println("Error en validar nombre de usuario " + ex);
				JOptionPane.showMessageDialog(null, "¡¡Error al comparar usuario!! Contacte con el Administrador");
			}
		}

	}
}

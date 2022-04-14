package ventanas.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import modelo.DatabaseConnection;

/**
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
	private JPasswordField txtPassword;
	private JLabel labelRegisterUser;
	private JLabel labelNameUser;
	private JLabel labelEmailUser;
	private JLabel labelPhoneUser;
	private JLabel labelUsername;
	private JLabel labelPassword;
	private JLabel labelPermissionsOf;
	private JPanel panelBackUser;
	private JButton btnRegisterUser;
	private JComboBox<String> cmbPermissions;

	/**
	 * Constructor de clase.
	 */
	public RegisterUser() {
		this.setSize(630, 360);
		this.setTitle("Registrar Usuario");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
	}

	/**
	 * Limpia los campos de texto.
	 */
	public void Limpiar() {
		txtEmailUser.setText("");
		txtNameUser.setText("");
		txtPassword.setText("");
		txtPhoneUser.setText("");
		txtUsername.setText("");
		cmbPermissions.setSelectedIndex(0);
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
		this.setContentPane(panelBackUser);

		/**
		 * Título de la ventana.
		 */
		this.labelRegisterUser = new JLabel("Registro de Usuarios");
		this.labelRegisterUser.setBounds(210, 10, 250, 20);
		this.labelRegisterUser.setForeground(new Color(192, 192, 192));
		this.labelRegisterUser.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBackUser.add(labelRegisterUser);

		/**
		 * Label Nombre.
		 */
		this.labelNameUser = new JLabel("Nombre:");
		this.labelNameUser.setBounds(20, 50, 100, 25);
		this.labelNameUser.setForeground(new Color(192, 192, 192));
		this.labelNameUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelNameUser);

		/**
		 * Campo de texto para ingresar el nombre del usuario a registrar.
		 */
		this.txtNameUser = new JTextField();
		this.txtNameUser.setBounds(20, 70, 230, 25);
		this.txtNameUser.setBackground(new Color(127, 140, 141));
		this.txtNameUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtNameUser.setHorizontalAlignment(JLabel.LEFT);
		this.txtNameUser.setForeground(Color.WHITE);
		this.txtNameUser.requestFocus();
		this.panelBackUser.add(txtNameUser);

		/**
		 * Label Email.
		 */
		this.labelEmailUser = new JLabel("Email:");
		this.labelEmailUser.setBounds(20, 110, 100, 25);
		this.labelEmailUser.setForeground(new Color(192, 192, 192));
		this.labelEmailUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelEmailUser);

		/*
		 * Campo de texto para ingresar el Email del usuario a registrar.
		 */
		this.txtEmailUser = new JTextField();
		this.txtEmailUser.setBounds(20, 130, 230, 25);
		this.txtEmailUser.setBackground(new Color(127, 140, 141));
		this.txtEmailUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmailUser.setHorizontalAlignment(JLabel.LEFT);
		this.txtEmailUser.setForeground(Color.WHITE);
		this.panelBackUser.add(txtEmailUser);

		/**
		 * Label Teléfono.
		 */
		this.labelPhoneUser = new JLabel("Teléfono:");
		this.labelPhoneUser.setBounds(20, 170, 100, 25);
		this.labelPhoneUser.setForeground(new Color(192, 192, 192));
		this.labelPhoneUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelPhoneUser);

		/**
		 * Campo de texto para ingresar el teléfono del usuario a registrar.
		 */
		this.txtPhoneUser = new JTextField();
		this.txtPhoneUser.setBounds(20, 190, 230, 25);
		this.txtPhoneUser.setBackground(new Color(127, 140, 141));
		this.txtPhoneUser.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhoneUser.setHorizontalAlignment(JLabel.LEFT);
		this.txtPhoneUser.setForeground(Color.WHITE);
		this.panelBackUser.add(txtPhoneUser);

		/**
		 * Label Permisos de.
		 */
		this.labelPermissionsOf = new JLabel("Permisos de:");
		this.labelPermissionsOf.setBounds(20, 230, 100, 25);
		this.labelPermissionsOf.setForeground(new Color(192, 192, 192));
		this.labelPermissionsOf.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelPermissionsOf);

		/**
		 * ComboBox con los permisos existentes en la aplicación.
		 */
		this.cmbPermissions = new JComboBox<String>();
		this.cmbPermissions.addItem("Administrador");
		this.cmbPermissions.addItem("Técnico");
		this.cmbPermissions.addItem("Capturista");
		this.cmbPermissions.setBounds(20, 250, 170, 25);
		this.cmbPermissions.setBackground(new Color(127, 140, 141));
		this.cmbPermissions.setFont(new Font("serif", Font.BOLD, 14));
		this.cmbPermissions.setForeground(Color.WHITE);
		this.panelBackUser.add(cmbPermissions);

		/**
		 * Label Username.
		 */
		this.labelUsername = new JLabel("Username:");
		this.labelUsername.setBounds(360, 50, 100, 25);
		this.labelUsername.setForeground(new Color(192, 192, 192));
		this.labelUsername.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelUsername);

		/**
		 * Campo de texto para ingresar el username del usuario a registrar.
		 */
		this.txtUsername = new JTextField();
		this.txtUsername.setBounds(360, 70, 230, 25);
		this.txtUsername.setBackground(new Color(127, 140, 141));
		this.txtUsername.setFont(new Font("serif", Font.BOLD, 20));
		this.txtUsername.setHorizontalAlignment(JLabel.LEFT);
		this.txtUsername.setForeground(Color.WHITE);
		this.panelBackUser.add(txtUsername);
		
		/**
		 * Label Password.
		 */
		this.labelPassword = new JLabel("Password:");
		this.labelPassword.setBounds(360, 110, 100, 25);
		this.labelPassword.setForeground(new Color(192, 192, 192));
		this.labelPassword.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackUser.add(labelPassword);

		/**
		 * Campo de texto para ingresar la contraseña del usuario a registrar.
		 */
		this.txtPassword = new JPasswordField();
		this.txtPassword.setBounds(360, 130, 230, 25);
		this.txtPassword.setBackground(new Color(127, 140, 141));
		this.txtPassword.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPassword.setHorizontalAlignment(JLabel.LEFT);
		this.txtPassword.setForeground(Color.WHITE);
		this.panelBackUser.add(txtPassword);
		
		/**
		 * Botón para registrar un usuario en el sistema.
		 */
		this.btnRegisterUser = new JButton();
		this.btnRegisterUser.setBounds(470, 180, 120, 100);
		this.btnRegisterUser.setIcon(new ImageIcon("src/img/addUser.png"));
		this.btnRegisterUser.addActionListener(this);
		this.panelBackUser.add(btnRegisterUser);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Accción para registrar un nuevo usuario en el sistema.
		 */
		if (e.getSource() == this.btnRegisterUser) {

			int permissionsCmb, validation = 0;
			String name, email, phone, username, password, permissionsString = "";

			/**
			 * Guardamos lo que esta en los txt en las variables que acabamos de crear
			 */
			email = txtEmailUser.getText().trim();
			phone = txtPhoneUser.getText().trim();
			username = txtUsername.getText().trim();
			password = txtPassword.getText().trim();
			name = txtNameUser.getText().trim();
			permissionsCmb = cmbPermissions.getSelectedIndex() + 1; // Guardamos lo que esta en el comboBox

			/**
			 * Validacion de campos para que no queden vacios.
			 */
			if (email.equals("")) {
				txtEmailUser.setBackground(Color.red);
				validation++;
			}
			if (username.equals("")) {
				txtUsername.setBackground(Color.red);
				validation++;
			}
			if (password.equals("")) {
				txtPassword.setBackground(Color.red);
				validation++;
			}
			if (name.equals("")) {
				txtNameUser.setBackground(Color.red);
				validation++;
			}
			if (phone.equals("")) {
				txtPhoneUser.setBackground(Color.red);
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
					txtUsername.setBackground(Color.red);
					JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible");
					cn.close();
				} else {
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
							pst2.setString(6, password);
							pst2.setString(7, permissionsString);
							pst2.setString(8, "Activo");
							pst2.setString(9, "Hola");
							pst2.executeUpdate();
							cn2.close();

							/**
							 *  Ahora procedemos a limpiar los campos de texto.
							 */
							Limpiar();

							/**
							 *  Iluminamos los campos de texto de color verde.
							 */
							txtEmailUser.setBackground(Color.green);
							txtNameUser.setBackground(Color.green);
							txtPassword.setBackground(Color.green);
							txtPhoneUser.setBackground(Color.green);
							txtUsername.setBackground(Color.green);

							JOptionPane.showMessageDialog(null, "Registro exitoso");
							this.dispose();
						} catch (SQLException ex) {
							System.err.println("Error en registrar usuario " + ex);
							JOptionPane.showMessageDialog(null,
									"¡¡Error al registrar usuario!! Contacte al Administrador");
						}
					} else {
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
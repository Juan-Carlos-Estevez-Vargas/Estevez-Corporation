package panel.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import panel.utilities.Login;
import util.DatabaseConnection;
import util.ValidateCharacters;
import util.ValidateNumbers;

/**
 * Vista con la informaci�n de un usuario.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class UserInformation extends JFrame implements ActionListener {

	/**
	 * Declaraci�n de Variables.
	 */
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

	/**
	 * Constructor de clase.
	 */
	public UserInformation() {
		initComponents();
		this.user = Login.user;
		this.user_update = ManagementUsers.user_update; // Guardamos el usuario seleccionado en la tabla usuarios
		this.setResizable(false);
		this.setTitle("Informaci�n del usuario " + user_update + " - Sesi�n de " + user);
		this.setSize(630, 360);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.labelTittle.setText("Informaci�n del usuario " + this.user_update);

		/**
		 * Conectando con la base de datos para recuperar la informaci�n del usuario
		 * previamente seleccionado el la tabla de la vista ManagementUsers.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM usuarios WHERE username = '" + this.user_update + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				this.ID = rs.getInt("id_usuario");
				this.txtName.setText(rs.getString("nombre_usuario"));
				this.txtEmail.setText(rs.getString("email"));
				this.txtPhone.setText(rs.getString("telefono"));
				this.txtUsername.setText(rs.getString("username"));
				this.txtRegisterBy.setText(rs.getString("registrado_por"));
				this.cmbLevels.setSelectedItem(rs.getString("tipo_nivel"));
				this.cmbStatus.setSelectedItem(rs.getString("estatus"));
			}
			rs.close();
			pst.close();
			cn.close();
		} catch (SQLException ex) {
			System.err.println("Error en cargar usuario " + ex);
			JOptionPane.showMessageDialog(null, "��Error al cargar!! Contacte al Administrador");
		}

		/**
		 * B�squeda del usuario super administrador.
		 */
		try {
			Connection conecction = (Connection) DatabaseConnection.conectar();
			PreparedStatement superAdministrador = (PreparedStatement) conecction
					.prepareStatement("SELECT * FROM usuarios WHERE id_usuario = 1");
			ResultSet resultset = superAdministrador.executeQuery();

			if (resultset.next()) {
				this.idSuperAdministrador = resultset.getInt("id_usuario");
			}

			resultset.close();
			superAdministrador.close();
			conecction.close();
		} catch (SQLException ex) {
			System.err.println("Error en cargar usuario " + ex);
			JOptionPane.showMessageDialog(null, "��Error al cargar!! Contacte al Administrador");
		}
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(630, 460, 630, 460);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Informaci�n del Usuario");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(110, 10, 400, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Nombre.
		 */
		this.labelName = new JLabel("Nombre :");
		this.labelName.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelName.setForeground(new java.awt.Color(192, 192, 192));
		this.labelName.setBounds(20, 50, 100, 20);
		this.container.add(this.labelName);

		/**
		 * Label Email.
		 */
		this.labelEmail = new JLabel("Email :");
		this.labelEmail.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelEmail.setForeground(new java.awt.Color(192, 192, 192));
		this.labelEmail.setBounds(20, 110, 100, 20);
		this.container.add(this.labelEmail);

		/**
		 * Label Phone.
		 */
		this.labelPhone = new JLabel("Tel�fono :");
		this.labelPhone.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelPhone.setForeground(new java.awt.Color(192, 192, 192));
		this.labelPhone.setBounds(20, 170, 100, 20);
		this.container.add(this.labelPhone);

		/**
		 * Label Permisos de.
		 */
		this.labelPermissionOf = new JLabel("Permisos de :");
		this.labelPermissionOf.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelPermissionOf.setForeground(new java.awt.Color(192, 192, 192));
		this.labelPermissionOf.setBounds(20, 230, 100, 20);
		this.container.add(this.labelPermissionOf);

		/**
		 * Label Username.
		 */
		this.labelUsername = new JLabel("Username :");
		this.labelUsername.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelUsername.setForeground(new java.awt.Color(192, 192, 192));
		this.labelUsername.setBounds(360, 50, 100, 20);
		this.container.add(this.labelUsername);

		/**
		 * Label Estado.
		 */
		this.labelStatus = new JLabel("Estatus :");
		this.labelStatus.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelStatus.setForeground(new java.awt.Color(192, 192, 192));
		this.labelStatus.setBounds(360, 110, 100, 20);
		this.container.add(this.labelStatus);

		/**
		 * Label Registrado Por.
		 */
		this.labelRegisterBy = new JLabel("Registrado por :");
		this.labelRegisterBy.setFont(new java.awt.Font("Segoe UI", 1, 12));
		this.labelRegisterBy.setForeground(new java.awt.Color(192, 192, 192));
		this.labelRegisterBy.setBounds(360, 170, 100, 20);
		this.container.add(this.labelRegisterBy);

		/**
		 * Campo de texto con la informaci�n del nombre del usuario a actualizar.
		 */
		this.txtName = new JTextField();
		this.txtName.setBounds(20, 70, 280, 30);
		this.txtName.setBackground(new Color(127, 140, 141));
		this.txtName.setFont(new Font("serif", Font.BOLD, 20));
		this.txtName.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtName.setForeground(Color.WHITE);
		this.txtName.requestFocus();
		this.txtName.addKeyListener(new ValidateCharacters());
		this.container.add(this.txtName);

		/**
		 * Campo de texto con la informaci�n del email del usuario a actualizar.
		 */
		this.txtEmail = new JTextField();
		this.txtEmail.setBounds(20, 130, 280, 30);
		this.txtEmail.setBackground(new Color(127, 140, 141));
		this.txtEmail.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtEmail.setForeground(Color.WHITE);
		this.container.add(this.txtEmail);

		/**
		 * Campo de texto con la informaci�n del tel�fono del usuario a actualizar.
		 */
		this.txtPhone = new JTextField();
		this.txtPhone.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtPhone.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.txtPhone.setBounds(20, 190, 230, 30);
		this.txtPhone.setBackground(new Color(127, 140, 141));
		this.txtPhone.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhone.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtPhone.setForeground(Color.WHITE);
		this.txtPhone.addKeyListener(new ValidateNumbers());
		this.container.add(this.txtPhone);

		/**
		 * Campo de texto con la informaci�n del username del usuario a actualizar.
		 */
		this.txtUsername = new JTextField();
		this.txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtUsername.setBounds(360, 70, 230, 30);
		this.txtUsername.setBackground(new Color(127, 140, 141));
		this.txtUsername.setFont(new Font("serif", Font.BOLD, 20));
		this.txtUsername.setForeground(Color.WHITE);
		this.container.add(this.txtUsername);

		/**
		 * Campo de texto con la informaci�n de qui�n registr� el usuario a actualizar.
		 */
		this.txtRegisterBy = new JTextField();
		this.txtRegisterBy.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtRegisterBy.setEnabled(false);
		this.txtRegisterBy.setBounds(360, 190, 230, 30);
		this.txtRegisterBy.setBackground(new Color(127, 140, 141));
		this.txtRegisterBy.setFont(new Font("serif", Font.BOLD, 20));
		this.txtRegisterBy.setForeground(Color.WHITE);
		this.container.add(this.txtRegisterBy);

		/**
		 * ComboBox con la informaci�n del Status del usuario a actualizar.
		 */
		this.cmbStatus = new JComboBox<>();
		this.cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
		this.cmbStatus.setBounds(360, 130, 170, 30);
		this.cmbStatus.setBackground(new Color(127, 140, 141));
		this.cmbStatus.setFont(new Font("serif", Font.BOLD, 20));
		this.cmbStatus.setForeground(Color.WHITE);
		this.container.add(this.cmbStatus);

		/**
		 * ComboBox con la informaci�n del nivel del usuario a actualizar.
		 */
		this.cmbLevels = new JComboBox<>();
		this.cmbLevels.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Capturista", "Tecnico" }));
		this.cmbLevels.setBounds(20, 250, 170, 30);
		this.cmbLevels.setBackground(new Color(127, 140, 141));
		this.cmbLevels.setFont(new Font("serif", Font.BOLD, 20));
		this.cmbLevels.setForeground(Color.WHITE);
		this.container.add(this.cmbLevels);

		/**
		 * Bot�n encargado de actualizar el usuario en cuesti�n.
		 */
		this.btnUpdate = new JButton("Actualizar Usuario");
		this.btnUpdate.setBounds(380, 250, 210, 35);
		this.btnUpdate.setFont(new Font("serif", Font.BOLD, 20));
		this.btnUpdate.setBackground(new Color(8, 85, 224));
		this.btnUpdate.setForeground(Color.WHITE);
		this.btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnUpdate.addActionListener(this);
		this.container.add(this.btnUpdate);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Actualizaci�n del usuario en el sistema y la base de datos.
		 */
		if (e.getSource() == this.btnUpdate) {
			int cmbPermissions, cmbStatus, validation = 0;
			String name, email, phone, username, permissionsString = "", statusString = "";

			/**
			 * Guardamos lo que esta en los comboBox y en los campos de texto en las
			 * variables.
			 */
			email = this.txtEmail.getText().trim();
			name = this.txtName.getText().trim();
			username = this.txtUsername.getText().trim();
			phone = this.txtPhone.getText().trim();
			cmbPermissions = this.cmbLevels.getSelectedIndex() + 1;
			cmbStatus = this.cmbStatus.getSelectedIndex() + 1;

			/**
			 * Validacion de campos para que no queden vacios y no se pasen del rango de
			 * caracyeres permitido.
			 */
			if (email.equals("") || email.length() >= 40) {
				this.txtEmail.setBackground(Color.red);
				if (email.length() >= 40) {
					JOptionPane.showMessageDialog(null, "El campo EMAIL no debe contener m�s de 40 caracteres");
					this.txtEmail.requestFocus();
				}
				validation++;
			}
			if (username.equals("") || username.length() >= 30) {
				this.txtUsername.setBackground(Color.red);
				if (username.length() >= 30) {
					JOptionPane.showMessageDialog(null, "El campo USERNAME no debe contener m�s de 30 caracteres");
					this.txtUsername.requestFocus();
				}
				validation++;
			}
			if (name.equals("") || name.length() >= 35) {
				this.txtName.setBackground(Color.red);
				if (name.length() >= 35) {
					JOptionPane.showMessageDialog(null, "El campo NOMBRE no debe contener m�s de 35 caracteres");
					this.txtName.requestFocus();
				}
				validation++;
			}
			if (phone.equals("") || phone.length() >= 12) {
				this.txtPhone.setBackground(Color.red);
				if (phone.length() >= 12) {
					JOptionPane.showMessageDialog(null, "El campo TEL�FONO no debe contener m�s de 12 caracteres");
					this.txtPhone.requestFocus();
				}
				validation++;
			}

			/**
			 * Si el usuario NO es super Administrador entonces es posible actualizarlo.
			 */
			if (!(idSuperAdministrador == ID)) {

				/**
				 * Cuando verifiquemos que todos los campos esten llenos.
				 */
				if (validation == 0) {

					switch (cmbPermissions) {
					case 1 -> permissionsString = "Administrador";
					case 2 -> permissionsString = "Capturista";
					case 3 -> permissionsString = "Tecnico";
					default -> {
					}
					}

					if (cmbStatus == 1) {
						statusString = "Activo";
					} else if (cmbStatus == 2) {
						statusString = "Inactivo";
					}

					/**
					 * Realizamos la actualizacion en la base de datos
					 */
					try {
						Connection cn2 = (Connection) DatabaseConnection.conectar();
						PreparedStatement pst2 = (PreparedStatement) cn2
								.prepareStatement("SELECT username FROM usuarios WHERE username = '" + username
										+ "' AND NOT id_usuario = '" + ID + "'");
						ResultSet rs2 = pst2.executeQuery();

						if (rs2.next()) {
							this.txtUsername.setBackground(Color.red);
							JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible");
							rs2.close();
							pst2.close();
							cn2.close();
						} else {
							Connection cn3 = (Connection) DatabaseConnection.conectar();
							PreparedStatement pst3 = (PreparedStatement) cn3.prepareStatement(
									"UPDATE usuarios SET nombre_usuario = ?, email = ?, telefono = ?, username = ?, tipo_nivel = ?,"
											+ " estatus = ? WHERE id_usuario = '" + ID + "'");

							pst3.setString(1, name);
							pst3.setString(2, email);
							pst3.setString(3, phone);
							pst3.setString(4, username);
							pst3.setString(5, permissionsString);
							pst3.setString(6, statusString);
							pst3.executeUpdate();

							pst3.close();
							cn3.close();

							JOptionPane.showMessageDialog(null, "Modificaci�n exitosa!!");
							this.dispose();

							ManagementUsers managementUsers = new ManagementUsers();
							managementUsers.setVisible(true);
						}
					} catch (SQLException ex) {
						System.err.println("Error al actualizar " + ex);
					}
				} else if (validation == 4) {
					JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos");
				}
			} else {
				JOptionPane.showMessageDialog(null, "No es posible actualizar el super administrador");
			}

		}
	}
}
package ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import modelo.DatabaseConnection;

public class UserInformation extends JFrame implements ActionListener {

	String user = "", user_update = "";
	int ID;
	private JLabel labelTittle, labelName, labelEmail, labelPhone, labelPermissionOf, labelUsername, labelStatus,
			labelRegisterBy;
	private JTextField txtName, txtEmail, txtPhone, txtUsername, txtRegisterBy;
	private JComboBox cmbLevels, cmbStatus;
	private JPanel container;
	
	public UserInformation() {
		initComponents();
		// user = Login.user;
		user_update = ManagementUsers.user_update; // Guardamos el usuario seleccionado en la tabla usuarios

		this.setResizable(false);
		this.setTitle("Información del usuario " + user_update + " - Sesión de " + user);
		//this.setLocationRelativeTo(null);
		
		this.setSize(630, 460);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		labelTittle.setText("Información del usuario " + user_update);

		// Nos conectamos a la base de datos
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT * FROM usuarios WHERE username = '" + user_update + "'");
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

			cn.close();

		} catch (SQLException ex) {
			System.err.println("Error en cargar usuario " + ex);
			JOptionPane.showMessageDialog(null, "¡¡Error al cargar!! Contacte al Administrador");
		}
	}

	public void initComponents() {

		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(630, 460, 630, 460);
		this.setContentPane(container);

		labelTittle = new JLabel();
		labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
		labelTittle.setForeground(new java.awt.Color(255, 255, 255));
		labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelTittle.setText("Información del Usuario");
		labelTittle.setBounds(140, 10, 400, 30);
		this.container.add(labelTittle);

		labelName = new JLabel();
		labelName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelName.setForeground(new java.awt.Color(255, 255, 255));
		labelName.setText("Nombre :");
		labelName.setBounds(20, 50, 100, 20);
		this.container.add(labelName);

		labelEmail = new JLabel();
		labelEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelEmail.setForeground(new java.awt.Color(255, 255, 255));
		labelEmail.setText("Email :");
		labelEmail.setBounds(20, 110, 100, 20);
		this.container.add(labelEmail);

		labelPhone = new JLabel();
		labelPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelPhone.setForeground(new java.awt.Color(255, 255, 255));
		labelPhone.setText("Teléfono :");
		labelPhone.setBounds(20, 170, 100, 20);
		this.container.add(labelPhone);

		labelPermissionOf = new JLabel();
		labelPermissionOf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelPermissionOf.setForeground(new java.awt.Color(255, 255, 255));
		labelPermissionOf.setText("Permisos de :");
		labelPermissionOf.setBounds(20, 230, 100, 20);
		this.container.add(labelPermissionOf);

		labelUsername = new JLabel();
		labelUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelUsername.setForeground(new java.awt.Color(255, 255, 255));
		labelUsername.setText("Username :");
		labelUsername.setBounds(380, 50, 100, 20);
		this.container.add(labelUsername);

		labelStatus = new JLabel();
		labelStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelStatus.setForeground(new java.awt.Color(255, 255, 255));
		labelStatus.setText("Estatus :");
		labelStatus.setBounds(380, 110, 100, 20);
		this.container.add(labelStatus);

		labelRegisterBy = new JLabel();
		labelRegisterBy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		labelRegisterBy.setForeground(new java.awt.Color(255, 255, 255));
		labelRegisterBy.setText("Registrado por :");
		labelRegisterBy.setBounds(380, 170, 100, 20);

		txtName = new JTextField();
		txtName.setBackground(new java.awt.Color(153, 153, 255));
		txtName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		txtName.setForeground(new java.awt.Color(255, 255, 255));
		txtName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtName.setBounds(20, 70, 210, 30);
		this.container.add(txtName);

		txtEmail = new JTextField();
		txtEmail.setBackground(new java.awt.Color(153, 153, 255));
		txtEmail.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		txtEmail.setForeground(new java.awt.Color(255, 255, 255));
		txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtEmail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtEmail.setBounds(20, 130, 210, 30);
		this.container.add(txtEmail);

		txtPhone = new JTextField();
		txtPhone.setBackground(new java.awt.Color(153, 153, 255));
		txtPhone.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		txtPhone.setForeground(new java.awt.Color(255, 255, 255));
		txtPhone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtPhone.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtPhone.setBounds(20, 190, 210, 30);
		this.container.add(txtPhone);

		txtUsername = new JTextField();
		txtUsername.setBackground(new java.awt.Color(153, 153, 255));
		txtUsername.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		txtUsername.setForeground(new java.awt.Color(255, 255, 255));
		txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtUsername.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtUsername.setBounds(380, 70, 210, 30);
		this.container.add(txtUsername);

		txtRegisterBy = new JTextField();
		txtRegisterBy.setBackground(new java.awt.Color(153, 153, 255));
		txtRegisterBy.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		txtRegisterBy.setForeground(new java.awt.Color(255, 255, 255));
		txtRegisterBy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		txtRegisterBy.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		txtRegisterBy.setEnabled(false);
		txtRegisterBy.setBounds(380, 190, 210, 30);
		this.container.add(txtRegisterBy);

		cmbStatus = new JComboBox<String>();
		cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
		cmbStatus.setBounds(380, 130, 150, 30);
		this.container.add(cmbStatus);

		cmbLevels = new JComboBox<String>();
		cmbLevels.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Capturista", "Tecnico" }));
		cmbLevels.setBounds(20, 250, 150, 30);
		this.container.add(cmbLevels);

		// jButton_Actualizar.setBackground(new java.awt.Color(153, 153, 255));
		// jButton_Actualizar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		// jButton_Actualizar.setForeground(new java.awt.Color(255, 255, 255));
		// jButton_Actualizar.setText("Actualizar Usuario");
		// jButton_Actualizar.setBorder(null);
		// jButton_Actualizar.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// jButton_ActualizarActionPerformed(evt);
		// }
		// });
		// getContentPane().add(jButton_Actualizar, new
		// org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 210, 35));

		// jButton_RestaurarPassword.setBackground(new java.awt.Color(153, 153, 255));
		// jButton_RestaurarPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); //
		// NOI18N
		// jButton_RestaurarPassword.setForeground(new java.awt.Color(255, 255, 255));
		// jButton_RestaurarPassword.setText("Restaurar Password");
		// jButton_RestaurarPassword.setBorder(null);
		// jButton_RestaurarPassword.addActionListener(new
		// java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// jButton_RestaurarPasswordActionPerformed(evt);
		// }
		// });
		// getContentPane().add(jButton_RestaurarPassword, new
		// org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 210, 35));

		// jLabel_Footer.setText("Creado por Juan Carlos Estevez Vargas");
		// getContentPane().add(jLabel_Footer, new
		// org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, -1, -1));
		// getContentPane().add(jLabel_Wallpaper, new
		// org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 460));

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

	}
}

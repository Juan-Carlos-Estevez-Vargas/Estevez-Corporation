package ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;

/**
 * Vista para recuperar contraseña.
 * 
 * @author
 *
 */
public class RestorePasswordWithEmail extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel labelTittle;
	private JLabel labelNewPassword;
	private JLabel labelConfirmPassword;
	private JPasswordField txtnewPasswordField;
	private JPasswordField txtConfirmPasswordField;
	private JTextField txtNewPassword;
	private JTextField txtConfirmPassword;
	private JButton btnRestorePassword;
	private JButton btnEyeNewPassword;
	private JButton btnEyeConfirmPassword;
	private boolean eyeEstateConfirmPassword;
	private boolean eyeEstateNewPassword;
	private String email;

	/**
	 * Constructor de clase.
	 */
	public RestorePasswordWithEmail(String email) {
		initComponents();
		this.setSize(360, 280);
		this.setResizable(false);
		this.setTitle("Restaurar Contraseña");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.email = email;
	}

	/**
	 * Inicializa los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.container.setBounds(360, 270, 360, 270);
		this.setContentPane(this.container);

		/**
		 * Label Principal.
		 */
		this.labelTittle = new JLabel("Restaurar Contraseña");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 22));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(20, 10, 300, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label New Password.
		 */
		this.labelNewPassword = new JLabel("Ingrese su nueva contraseña.");
		this.labelNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 18));
		this.labelNewPassword.setForeground(new java.awt.Color(192, 192, 192));
		this.labelNewPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		this.labelNewPassword.setBounds(20, 50, 250, 20);
		this.container.add(this.labelNewPassword);
		
		/**
		 * Campo de texto de tipo JPasswordField para el nuevo password del usuario en cuestión.
		 */
		this.txtnewPasswordField = new JPasswordField();
		this.txtnewPasswordField.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtnewPasswordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtnewPasswordField.setBounds(20, 70, 250, 30);
		this.txtnewPasswordField.setBackground(new Color(127, 140, 141));
		this.txtnewPasswordField.setForeground(Color.WHITE);
		this.container.add(this.txtnewPasswordField);
		
		/**
		 * Campo de texto para el nuevo password del usuario en cuestión.
		 */
		this.txtNewPassword = new JTextField();
		this.txtNewPassword.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtNewPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtNewPassword.setBounds(20, 70, 250, 30);
		this.txtNewPassword.setBackground(new Color(127, 140, 141));
		this.txtNewPassword.setForeground(Color.WHITE);
		this.container.add(this.txtNewPassword);
		
		/**
		 * Botón para mostrar u ocultar el texto del campo de texto password.
		 */
		this.btnEyeNewPassword = new JButton();
		this.btnEyeNewPassword.setBounds(275, 70, 50, 30);
		this.btnEyeNewPassword.setBackground(new Color(46, 59, 104));
		this.btnEyeNewPassword.setIcon(new ImageIcon("src/img/ojo_opt.png"));
		this.btnEyeNewPassword.setBorder(null);
		this.btnEyeNewPassword.addActionListener(this);
		this.container.add(this.btnEyeNewPassword);

		/**
		 * Label New Password.
		 */
		this.labelConfirmPassword = new JLabel("Vuelva a ingresar la contraseña.");
		this.labelConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 18));
		this.labelConfirmPassword.setForeground(new java.awt.Color(192, 192, 192));
		this.labelConfirmPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		this.labelConfirmPassword.setBounds(20, 110, 300, 20);
		this.container.add(this.labelConfirmPassword);

		/**
		 * Campo de texto de tipo JPasswordFiel para el nuevo password del usuario en cuestión.
		 */
		this.txtConfirmPasswordField = new JPasswordField();
		this.txtConfirmPasswordField.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtConfirmPasswordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtConfirmPasswordField.setBounds(20, 130, 250, 30);
		this.txtConfirmPasswordField.setBackground(new Color(127, 140, 141));
		this.txtConfirmPasswordField.setForeground(Color.WHITE);
		this.container.add(this.txtConfirmPasswordField);
		
		/**
		 * Campo de texto para el nuevo password del usuario en cuestión.
		 */
		this.txtConfirmPassword = new JTextField();
		this.txtConfirmPassword.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtConfirmPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		this.txtConfirmPassword.setBounds(20, 130, 250, 30);
		this.txtConfirmPassword.setBackground(new Color(127, 140, 141));
		this.txtConfirmPassword.setForeground(Color.WHITE);
		this.container.add(this.txtConfirmPassword);
		
		/**
		 * Botón para mostrar u ocultar el texto del campo de texto password.
		 */
		this.btnEyeConfirmPassword = new JButton();
		this.btnEyeConfirmPassword.setBounds(275, 130, 50, 30);
		this.btnEyeConfirmPassword.setBackground(new Color(46, 59, 104));
		this.btnEyeConfirmPassword.setIcon(new ImageIcon("src/img/ojo_opt.png"));
		this.btnEyeConfirmPassword.setBorder(null);
		this.btnEyeConfirmPassword.addActionListener(this);
		this.container.add(this.btnEyeConfirmPassword);

		/**
		 * Botón para restaurar la contraseña del usuario en cuestión.
		 */
		this.btnRestorePassword = new JButton("Restaurar Password");
		this.btnRestorePassword.setFont(new java.awt.Font("Tahoma", 1, 18));
		this.btnRestorePassword.setBorder(null);
		this.btnRestorePassword.addActionListener(this);
		this.btnRestorePassword.setBounds(20, 180, 300, 35);
		this.btnRestorePassword.setBackground(new Color(8, 85, 224));
		this.btnRestorePassword.setForeground(Color.WHITE);
		this.container.add(this.btnRestorePassword);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnRestorePassword) {

			if (this.txtNewPassword.getText().trim().equals(this.txtConfirmPassword.getText().trim())) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement("UPDATE usuarios SET password = '"
							+ this.txtnewPasswordField.getText().trim() + "'  WHERE email = '" + this.email + "'");
					pst.executeUpdate();
					cn.close();
					this.dispose();

					/**
					 * Llamado al Login para que inicie sesión con su nuevo password.
					 */
					Login login = new Login();
					login.setVisible(true);
					login.setLocationRelativeTo(null);
				} catch (SQLException ex) {
					System.err.println("Error en consultar capturista");
				}
			} else {
				this.txtNewPassword.setBackground(Color.red);
				this.txtNewPassword.setBackground(Color.red);
				JOptionPane.showMessageDialog(null, "Los campos no coinciden.");
			}
		}
		
		/**
		 * Acción para que el usuario pueda visualizar el password digitado.
		 */
		if (e.getSource() == this.btnEyeNewPassword) {
			if (eyeEstateNewPassword == false) {
				this.txtNewPassword.setText(txtnewPasswordField.getText());
				this.txtNewPassword.setVisible(true);
				this.txtnewPasswordField.setVisible(false);
				this.eyeEstateNewPassword = true;
			} else {
				this.txtnewPasswordField.setText(txtnewPasswordField.getText());
				this.txtNewPassword.setVisible(false);
				this.txtnewPasswordField.setVisible(true);
				this.eyeEstateNewPassword = false;
			}
		}
		
		/**
		 * Acción para que el usuario pueda visualizar el password digitado.
		 */
		if (e.getSource() == this.btnEyeConfirmPassword) {
			if (eyeEstateConfirmPassword == false) {
				this.txtConfirmPassword.setText(txtConfirmPasswordField.getText());
				this.txtConfirmPassword.setVisible(true);
				this.txtConfirmPasswordField.setVisible(false);
				this.eyeEstateConfirmPassword = true;
			} else {
				this.txtConfirmPasswordField.setText(txtConfirmPasswordField.getText());
				this.txtConfirmPassword.setVisible(false);
				this.txtConfirmPasswordField.setVisible(true);
				this.eyeEstateConfirmPassword = false;
			}
		}
	}

}

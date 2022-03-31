package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import modelo.DatabaseConnection;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public final class Login extends JFrame implements ActionListener {

	private JPanel container;
	private JLabel jlLogo, jlUser, jlPassword, jlForgot, jlError;
	private JTextField txtUser, txtPassword2;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnEye;
	private JSeparator separator;
	private boolean eyeEstate;

	public Login() {
		this.setSize(350, 600);
		this.setTitle("Estevez Corporation");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.initComponents();
	}

	public void initComponents() {
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.setContentPane(container);

		this.jlLogo = new JLabel();
		this.jlLogo.setBounds(70, 50, 200, 150);
		this.jlLogo.setIcon(new ImageIcon("src/img/logo.png"));
		this.jlLogo.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlLogo);

		this.jlUser = new JLabel("Usuario:");
		this.jlUser.setBounds(45, 250, 210, 30);
		this.jlUser.setForeground(new Color(192, 192, 192));
		this.jlUser.setFont(new Font("serif", Font.BOLD, 20));
		this.jlUser.setHorizontalAlignment(JLabel.LEFT);
		this.container.add(jlUser);

		this.txtUser = new JTextField();
		this.txtUser.setBounds(45, 285, 250, 30);
		this.txtUser.setBackground(new Color(127, 140, 141));
		this.txtUser.setFont(new Font("serif", Font.BOLD, 22));
		this.txtUser.setHorizontalAlignment(JLabel.LEFT);
		this.txtUser.setForeground(Color.WHITE);
		this.txtUser.requestFocus();
		this.container.add(txtUser);

		this.jlPassword = new JLabel("Password:");
		this.jlPassword.setBounds(45, 320, 210, 30);
		this.jlPassword.setForeground(new Color(192, 192, 192));
		this.jlPassword.setFont(new Font("serif", Font.BOLD, 20));
		this.jlPassword.setHorizontalAlignment(JLabel.LEFT);
		this.container.add(jlPassword);

		this.txtPassword = new JPasswordField();
		this.txtPassword.setBounds(45, 355, 220, 30);
		this.txtPassword.setBackground(new Color(127, 140, 141));
		this.txtPassword.setFont(new Font("serif", Font.BOLD, 22));
		this.txtPassword.setForeground(Color.WHITE);
		this.container.add(txtPassword);

		this.txtPassword2 = new JTextField();
		this.txtPassword2.setBounds(45, 355, 220, 30);
		this.txtPassword2.setBackground(new Color(127, 140, 141));
		this.txtPassword2.setFont(new Font("serif", Font.BOLD, 22));
		this.txtPassword2.setForeground(Color.WHITE);
		this.txtPassword2.setVisible(false);
		this.container.add(txtPassword2);

		this.btnEye = new JButton();
		this.btnEye.setBounds(270, 355, 50, 30);
		this.btnEye.setBackground(new Color(46, 59, 104));
		this.btnEye.setIcon(new ImageIcon("src/img/ojo_opt.png"));
		this.btnEye.setBorder(null);
		this.btnEye.addActionListener(this);
		this.container.add(btnEye);

		this.jlError = new JLabel();
		this.jlError.setBounds(45, 390, 250, 25);
		this.jlError.setForeground(new Color(192, 192, 192));
		this.jlError.setFont(new Font("serif", Font.BOLD, 14));
		this.jlError.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlError);

		this.btnLogin = new JButton("Iniciar Sesión");
		this.btnLogin.setBounds(45, 435, 250, 45);
		this.btnLogin.setFont(new Font("serif", Font.BOLD, 22));
		this.btnLogin.setBackground(new Color(8, 85, 224));
		this.btnLogin.setForeground(Color.WHITE);
		this.btnLogin.setHorizontalAlignment(JButton.CENTER);
		this.btnLogin.addActionListener(this);
		this.container.add(btnLogin);

		this.separator = new JSeparator();
		this.separator.setBackground(new Color(192, 192, 192));
		this.separator.setBounds(45, 510, 250, 5);
		this.container.add(separator);

		this.jlForgot = new JLabel("¿Olvidó su contraseña?");
		this.jlForgot.setBounds(45, 515, 250, 35);
		this.jlForgot.setForeground(new Color(192, 192, 192));
		this.jlForgot.setFont(new Font("serif", Font.BOLD, 18));
		this.jlForgot.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlForgot);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String username;
		String password;

		if (e.getSource() == this.btnLogin) {
			// Recuperamos los datos introducidos en los TextField
			username = txtUser.getText().trim();
			password = txtPassword.getText().trim();

			// Validamos los campos
			if (!username.equals("") || !password.equals("")) { // Si el usuario o la contraseña no estan vacios
				try {
					Connection cn = (Connection) DatabaseConnection.conectar(); // Nos conectamos a la base de datos
					PreparedStatement pst = (PreparedStatement) cn
							.prepareStatement("SELECT tipo_nivel, estatus FROM usuarios WHERE username = '" + username
									+ "' AND password = '" + password + "'");
					ResultSet rs = pst.executeQuery(); // Ejecutamos el la consulta SQL

					// Si la consulta coincide con algo
					if (rs.next()) {
						// Creamos dos variables que almacenan el resultado de los campos de la consulta
						String tipo_nivel = rs.getString("tipo_nivel");
						String estatus = rs.getString("estatus");

						// Validamos los datos para saber a que interfaz ira el usuario
						if (tipo_nivel.equals("Administrador") && estatus.equalsIgnoreCase("Activo")) {
							this.dispose();
							new PanelAdministrador().setVisible(true);
						} else if (tipo_nivel.equals("Capturista") && estatus.equalsIgnoreCase("Activo")) {
							this.dispose();
							// new Capturista().setVisible(true);
						} else if (tipo_nivel.equals("Tecnico") && estatus.equalsIgnoreCase("Activo")) {
							this.dispose();
							// new Tecnico().setVisible(true);
						}
					} else {
						this.jlError.setText("Usuario y/o contraseña erróneos");
						this.txtUser.setText("");
						this.txtUser.requestFocus();
						this.txtPassword.setText("");
						this.txtPassword2.setText("");
					}
				} catch (SQLException ex) {
					System.err.println("Error en el boton Acceder." + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al iniciar sesion!! Contactate con el administrador.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
			}
		}

		if (e.getSource() == this.btnEye) {
			if (eyeEstate == false) {
				this.txtPassword2.setText(txtPassword.getText());
				this.txtPassword2.setVisible(true);
				this.txtPassword.setVisible(false);
				this.eyeEstate = true;
			} else {
				this.txtPassword.setText(txtPassword.getText());
				this.txtPassword2.setVisible(false);
				this.txtPassword.setVisible(true);
				this.eyeEstate = false;
			}
		}
	}

}

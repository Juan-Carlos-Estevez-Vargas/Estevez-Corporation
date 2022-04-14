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
import ventanas.administrator.AdministratorPanel;

/**
 *
 * @author Juan Carlos Estevez Vargas
 */
public final class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Declaración de Variables
	 */
	private JPanel container;
	private JLabel jlLogo;
	private JLabel jlUser;
	private JLabel jlPassword;
	private JLabel jlForgot;
	private JLabel jlError;
	private JTextField txtUser;
	private JTextField txtPassword2;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnEye;
	private JSeparator separator;
	private boolean eyeEstate;

	/**
	 * Constructor de clase
	 */
	public Login() {
		this.setSize(350, 600);
		this.setTitle("Estevez Corporation");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.initComponents();
	}

	/**
	 * Inicializa y construte los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * JPanel principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.setContentPane(container);

		/**
		 * Logo del Login.
		 */
		this.jlLogo = new JLabel();
		this.jlLogo.setBounds(70, 50, 200, 150);
		this.jlLogo.setIcon(new ImageIcon("src/img/logo.png"));
		this.jlLogo.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlLogo);

		/**
		 * JLabel Usuario.
		 */
		this.jlUser = new JLabel("Usuario:");
		this.jlUser.setBounds(45, 250, 210, 30);
		this.jlUser.setForeground(new Color(192, 192, 192));
		this.jlUser.setFont(new Font("serif", Font.BOLD, 20));
		this.jlUser.setHorizontalAlignment(JLabel.LEFT);
		this.container.add(jlUser);

		/**
		 * Campo de texto donde el usuario ingresa su usuario.
		 */
		this.txtUser = new JTextField();
		this.txtUser.setBounds(45, 285, 250, 30);
		this.txtUser.setBackground(new Color(127, 140, 141));
		this.txtUser.setFont(new Font("serif", Font.BOLD, 22));
		this.txtUser.setHorizontalAlignment(JLabel.CENTER);
		this.txtUser.setForeground(Color.WHITE);
		this.txtUser.requestFocus();
		this.container.add(txtUser);

		/**
		 * Label indicando al usuario que digite el password.
		 */
		this.jlPassword = new JLabel("Password:");
		this.jlPassword.setBounds(45, 320, 210, 30);
		this.jlPassword.setForeground(new Color(192, 192, 192));
		this.jlPassword.setFont(new Font("serif", Font.BOLD, 20));
		this.jlPassword.setHorizontalAlignment(JLabel.LEFT);
		this.container.add(jlPassword);

		/**
		 * Campo de texto de tipo password (***) para ingresar la contraseña del
		 * usuario.
		 */
		this.txtPassword = new JPasswordField();
		this.txtPassword.setBounds(45, 355, 220, 30);
		this.txtPassword.setBackground(new Color(127, 140, 141));
		this.txtPassword.setFont(new Font("serif", Font.BOLD, 22));
		this.txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtPassword.setForeground(Color.WHITE);
		this.container.add(txtPassword);

		/**
		 * Campo de texto con la misma información del JPassword inicial pero muestra el
		 * texto.
		 */
		this.txtPassword2 = new JTextField();
		this.txtPassword2.setBounds(45, 355, 220, 30);
		this.txtPassword2.setBackground(new Color(127, 140, 141));
		this.txtPassword2.setFont(new Font("serif", Font.BOLD, 22));
		this.txtPassword2.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtPassword2.setForeground(Color.WHITE);
		this.txtPassword2.setVisible(false);
		this.container.add(txtPassword2);

		/**
		 * Botón para mostrar u ocultar el texto del campo de texto password.
		 */
		this.btnEye = new JButton();
		this.btnEye.setBounds(270, 355, 50, 30);
		this.btnEye.setBackground(new Color(46, 59, 104));
		this.btnEye.setIcon(new ImageIcon("src/img/ojo_opt.png"));
		this.btnEye.setBorder(null);
		this.btnEye.addActionListener(this);
		this.container.add(btnEye);

		/**
		 * Label encargado de mostrar un error el caso que el usuario o la contraseña
		 * digitados sean erróneos.
		 */
		this.jlError = new JLabel();
		this.jlError.setBounds(45, 390, 250, 25);
		this.jlError.setForeground(new Color(192, 192, 192));
		this.jlError.setFont(new Font("serif", Font.BOLD, 14));
		this.jlError.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlError);

		/**
		 * Botón para realizar el logueo a la aplicación.
		 */
		this.btnLogin = new JButton("Iniciar Sesión");
		this.btnLogin.setBounds(45, 435, 250, 45);
		this.btnLogin.setFont(new Font("serif", Font.BOLD, 22));
		this.btnLogin.setBackground(new Color(8, 85, 224));
		this.btnLogin.setForeground(Color.WHITE);
		this.btnLogin.setHorizontalAlignment(JButton.CENTER);
		this.btnLogin.addActionListener(this);
		this.container.add(btnLogin);

		/**
		 * Separador de caracter visual.
		 */
		this.separator = new JSeparator();
		this.separator.setBackground(new Color(192, 192, 192));
		this.separator.setBounds(45, 510, 250, 5);
		this.container.add(separator);

		/**
		 * Label para recuperar la contraseña.
		 */
		this.jlForgot = new JLabel("¿Olvidó su contraseña?");
		this.jlForgot.setBounds(45, 515, 250, 35);
		this.jlForgot.setForeground(new Color(192, 192, 192));
		this.jlForgot.setFont(new Font("serif", Font.BOLD, 18));
		this.jlForgot.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlForgot);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		String username;
		String password;

		/**
		 * Acción para realizar el logueo a la aplicación.
		 */
		if (e.getSource() == this.btnLogin) {

			/**
			 * Recuperamos los datos introducidos en los TextField
			 */
			username = txtUser.getText().trim();
			password = txtPassword.getText().trim();

			/**
			 * Validamos que los campos no estén vacíos.
			 */
			if (!username.equals("") || !password.equals("")) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn
							.prepareStatement("SELECT tipo_nivel, estatus FROM usuarios WHERE username = '" + username
									+ "' AND password = '" + password + "'");
					ResultSet rs = pst.executeQuery();

					/**
					 * Si la consulta encuentra resultados.
					 */
					if (rs.next()) {
						/**
						 * Creamos dos variables que almacenan el resultado de los comboBox de la
						 * consulta
						 */
						String levelType = rs.getString("tipo_nivel");
						String status = rs.getString("estatus");

						/**
						 * Validamos los datos para saber a que interfaz irá el usuario
						 */
						if (levelType.equals("Administrador") && status.equalsIgnoreCase("Activo")) {
							this.dispose();
							new AdministratorPanel().setVisible(true);
						} else if (levelType.equals("Capturista") && status.equalsIgnoreCase("Activo")) {
							this.dispose();
							// new Capturista().setVisible(true);
						} else if (levelType.equals("Tecnico") && status.equalsIgnoreCase("Activo")) {
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

		/**
		 * Acción para que el usuario pueda visualizar el password digitado.
		 */
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

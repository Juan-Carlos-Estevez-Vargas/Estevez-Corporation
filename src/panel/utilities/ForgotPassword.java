package panel.utilities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.utils.database.DatabaseConnection;

/**
 * Vista para recuperar contrase�a por si el usuario olvid� la misma.
 *
 * @author
 *
 */
public class ForgotPassword extends JFrame implements ActionListener {

	/**
	 * Declaraci�n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel labelTittle;
	private JLabel labelEmailUser;
	private JTextField txtEmailUser;
	private JButton btnRestorePassword;

	/**
	 * Constructor de clase.
	 */
	public ForgotPassword() {
		initComponents();
		this.setSize(360, 210);
		this.setResizable(false);
		this.setTitle("�Olvid� su contrase�a?");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		this.labelTittle = new JLabel("�Olvid� su contrase�a?");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 22));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(20, 10, 300, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label New Password.
		 */
		this.labelEmailUser = new JLabel("Correo de recuperaci�n.");
		this.labelEmailUser.setFont(new java.awt.Font("Segoe UI", 0, 18));
		this.labelEmailUser.setForeground(new java.awt.Color(192, 192, 192));
		this.labelEmailUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		this.labelEmailUser.setBounds(20, 50, 250, 20);
		this.container.add(this.labelEmailUser);

		/**
		 * Campo de texto de tipo JPasswordField para el nuevo password del usuario en
		 * cuesti�n.
		 */
		this.txtEmailUser = new JTextField();
		this.txtEmailUser.setFont(new java.awt.Font("Segoe UI", 1, 16));
		this.txtEmailUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtEmailUser.setBounds(20, 70, 300, 30);
		this.txtEmailUser.setBackground(new Color(127, 140, 141));
		this.txtEmailUser.setForeground(Color.WHITE);
		this.container.add(this.txtEmailUser);

		/**
		 * Bot�n para restaurar la contrase�a del usuario en cuesti�n.
		 */
		this.btnRestorePassword = new JButton("Validar");
		this.btnRestorePassword.setFont(new java.awt.Font("Tahoma", 1, 18));
		this.btnRestorePassword.setBorder(null);
		this.btnRestorePassword.addActionListener(this);
		this.btnRestorePassword.setBounds(20, 110, 300, 35);
		this.btnRestorePassword.setBackground(new Color(8, 85, 224));
		this.btnRestorePassword.setForeground(Color.WHITE);
		this.container.add(this.btnRestorePassword);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnRestorePassword) {
			String email;
			try {
				Connection cn = (Connection) DatabaseConnection.connect();
				PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
						"SELECT * FROM usuarios WHERE email = '" + this.txtEmailUser.getText().trim() + "'");
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					email = rs.getString("email");
					if (email.equalsIgnoreCase(this.txtEmailUser.getText().trim())) {
						this.dispose();
						RestorePasswordWithEmail restorePasswordWithEmail = new RestorePasswordWithEmail(email);
						restorePasswordWithEmail.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"La direcci�n de correo electr�nico es err�nea o no existe en el sistema.");
				}
				cn.close();
			} catch (SQLException ex) {
				System.err.println("Error al intentar restaurar contrase�a en ForgotPassword " + ex.getMessage());
			}

		}

	}

}

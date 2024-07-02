package panel.utilities;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/*
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

/**
 * Vista para recuperar contrase�a.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class RestorePasswordWithEmail extends JFrame /*implements ActionListener*/ {

	/**
	 * Declaraci�n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel labelTittle;
	private JLabel labelIndications;
	private JTextPane textPaneIndications;
	private JScrollPane scrollIndications;
	private JButton btnRestorePassword;
	private String email;

	/**
	 * Constructor de clase.
	 */
	public RestorePasswordWithEmail(String email) {
		initComponents();
		this.setSize(360, 280);
		this.setResizable(false);
		this.setTitle("Restaurar Contrase�a");
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
		this.labelTittle = new JLabel("Restaurar Contrase�a");
		this.labelTittle.setFont(new java.awt.Font("Segoe UI", 0, 22));
		this.labelTittle.setForeground(new java.awt.Color(192, 192, 192));
		this.labelTittle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.labelTittle.setBounds(20, 10, 300, 30);
		this.container.add(this.labelTittle);

		/**
		 * Label Indicaciones.
		 */
		this.labelIndications = new JLabel("Indicaciones");
		this.labelIndications.setFont(new java.awt.Font("Segoe UI", 0, 18));
		this.labelIndications.setForeground(new java.awt.Color(192, 192, 192));
		this.labelIndications.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		this.labelIndications.setBounds(20, 50, 250, 20);
		this.container.add(this.labelIndications);

		/**
		 * Texto con las indicaciones suministradas.
		 */
		/**
		 * TextPane con las observaciones generales del equipo.
		 */
		this.textPaneIndications = new JTextPane();
		this.textPaneIndications.setForeground(Color.BLACK);
		this.textPaneIndications.setFont(new Font("serif", Font.BOLD, 14));
		this.textPaneIndications.setEditable(false);
		this.textPaneIndications.setText(
				"A continuaci�n, se enviar� la nueva contrase�a generada por el sistema a tu correo electr�nico, por favor revisa en spam o notificaciones, cuando tengas la clave ingresa al sistema y cambiala inmediatamente.");
		this.scrollIndications = new JScrollPane(this.textPaneIndications);
		this.scrollIndications.setBounds(20, 75, 300, 80);
		this.scrollIndications.setViewportView(this.textPaneIndications);
		this.container.add(this.scrollIndications);

		/**
		 * Bot�n para restaurar la contrase�a del usuario en cuesti�n.
		 */
		this.btnRestorePassword = new JButton("Restaurar Password");
		this.btnRestorePassword.setFont(new java.awt.Font("Tahoma", 1, 18));
		this.btnRestorePassword.setBorder(null);
		//this.btnRestorePassword.addActionListener(this);
		this.btnRestorePassword.setBounds(20, 180, 300, 35);
		this.btnRestorePassword.setBackground(new Color(8, 85, 224));
		this.btnRestorePassword.setForeground(Color.WHITE);
		this.container.add(this.btnRestorePassword);
	}

	//@Override
	/*public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnRestorePassword) {
			String newPassword = SendMail(email);
			if (newPassword != null) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
							"UPDATE usuarios SET password = '" + newPassword + "'  WHERE email = '" + this.email + "'");
					pst.executeUpdate();
					cn.close();
					this.dispose();
					
					JOptionPane.showMessageDialog(null, "Contrase�a enviada al correo");

					Login login = new Login();
					login.setVisible(true);
					login.setLocationRelativeTo(null);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Ocurri� un error inesperado. Contacta al Administrador");
				}
			}
		}
	}*/

	/*public String SendMail(String email) {
		String sender = "juank2001estevez@gmail.com";
		String password = "dguiukhvunpaqxfx";
		String emailReceived = email;
		String affair = "Clave Estevez Corporation";
		String messageToSend = "";
		String newPasswordGenerated = null;

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", sender);
			props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceived));
			message.setSubject(affair);
			newPasswordGenerated = "Sqrt.983@*kjdg";
			messageToSend = "Tu nueva contrase�a es la siguiente:\n\n" + newPasswordGenerated
					+ "\n\nSe recomienda cambiarla tan pronto ingreses al sistema.";
			message.setText(messageToSend);

			Transport t = session.getTransport("smtp");
			t.connect(sender, password);
			t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			t.close();
			return newPasswordGenerated;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"�Ocurri� un error al intentar enviar la clave al correo electr�nico! Contacta al Administrador");
		}
		return newPasswordGenerated;
	}*/

}

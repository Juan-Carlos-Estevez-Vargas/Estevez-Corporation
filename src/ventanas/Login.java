package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		this.jlError.setFont(new Font("serif", Font.BOLD, 22));
		this.jlError.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlError);

		this.btnLogin = new JButton("Iniciar Sesi�n");
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

		this.jlForgot = new JLabel("�Olvid� su contrase�a?");
		this.jlForgot.setBounds(45, 515, 250, 35);
		this.jlForgot.setForeground(new Color(192, 192, 192));
		this.jlForgot.setFont(new Font("serif", Font.BOLD, 18));
		this.jlForgot.setHorizontalAlignment(JLabel.CENTER);
		this.container.add(jlForgot);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			JOptionPane.showMessageDialog(null, "Iniciando Sesi�n");
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

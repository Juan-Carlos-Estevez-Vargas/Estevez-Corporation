package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import panel.utilities.Login;

/**
 * Interface encargada de registrar clientes al sistema.
 *
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class RegisterClient extends JFrame implements ActionListener {

	/**
	 * Declaración de Variables
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNameClient;
	private JTextField txtEmailClient;
	private JTextField txtPhoneClient;
	private JTextField txtAdressClient;
	private JLabel labelRegisterClient;
	private JLabel labelNameClient;
	private JLabel labelEmailClient;
	private JLabel labelPhoneClient;
	private JLabel labelAdressClient;
	private JLabel labelTitle;
	private JPanel panelBackClient;
	private JButton btnRegisterClient;
	private String user;

	/**
	 * Constructor de clase.
	 */
	public RegisterClient() {
		this.user = Login.user;
		this.setTitle("Registrar nuevo cliente - Sesión de " + this.user);
		this.setSize(480, 340);
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
		this.txtEmailClient.setText("");
		this.txtNameClient.setText("");
		this.txtAdressClient.setText("");
		this.txtPhoneClient.setText("");
	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel principal.
		 */
		this.panelBackClient = new JPanel();
		this.panelBackClient.setBackground(new Color(46, 59, 104));
		this.panelBackClient.setLayout(null);
		this.setContentPane(this.panelBackClient);

		/**
		 * Título de la ventana.
		 */
		this.labelTitle = new JLabel("Registro de Clientes");
		this.labelTitle.setBounds(160, 10, 250, 20);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBackClient.add(this.labelTitle);

		/**
		 * Label Nombre Cliente.
		 */
		this.labelNameClient = new JLabel("Nombre:");
		this.labelNameClient.setBounds(20, 50, 100, 25);
		this.labelNameClient.setForeground(new Color(192, 192, 192));
		this.labelNameClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelNameClient);

		/**
		 * Campo de texto para ingresar el nombre del cliente a registrar.
		 */
		this.txtNameClient = new JTextField();
		this.txtNameClient.setBounds(20, 70, 230, 25);
		this.txtNameClient.setBackground(new Color(127, 140, 141));
		this.txtNameClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtNameClient.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtNameClient.setForeground(Color.WHITE);
		this.txtNameClient.requestFocus();
		this.txtNameClient.addKeyListener(new ValidateCharacters());
		this.panelBackClient.add(this.txtNameClient);

		/**
		 * Label Email Cliente.
		 */
		this.labelEmailClient = new JLabel("Email:");
		this.labelEmailClient.setBounds(20, 110, 100, 25);
		this.labelEmailClient.setForeground(new Color(192, 192, 192));
		this.labelEmailClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelEmailClient);

		/*
		 * Campo de texto para ingresar el Email del Cliente a registrar.
		 */
		this.txtEmailClient = new JTextField();
		this.txtEmailClient.setBounds(20, 130, 230, 25);
		this.txtEmailClient.setBackground(new Color(127, 140, 141));
		this.txtEmailClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmailClient.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtEmailClient.setForeground(Color.WHITE);
		this.panelBackClient.add(this.txtEmailClient);

		/**
		 * Label Teléfono Cliente.
		 */
		this.labelPhoneClient = new JLabel("Teléfono:");
		this.labelPhoneClient.setBounds(20, 170, 100, 25);
		this.labelPhoneClient.setForeground(new Color(192, 192, 192));
		this.labelPhoneClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelPhoneClient);

		/**
		 * Campo de texto para ingresar el teléfono del Cliente a registrar.
		 */
		this.txtPhoneClient = new JTextField();
		this.txtPhoneClient.setBounds(20, 190, 230, 25);
		this.txtPhoneClient.setBackground(new Color(127, 140, 141));
		this.txtPhoneClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhoneClient.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtPhoneClient.setForeground(Color.WHITE);
		this.txtPhoneClient.addKeyListener(new ValidateNumbers());
		this.panelBackClient.add(this.txtPhoneClient);

		/**
		 * Label Dirección Cliente.
		 */
		this.labelAdressClient = new JLabel("Dirección:");
		this.labelAdressClient.setBounds(20, 230, 100, 25);
		this.labelAdressClient.setForeground(new Color(192, 192, 192));
		this.labelAdressClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelAdressClient);

		/**
		 * Campo de texto para ingresar la dirección del cliente a registrar.
		 */
		this.txtAdressClient = new JTextField();
		this.txtAdressClient.setBounds(20, 250, 230, 25);
		this.txtAdressClient.setBackground(new Color(127, 140, 141));
		this.txtAdressClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtAdressClient.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtAdressClient.setForeground(Color.WHITE);
		this.panelBackClient.add(this.txtAdressClient);

		/**
		 * Label Registrar Cliente.
		 */
		this.labelRegisterClient = new JLabel("Registrar Cliente:");
		this.labelRegisterClient.setBounds(300, 220, 150, 25);
		this.labelRegisterClient.setForeground(new Color(192, 192, 192));
		this.labelRegisterClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(this.labelRegisterClient);

		/**
		 * Botón para registrar un Cliente en el sistema.
		 */
		this.btnRegisterClient = new JButton();
		this.btnRegisterClient.setBounds(300, 100, 120, 120);
		this.btnRegisterClient.setIcon(new ImageIcon("src/img/registerClient.png"));
		this.btnRegisterClient.addActionListener(this);
		this.btnRegisterClient.setBorder(null);
		this.btnRegisterClient.setBackground(new Color(46, 59, 104));
		this.btnRegisterClient.setOpaque(true);
		this.panelBackClient.add(this.btnRegisterClient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Inserta un cliente en la base de datos.
		 */
		if (e.getSource() == this.btnRegisterClient) {
			int validation = 0;
			String name, mail, phone, adress;

			/**
			 * Recuperamos los valores de los text field
			 */
			name = this.txtNameClient.getText().trim();
			mail = this.txtEmailClient.getText().trim();
			phone = this.txtPhoneClient.getText().trim();
			adress = this.txtAdressClient.getText().trim();

			/**
			 * Validacion de los campos
			 */
			if (name.equals("") || name.length() >= 35) {
				this.txtNameClient.setBackground(Color.red);
				if (name.length() >= 35) {
					JOptionPane.showMessageDialog(null, "El campo NOMBRE no debe contener más de 35 caracteres");
					this.txtNameClient.requestFocus();
				}
				validation++;
			}
			if (mail.equals("") || mail.length() >= 40 || !(mail.contains("@") && mail.contains("."))) {
				this.txtEmailClient.setBackground(Color.red);
				if (mail.length() >= 40) {
					JOptionPane.showMessageDialog(null, "El campo EMAIL no debe contener más de 40 caracteres");
					this.txtEmailClient.requestFocus();
				}
				if (!(mail.contains("@") && mail.contains("."))) {
					JOptionPane.showMessageDialog(null, "El campo EMAIL no es válido");
					this.txtEmailClient.requestFocus();
				}
				validation++;
			}
			if (phone.equals("") || phone.length() >= 12) {
				this.txtPhoneClient.setBackground(Color.red);
				if (phone.length() >= 12 || phone.length() < 10) {
					JOptionPane.showMessageDialog(null, "El campo TELÉFONO no debe contener más de 12 caracteres ni menos de 10");
					this.txtPhoneClient.requestFocus();
				}
				validation++;
			}
			if (adress.equals("") || adress.length() >= 60) {
				this.txtAdressClient.setBackground(Color.red);
				if (adress.length() >= 60) {
					JOptionPane.showMessageDialog(null, "El campo DIRECCIÓN no debe contener más de 60 caracteres");
					this.txtAdressClient.requestFocus();
				}
				validation++;
			}

			/**
			 * Insertando clientes a la base de datos
			 */
			if (validation == 0) {
				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn
							.prepareStatement("INSERT INTO clientes VALUES (?,?,?,?,?,?)");

					pst.setInt(1, 0);
					pst.setString(2, name);
					pst.setString(3, mail);
					pst.setString(4, phone);
					pst.setString(5, adress);
					pst.setString(6, this.user);
					pst.executeUpdate();
					
					pst.close();
					cn.close();

					clean();

					this.txtNameClient.setBackground(Color.green);
					this.txtEmailClient.setBackground(Color.green);
					this.txtPhoneClient.setBackground(Color.green);
					this.txtAdressClient.setBackground(Color.green);

					JOptionPane.showMessageDialog(null, "Registro exitoso");
					this.dispose();
				} catch (SQLException ex) {
					System.err.println("Error en registrar Cliente " + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al registrar cliente, contacte al Administrador!!");
				}
			} else if (validation == 4) {
				JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos");
			}
		}

	}
}

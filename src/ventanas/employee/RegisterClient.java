package ventanas.employee;

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
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;

/**
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

	/**
	 * Constructor de clase.
	 */
	public RegisterClient() {
		this.setSize(530, 350);
		this.setTitle("Registrar Cliente");
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
		txtEmailClient.setText("");
		txtNameClient.setText("");
		txtAdressClient.setText("");
		txtPhoneClient.setText("");
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
		this.setContentPane(panelBackClient);

		/**
		 * Título de la ventana.
		 */
		this.labelTitle = new JLabel("Registro de Clientes");
		this.labelTitle.setBounds(160, 10, 250, 20);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBackClient.add(labelTitle);

		/**
		 * Label Nombre.
		 */
		this.labelNameClient = new JLabel("Nombre:");
		this.labelNameClient.setBounds(20, 50, 100, 25);
		this.labelNameClient.setForeground(new Color(192, 192, 192));
		this.labelNameClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(labelNameClient);

		/**
		 * Campo de texto para ingresar el nombre del usuario a registrar.
		 */
		this.txtNameClient = new JTextField();
		this.txtNameClient.setBounds(20, 70, 230, 25);
		this.txtNameClient.setBackground(new Color(127, 140, 141));
		this.txtNameClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtNameClient.setHorizontalAlignment(JLabel.LEFT);
		this.txtNameClient.setForeground(Color.WHITE);
		this.txtNameClient.requestFocus();
		this.panelBackClient.add(txtNameClient);

		/**
		 * Label Email.
		 */
		this.labelEmailClient = new JLabel("Email:");
		this.labelEmailClient.setBounds(20, 110, 100, 25);
		this.labelEmailClient.setForeground(new Color(192, 192, 192));
		this.labelEmailClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(labelEmailClient);

		/*
		 * Campo de texto para ingresar el Email del usuario a registrar.
		 */
		this.txtEmailClient = new JTextField();
		this.txtEmailClient.setBounds(20, 130, 230, 25);
		this.txtEmailClient.setBackground(new Color(127, 140, 141));
		this.txtEmailClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtEmailClient.setHorizontalAlignment(JLabel.LEFT);
		this.txtEmailClient.setForeground(Color.WHITE);
		this.panelBackClient.add(txtEmailClient);

		/**
		 * Label Teléfono.
		 */
		this.labelPhoneClient = new JLabel("Teléfono:");
		this.labelPhoneClient.setBounds(20, 170, 100, 25);
		this.labelPhoneClient.setForeground(new Color(192, 192, 192));
		this.labelPhoneClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(labelPhoneClient);

		/**
		 * Campo de texto para ingresar el teléfono del usuario a registrar.
		 */
		this.txtPhoneClient = new JTextField();
		this.txtPhoneClient.setBounds(20, 190, 230, 25);
		this.txtPhoneClient.setBackground(new Color(127, 140, 141));
		this.txtPhoneClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtPhoneClient.setHorizontalAlignment(JLabel.LEFT);
		this.txtPhoneClient.setForeground(Color.WHITE);
		this.panelBackClient.add(txtPhoneClient);

		/**
		 * Label Dirección.
		 */
		this.labelAdressClient = new JLabel("Dirección:");
		this.labelAdressClient.setBounds(20, 230, 100, 25);
		this.labelAdressClient.setForeground(new Color(192, 192, 192));
		this.labelAdressClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(labelAdressClient);

		/**
		 * Campo de texto para ingresar la dirección del cliente a registrar.
		 */
		this.txtAdressClient = new JTextField();
		this.txtAdressClient.setBounds(20, 250, 230, 25);
		this.txtAdressClient.setBackground(new Color(127, 140, 141));
		this.txtAdressClient.setFont(new Font("serif", Font.BOLD, 20));
		this.txtAdressClient.setHorizontalAlignment(JLabel.LEFT);
		this.txtAdressClient.setForeground(Color.WHITE);
		this.panelBackClient.add(txtAdressClient);

		/**
		 * Label Password.
		 */
		this.labelRegisterClient = new JLabel("Registrar Cliente:");
		this.labelRegisterClient.setBounds(350, 200, 100, 25);
		this.labelRegisterClient.setForeground(new Color(192, 192, 192));
		this.labelRegisterClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBackClient.add(labelRegisterClient);

		/**
		 * Botón para registrar un cliente en el sistema.
		 */
		this.btnRegisterClient = new JButton();
		this.btnRegisterClient.setBounds(350, 100, 70, 70);
		this.btnRegisterClient.setIcon(new ImageIcon("src/img/add.png"));
		this.btnRegisterClient.addActionListener(this);
		this.panelBackClient.add(btnRegisterClient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnRegisterClient) {
			int validation = 0;
			String name, mail, phone, adress;

			// Recuperamos los valores de los text field
			name = txtNameClient.getText().trim();
			mail = txtEmailClient.getText().trim();
			phone = txtPhoneClient.getText().trim();
			adress = txtAdressClient.getText().trim();

			// Validacion de los campos
			if (name.equals("")) {
				txtNameClient.setBackground(Color.red);
				validation++;
			}
			if (mail.equals("")) {
				txtEmailClient.setBackground(Color.red);
				validation++;
			}
			if (phone.equals("")) {
				txtPhoneClient.setBackground(Color.red);
				validation++;
			}
			if (adress.equals("")) {
				txtAdressClient.setBackground(Color.red);
				validation++;
			}

			// Insertando clientes a la base de datos
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
					pst.setString(6, "hola");

					pst.executeUpdate();
					cn.close();

					Limpiar();

					txtNameClient.setBackground(Color.green);
					txtEmailClient.setBackground(Color.green);
					txtPhoneClient.setBackground(Color.green);
					txtAdressClient.setBackground(Color.green);

					JOptionPane.showMessageDialog(null, "Registro exitoso");

					this.dispose();

				} catch (SQLException ex) {
					System.err.println("Error en registrar Cliente " + ex);
					JOptionPane.showMessageDialog(null, "¡¡Error al registrar cliente, contacte al Administrador!!");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Debes de llenar todos los campos");
			}
		}

	}
}

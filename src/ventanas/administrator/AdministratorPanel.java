package ventanas.administrator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class AdministratorPanel extends JFrame implements ActionListener {

	/**
	 * Definición de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterUser;
	private JLabel labelManageUser;
	private JPanel panelBack;
	private JButton btnRegisterUser;
	private JButton btnManageUser;
	private JButton btnPrueba;
	private JButton btnRole;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public AdministratorPanel() {
		this.user = Login.user;
		this.setSize(670, 310);
		this.setTitle("Administrador - Sesión de " + this.user);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();

		/**
		 * Recuperando el nombre del usuario.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText(nameUser);
			}
		} catch (Exception e) {
			System.err.println("Error en conexión desde la interfaz Administrador");
		}
	}

	/**
	 * Inicializa y construye los componentes Swing en el Frame principal.
	 */
	public void initComponents() {

		/**
		 * Panel principal.
		 */
		this.panelBack = new JPanel();
		this.panelBack.setBackground(new Color(46, 59, 104));
		this.panelBack.setLayout(null);
		this.setContentPane(this.panelBack);

		/*
		 * Label Usuario Logueado.
		 */
		this.labelTittle = new JLabel();
		this.labelTittle.setBounds(10, 10, 280, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(this.labelTittle);

		/*
		 * Botón para registrar un nuevo Usuario en el sistema.
		 */
		this.btnRegisterUser = new JButton();
		this.btnRegisterUser.setBounds(40, 80, 120, 100);
		this.btnRegisterUser.setIcon(new ImageIcon("src/img/addUser.png"));
		this.btnRegisterUser.addActionListener(this);
		this.panelBack.add(this.btnRegisterUser);

		/**
		 * Label Registrar Usuario.
		 */
		this.labelRegisterUser = new JLabel("Registrar Usuario");
		this.labelRegisterUser.setBounds(45, 190, 120, 15);
		this.labelRegisterUser.setForeground(new Color(192, 192, 192));
		this.labelRegisterUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelRegisterUser);

		/**
		 * Botón encargado de mostrar la lista de usuarios registrados en el sistema.
		 */
		this.btnManageUser = new JButton();
		this.btnManageUser.setBounds(270, 80, 120, 100);
		this.btnManageUser.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageUser.addActionListener(this);
		this.panelBack.add(this.btnManageUser);

		/**
		 * Label Gestionar Usuarios.
		 */
		this.labelManageUser = new JLabel("Gestionar Usuarios");
		this.labelManageUser.setBounds(270, 190, 120, 15);
		this.labelManageUser.setForeground(new Color(192, 192, 192));
		this.labelManageUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManageUser);

		/**
		 * Botón para modificar después.
		 */
		this.btnPrueba = new JButton();
		this.btnPrueba.setBounds(500, 80, 120, 100);
		this.btnPrueba.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.panelBack.add(this.btnPrueba);

		/**
		 * ComboBox encargado de mostrar los roles a los que puede acceder el usuario
		 * Administrador junto con el apartado de cerrar sesión.
		 */
		this.btnRole = new JButton("Cerrar Sesión");
		this.btnRole.setBounds(500, 20, 120, 30);
		this.btnRole.setFont(new Font("serif", Font.BOLD, 14));
		this.btnRole.setBackground(new Color(8, 85, 224));
		this.btnRole.setForeground(Color.WHITE);
		this.btnRole.setHorizontalAlignment(JButton.CENTER);
		this.btnRole.addActionListener(this);
		this.panelBack.add(this.btnRole);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Objetos de las clases (ventanas) a utilizar.
		 */
		RegisterUser registerUser;
		ManagementUsers managementUsers;

		/**
		 * Muestra el panel para registrar un usuario si se seleccionó dicha acción.
		 */
		if (e.getSource() == this.btnRegisterUser) {
			registerUser = new RegisterUser();
			registerUser.setVisible(true);
		}

		/**
		 * Muestra el panel con el listado de los usuarios registrados si se seleccionó
		 * dicha acción.
		 */
		if (e.getSource() == this.btnManageUser) {
			managementUsers = new ManagementUsers();
			managementUsers.setVisible(true);
		}
		
		if(e.getSource() == this.btnRole) {
			Login login = new Login();
			login.setLocationRelativeTo(null);
			login.setVisible(true);
			this.dispose();
		}
	}

}

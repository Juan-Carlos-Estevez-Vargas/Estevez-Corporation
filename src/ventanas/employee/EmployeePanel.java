package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmployeePanel extends JFrame implements ActionListener{

	/**
	 * Definición de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterClient;
	private JLabel labelManageClient;
	private JLabel labelPrintClients;
	private JPanel panelBack;
	private JButton btnRegisterClient;
	private JButton btnManageClient;
	private JButton btnPrintClients;
	private JComboBox<String> cmbRole;

	/**
	 * Constructor de clase.
	 */
	public EmployeePanel() {
		this.setSize(670, 310);
		this.setTitle("Panel Capturista");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
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
		this.setContentPane(panelBack);

		/*
		 * Label Usuario Logueado.
		 */
		this.labelTittle = new JLabel("Holaaaaa");
		this.labelTittle.setBounds(10, 10, 280, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(labelTittle);

		/*
		 * Botón para registrar un nuevo Usuario en el sistema.
		 */
		this.btnRegisterClient = new JButton();
		this.btnRegisterClient.setBounds(40, 80, 120, 100);
		this.btnRegisterClient.setIcon(new ImageIcon("src/img/add.png"));
		this.btnRegisterClient.addActionListener(this);
		this.panelBack.add(btnRegisterClient);

		/**
		 * Label Registrar Usuario.
		 */
		this.labelRegisterClient = new JLabel("Registrar Cliente");
		this.labelRegisterClient.setBounds(45, 190, 120, 15);
		this.labelRegisterClient.setForeground(new Color(192, 192, 192));
		this.labelRegisterClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(labelRegisterClient);

		/**
		 * Botón encargado de mostrar la lista de usuarios registrados en el sistema.
		 */
		this.btnManageClient = new JButton();
		this.btnManageClient.setBounds(270, 80, 120, 100);
		this.btnManageClient.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageClient.addActionListener(this);
		this.panelBack.add(btnManageClient);

		/**
		 * Label Gestionar Usuarios.
		 */
		this.labelManageClient = new JLabel("Gestionar Clientes");
		this.labelManageClient.setBounds(270, 190, 120, 15);
		this.labelManageClient.setForeground(new Color(192, 192, 192));
		this.labelManageClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(labelManageClient);
		
		/**
		 * Label Imprimir Clientes.
		 */
		this.labelPrintClients = new JLabel("Imprimir Clientes");
		this.labelPrintClients.setBounds(500, 190, 120, 15);
		this.labelPrintClients.setForeground(new Color(192, 192, 192));
		this.labelPrintClients.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(labelPrintClients);

		/**
		 * Botón para imprimir los clientes.
		 */
		this.btnPrintClients = new JButton();
		this.btnPrintClients.setBounds(500, 80, 120, 100);
		this.btnPrintClients.setIcon(new ImageIcon("src/img/impresora.png"));
		this.panelBack.add(btnPrintClients);

		/**
		 * ComboBox encargado de mostrar los roles a los que puede acceder el usuario
		 * Administrador junto con el apartado de cerrar sesión.
		 */
		this.cmbRole = new JComboBox<>();
		this.cmbRole.addItem("Capturista");
		this.cmbRole.addItem("Cerrar Sesión");
		this.cmbRole.setBounds(500, 20, 120, 20);
		this.panelBack.add(cmbRole);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

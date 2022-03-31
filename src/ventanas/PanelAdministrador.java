package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelAdministrador extends JFrame implements ActionListener {

	private JLabel labelTittle, labelRegisterUser, labelManageUser;
	private JPanel panelBack;
	private JButton btnRegisterUser, btnManageUser, btnPrueba;
	private JComboBox<String> cmbRole;

	public PanelAdministrador() {
		this.setSize(670, 310);
		this.setTitle("Panel Administrador");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();
	}

	public void initComponents() {

		this.panelBack = new JPanel();
		this.panelBack.setBackground(new Color(46, 59, 104));
		this.panelBack.setLayout(null);
		this.setContentPane(panelBack);

		this.labelTittle = new JLabel("Holaaaaa");
		this.labelTittle.setBounds(10, 10, 68, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(labelTittle);

		this.btnRegisterUser = new JButton();
		this.btnRegisterUser.setBounds(40, 80, 120, 100);
		this.btnRegisterUser.setIcon(new ImageIcon("src/img/addUser.png"));
		this.btnRegisterUser.addActionListener(this);
		this.panelBack.add(btnRegisterUser);

		this.labelRegisterUser = new JLabel("Registrar Usuario");
		this.labelRegisterUser.setBounds(45, 190, 120, 15);
		this.labelRegisterUser.setForeground(new Color(192, 192, 192));
		this.labelRegisterUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(labelRegisterUser);

		this.btnManageUser = new JButton();
		this.btnManageUser.setBounds(270, 80, 120, 100);
		this.btnManageUser.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageUser.addActionListener(this);
		this.panelBack.add(btnManageUser);

		this.labelManageUser = new JLabel("Gestionar Usuarios");
		this.labelManageUser.setBounds(270, 190, 120, 15);
		this.labelManageUser.setForeground(new Color(192, 192, 192));
		this.labelManageUser.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(labelManageUser);

		this.btnPrueba = new JButton();
		this.btnPrueba.setBounds(500, 80, 120, 100);
		this.btnPrueba.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.panelBack.add(btnPrueba);

		this.cmbRole = new JComboBox<>();
		this.cmbRole.addItem("Administrador");
		this.cmbRole.addItem("Técnico");
		this.cmbRole.addItem("Capturista");
		this.cmbRole.addItem("Cerrar Sesión");
		this.cmbRole.setBounds(500, 20, 120, 20);
		this.panelBack.add(cmbRole);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		RegisterUser registerUser;
		ManagementUsers managementUsers;
    	
		if (e.getSource() == this.btnRegisterUser) {
			registerUser = new RegisterUser();
	    	registerUser.setVisible(true);
		}
		
		if (e.getSource() == this.btnManageUser) {
			managementUsers = new ManagementUsers();
			managementUsers.setVisible(true);
		}
	}

}

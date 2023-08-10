package dev.juan.estevez.views.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.juan.estevez.utils.DatabaseConnection;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * Panel principal del tipo de usuario T�cnico.
 *
 * @author
 *
 */
public class PanelTechnical extends JFrame implements ActionListener {

	/**
	 * Definici�n de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelManagementEquips;
	private JLabel labelBrandChart;
	private JLabel labelStateGraph;
	private JPanel panelBack;
	private JButton btnManagementEquips;
	private JButton btnBrandChart;
	private JButton btnStateGraph;
	private JButton btnRestorePass;
	private JButton btnLogout;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public PanelTechnical() {
		this.user = LoginView.user;
		this.setSize(630, 280);
		this.setTitle("T�cnico - Sesi�n de " + this.user);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();

		/**
		 * Recuperando el nombre del usuario.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.connect();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT nombre_usuario, password FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText(nameUser);
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar T�cnico");
		}

	}

	/**
	 * Inicializa y construye los componentes Swing en el Frame principal.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.panelBack = new JPanel();
		this.panelBack.setBackground(new Color(46, 59, 104));
		this.panelBack.setLayout(null);
		this.setContentPane(this.panelBack);

		/*
		 * Label Principal.
		 */
		this.labelTittle = new JLabel();
		this.labelTittle.setBounds(10, 10, 280, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(this.labelTittle);

		/*
		 * Bot�n para listar los equipos presentes en el sistema.
		 */
		this.btnManagementEquips = new JButton();
		this.btnManagementEquips.setBounds(40, 80, 120, 100);
		this.btnManagementEquips.setIcon(new ImageIcon("src/img/apoyo-tecnico.png"));
		this.btnManagementEquips.addActionListener(this);
		this.btnManagementEquips.setBorder(null);
		this.btnManagementEquips.setBackground(new Color(46, 59, 104));
		this.btnManagementEquips.setOpaque(true);
		this.btnManagementEquips.setFocusable(false);
		this.panelBack.add(this.btnManagementEquips);

		/**
		 * Label Listar Equipos existentes en el sistema.
		 */
		this.labelManagementEquips = new JLabel("Gesti�n de Equipos");
		this.labelManagementEquips.setBounds(45, 190, 120, 15);
		this.labelManagementEquips.setForeground(new Color(192, 192, 192));
		this.labelManagementEquips.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManagementEquips);

		/**
		 * Bot�n encargado de mostrar la gr�fica de marcas de los equipos registrados.
		 */
		this.btnBrandChart = new JButton();
		this.btnBrandChart.setBounds(250, 80, 120, 100);
		this.btnBrandChart.setIcon(new ImageIcon("src/img/grafica.png"));
		this.btnBrandChart.addActionListener(this);
		this.btnBrandChart.setBorder(null);
		this.btnBrandChart.setBackground(new Color(46, 59, 104));
		this.btnBrandChart.setOpaque(true);
		this.btnBrandChart.setFocusable(false);
		this.panelBack.add(this.btnBrandChart);

		/**
		 * Label Gr�fica de Marcas.
		 */
		this.labelBrandChart = new JLabel("Gr�fica de Marcas");
		this.labelBrandChart.setBounds(250, 190, 120, 15);
		this.labelBrandChart.setForeground(new Color(192, 192, 192));
		this.labelBrandChart.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelBrandChart);

		/**
		 * Label Gr�fica de Estado.
		 */
		this.labelStateGraph = new JLabel("Gr�fica de Estado");
		this.labelStateGraph.setBounds(460, 190, 120, 15);
		this.labelStateGraph.setForeground(new Color(192, 192, 192));
		this.labelStateGraph.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelStateGraph);

		/**
		 * Bot�n para mostrar la gr�fica de estado de los equipos registrados en el
		 * sistema.
		 */
		this.btnStateGraph = new JButton();
		this.btnStateGraph.setBounds(460, 80, 120, 100);
		this.btnStateGraph.setIcon(new ImageIcon("src/img/grafica2.png"));
		this.btnStateGraph.addActionListener(this);
		this.btnStateGraph.setBorder(null);
		this.btnStateGraph.setBackground(new Color(46, 59, 104));
		this.btnStateGraph.setOpaque(true);
		this.btnStateGraph.setFocusable(false);
		this.panelBack.add(this.btnStateGraph);

		/**
		 * Bot�n Restaurar Contrase�a.
		 */
		this.btnRestorePass = new JButton();
		this.btnRestorePass.setBounds(430, 20, 40, 30);
		this.btnRestorePass.setIcon(new ImageIcon("src/img/padlock.png"));
		this.btnRestorePass.setBorder(null);
		this.btnRestorePass.setBackground(new Color(46, 59, 104));
		this.btnRestorePass.setOpaque(true);
		this.btnRestorePass.addActionListener(this);
		this.btnRestorePass.setFocusable(false);
		this.btnRestorePass.setFocusable(false);
		this.panelBack.add(this.btnRestorePass);

		/**
		 * Bot�n Cerrar Sesi�n.
		 */
		this.btnLogout = new JButton("Cerrar Sesi�n");
		this.btnLogout.setBounds(470, 20, 120, 30);
		this.btnLogout.setFont(new Font("serif", Font.BOLD, 14));
		this.btnLogout.setBackground(new Color(8, 85, 224));
		this.btnLogout.setForeground(Color.WHITE);
		this.btnLogout.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnLogout.addActionListener(this);
		this.btnLogout.setFocusable(false);
		this.panelBack.add(this.btnLogout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Muestra el panel para restaurar la contrase�a.
		 */
		if (e.getSource() == this.btnRestorePass) {
			RestorePassword restorePassword = new RestorePassword();
			restorePassword.setVisible(true);
			this.dispose();
		}

		/**
		 * Redirecci�n al panel con el listado de equipos registrados en el sistema.
		 */
		if (e.getSource() == this.btnManagementEquips) {
			ManagementEquips managementEquips = new ManagementEquips();
			managementEquips.setVisible(true);
		}

		/**
		 * Redirecci�n al panel con el reporte del estado de los equipos.
		 */
		if (e.getSource() == this.btnStateGraph) {
			StateGraph stateGraph = new StateGraph();
			stateGraph.setVisible(true);
		}

		/**
		 * Redirecci�n al panel con el reporte de marcas de los equipos registrados en
		 * el sistema.
		 */
		if (e.getSource() == this.btnBrandChart) {
			MarkGraph markGraph = new MarkGraph();
			markGraph.setVisible(true);
		}

		/**
		 * Cierra la sesi�n del usuario.
		 */
		if (e.getSource() == this.btnLogout) {
			/*if (JOptionPane.showConfirmDialog(null, "�Est� seguro de cerrar la sesi�n?") == 0) {
				Login login = new Login();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				this.dispose();
			}*/
		}

	}

}

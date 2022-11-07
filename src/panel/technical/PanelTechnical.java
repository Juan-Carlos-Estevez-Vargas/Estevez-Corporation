package panel.technical;

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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import panel.utilities.Login;
import panel.utilities.RestorePassword;
import util.DatabaseConnection;

/**
 * Panel principal del tipo de usuario Técnico.
 *
 * @author
 *
 */
public class PanelTechnical extends JFrame implements ActionListener {

	/**
	 * Definición de Variables.
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
		this.user = Login.user;
		this.setSize(630, 280);
		this.setTitle("Técnico - Sesión de " + this.user);
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
					.prepareStatement("SELECT nombre_usuario, password FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText(nameUser);
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar Técnico");
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
		 * Botón para listar los equipos presentes en el sistema.
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
		this.labelManagementEquips = new JLabel("Gestión de Equipos");
		this.labelManagementEquips.setBounds(45, 190, 120, 15);
		this.labelManagementEquips.setForeground(new Color(192, 192, 192));
		this.labelManagementEquips.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManagementEquips);

		/**
		 * Botón encargado de mostrar la gráfica de marcas de los equipos registrados.
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
		 * Label Gráfica de Marcas.
		 */
		this.labelBrandChart = new JLabel("Gráfica de Marcas");
		this.labelBrandChart.setBounds(250, 190, 120, 15);
		this.labelBrandChart.setForeground(new Color(192, 192, 192));
		this.labelBrandChart.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelBrandChart);

		/**
		 * Label Gráfica de Estado.
		 */
		this.labelStateGraph = new JLabel("Gráfica de Estado");
		this.labelStateGraph.setBounds(460, 190, 120, 15);
		this.labelStateGraph.setForeground(new Color(192, 192, 192));
		this.labelStateGraph.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelStateGraph);

		/**
		 * Botón para mostrar la gráfica de estado de los equipos registrados en el
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
		 * Botón Restaurar Contraseña.
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
		 * Botón Cerrar Sesión.
		 */
		this.btnLogout = new JButton("Cerrar Sesión");
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
		 * Muestra el panel para restaurar la contraseña.
		 */
		if (e.getSource() == this.btnRestorePass) {
			RestorePassword restorePassword = new RestorePassword();
			restorePassword.setVisible(true);
			this.dispose();
		}

		/**
		 * Redirección al panel con el listado de equipos registrados en el sistema.
		 */
		if (e.getSource() == this.btnManagementEquips) {
			ManagementEquips managementEquips = new ManagementEquips();
			managementEquips.setVisible(true);
		}

		/**
		 * Redirección al panel con el reporte del estado de los equipos.
		 */
		if (e.getSource() == this.btnStateGraph) {
			StateGraph stateGraph = new StateGraph();
			stateGraph.setVisible(true);
		}

		/**
		 * Redirección al panel con el reporte de marcas de los equipos registrados en
		 * el sistema.
		 */
		if (e.getSource() == this.btnBrandChart) {
			MarkGraph markGraph = new MarkGraph();
			markGraph.setVisible(true);
		}

		/**
		 * Cierra la sesión del usuario.
		 */
		if (e.getSource() == this.btnLogout) {
			Login login = new Login();
			login.setLocationRelativeTo(null);
			this.dispose();
			login.setVisible(true);
		}

	}

}

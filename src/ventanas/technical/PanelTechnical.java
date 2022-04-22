package ventanas.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;
import ventanas.Login;

public class PanelTechnical extends JFrame implements ActionListener{

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
	private JComboBox<String> cmbRole;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public PanelTechnical() {
		this.user = Login.user;
		this.setSize(670, 310);
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
					.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText(nameUser);
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar capturista");
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
		 * Botón encargado de mostrar la gráfica de estado de los equipos registrados.
		 */
		this.btnBrandChart = new JButton();
		this.btnBrandChart.setBounds(270, 80, 120, 100);
		this.btnBrandChart.setIcon(new ImageIcon("src/img/grafica.png"));
		this.btnBrandChart.addActionListener(this);
		this.panelBack.add(this.btnBrandChart);

		/**
		 * Label Gráfica de Marcas.
		 */
		this.labelBrandChart = new JLabel("Gráfica de Marcas");
		this.labelBrandChart.setBounds(270, 190, 120, 15);
		this.labelBrandChart.setForeground(new Color(192, 192, 192));
		this.labelBrandChart.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelBrandChart);

		/**
		 * Label Gráfica de Estado.
		 */
		this.labelStateGraph = new JLabel("Gráfica de Estado");
		this.labelStateGraph.setBounds(500, 190, 120, 15);
		this.labelStateGraph.setForeground(new Color(192, 192, 192));
		this.labelStateGraph.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelStateGraph);

		/**
		 * Botón para mostrar la gráfica de estado de los equipos registrados en el sistema.
		 */
		this.btnStateGraph = new JButton();
		this.btnStateGraph.setBounds(500, 80, 120, 100);
		this.btnStateGraph.setIcon(new ImageIcon("src/img/grafica.png"));
		this.btnStateGraph.addActionListener(this);
		this.panelBack.add(this.btnStateGraph);

		/**
		 * ComboBox encargado de mostrar los roles a los que puede acceder el cliente
		 * Capturista junto con el apartado de cerrar sesión.
		 */
		this.cmbRole = new JComboBox<>();
		this.cmbRole.addItem("Técnico");
		this.cmbRole.addItem("Cerrar Sesión");
		this.cmbRole.setBounds(500, 20, 120, 20);
		this.panelBack.add(this.cmbRole);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.btnManagementEquips) {
			ManagementEquips managementEquips = new ManagementEquips();
			managementEquips.setVisible(true);
		}
		
	}

	

}

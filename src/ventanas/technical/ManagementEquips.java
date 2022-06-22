package ventanas.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * Frame con la lista de equipos registrados en el sistema.
 * 
 * @author 
 *
 */
public class ManagementEquips extends JFrame {

	/**
	 * Declaración de Variables.
	 */
	private static final long serialVersionUID = 1L;
	public static int id_equip_update = 0;
	public static String user_update = "";
	private JLabel labelTitle;
	private JPanel container;
	private String user;
	private JTable tableEquips;
	private JScrollPane scrollPaneEquips;
	private DefaultTableModel model = new DefaultTableModel();

	/**
	 * Constructor de Clase.
	 */
	public ManagementEquips() {
		this.user = Login.user;
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Técnico - Sesión de " + this.user);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.initComponents();

		/**
		 * Rellenando la tabla con los datos de la base de datos.
		 */
		try {
			Connection cn = DatabaseConnection.conectar();
			PreparedStatement pst = cn.prepareStatement("SELECT id_equipo, tipo_equipo, marca, estatus FROM equipos");
			ResultSet rs = pst.executeQuery();

			this.tableEquips = new JTable(this.model);
			this.tableEquips.setFont(new Font("serif", Font.BOLD, 14));
			this.tableEquips.setForeground(Color.BLACK);
			this.scrollPaneEquips = new JScrollPane(this.tableEquips);
			this.scrollPaneEquips.setBounds(10, 55, 593, 230);
			this.scrollPaneEquips.setViewportView(this.tableEquips);

			/**
			 * Columnas de la tabla
			 */
			this.model.addColumn(" ");
			this.model.addColumn("Tipo");
			this.model.addColumn("Marca");
			this.model.addColumn("Estado");

			/**
			 * Llenado de la tabla
			 */
			while (rs.next()) {
				Object[] row = new Object[4];
				for (int i = 0; i < 4; i++) {
					row[i] = rs.getObject(i + 1);
				}
				this.model.addRow(row);
			}
			cn.close();
		} catch (SQLException e) {
			System.err.println("Error en el llenado de la tabla equipos " + e);
		}

		/**
		 * Evento de accion para seleccionar equipo.
		 */
		tableEquips.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowPoint = tableEquips.rowAtPoint(e.getPoint());
				int columnPoint = 0;

				if (rowPoint > -1) {
					id_equip_update = (int) model.getValueAt(rowPoint, columnPoint);
					EquipmentInformationTechnical equipmentInformationTechnical = new EquipmentInformationTechnical();
					equipmentInformationTechnical.setVisible(true);
					dispose();
				}
			}
		});
		this.container.add(this.scrollPaneEquips);

	}

	/**
	 * Construye los componentes Swing en el Frame.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.setContentPane(this.container);

		/**
		 * Título del Panel.
		 */
		this.labelTitle = new JLabel("Equipos Registrados");
		this.labelTitle.setBounds(210, 10, 250, 20);
		this.labelTitle.setForeground(new Color(192, 192, 192));
		this.labelTitle.setFont(new Font("serif", Font.BOLD, 20));
		this.container.add(this.labelTitle);
	}

}

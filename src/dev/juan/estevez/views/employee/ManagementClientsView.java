package dev.juan.estevez.views.employee;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.Bounds;
import dev.juan.estevez.utils.constants.CapConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas
 */
public class ManagementClientsView extends JFrame implements IGui {

	private static final long serialVersionUID = 1L;
	public static int id_cliente_update = 0;
	public static String user_update = "";
	private JPanel panel;
	private String user;
	private JTable tableClients;
	private DefaultTableModel model = new DefaultTableModel();
	private ClientService clientController;

	public ManagementClientsView() {
		this.user = LoginView.user;
		clientController = new ClientService(new ClientDAO());
		initializeFrame();
		initComponents();
		populateTable();
		addTableMouseListener();
	}

	@Override
	public void initializeFrame() {
		setSize(630, 340);
		setTitle(String.format(CapConstants.MANAGE_CLIENTS_SESION, this.user));
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void initComponents() {
		setupMainPanel();
		setupLabels();
	}

	@Override
	public void setupMainPanel() {
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		setContentPane(panel);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel(CapConstants.MANAGE_CLIENTS_SESION, Bounds.LABEL_MANAGE_TITLE, panel)
				.setFont(Fonts.BUTTON_FONT.getValue());
	}

	/**
	 * Populates the table with data.
	 *
	 * @throws SQLException if there is an error retrieving the clients
	 */
	private void populateTable() {
		try {
			List<Client> clients = clientController.getAll();
			createTableAndScrollPane();
			addColumnsToTable();
			fillTableWithData(clients);
		} catch (SQLException e) {
			StringUtils.handleQueryError(e, Constants.ERROR_SHOWING_INFORMATION);
		}
	}

	/**
	 * Creates a table and scroll pane.
	 */
	private void createTableAndScrollPane() {
		tableClients = GUIComponents.createTable(model, panel);
		GUIComponents.createScrollPanel(tableClients, Bounds.SCROLL_MANAGE, panel);
	}

	/**
	 * Adds columns to the table.
	 */
	private void addColumnsToTable() {
		model.addColumn(Clients.NAME.getValue());
		model.addColumn(Clients.EMAIL.getValue());
		model.addColumn(Clients.PHONE.getValue());
		model.addColumn(Clients.MODIFY_BY.getValue());
	}

	/**
	 * Fills the table with data from the given list of clients.
	 *
	 * @param clients the list of clients to be displayed in the table
	 * @throws SQLException if there is an error accessing the database
	 */
	private void fillTableWithData(List<Client> clients) throws SQLException {
		if (clients != null) {
			for (Client client : clients) {
				model.addRow(new Object[] {
						client.getName(),
						client.getEmail(),
						client.getPhone(),
						client.getLastModification()
				});
			}
		}
	}

	/**
	 * Adds a mouse event listener to the tableUsers object.
	 *
	 * @param e the MouseEvent object representing the mouse click event
	 */
	private void addTableMouseListener() {
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableClients.rowAtPoint(e.getPoint());
				int column = 1;

				if (row >= 0) {
					Client clientDB = clientController.getByEmail((String) model.getValueAt(row, column));
					id_cliente_update = clientDB.getId();
					ViewUtils.openPanel(new ClientInformationView(), ManagementClientsView.this);
				}
			}
		});
	}

	@Override
	public void setupTextFields() {
		throw new UnsupportedOperationException("Unimplemented method 'setupTextFields'");
	}

	@Override
	public void setupButtons() {
		throw new UnsupportedOperationException("Unimplemented method 'setupButtons'");
	}

	@Override
	public void setupEvents() {
		throw new UnsupportedOperationException("Unimplemented method 'setupEvents'");
	}

}

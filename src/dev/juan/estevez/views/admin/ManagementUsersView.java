package dev.juan.estevez.views.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.enums.Colors;
import dev.juan.estevez.utils.enums.Users;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class ManagementUsersView extends JFrame implements GUIInterface {

	private static final long serialVersionUID = 1L;
	public static String user_update = "";
	private JPanel container;
	private JTable tableUsers;
	private String user;
	private DefaultTableModel model = new DefaultTableModel();
	private UserController userController;

	public ManagementUsersView() {
		this.user = LoginView.user;
		userController = new UserController(new UserDAO());
		initializeFrame();
		initComponents();
		populateTable();
		addTableMouseEvent();
	}

	@Override
	public void initializeFrame() {
		setSize(630, 340);
		setTitle("Usuarios registrados - Sesión de " + this.user);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
	}

	@Override
	public void initComponents() {
		setupMainPanel();
		setupLabels();
	}

	@Override
	public void setupMainPanel() {
		container = new JPanel();
		container.setBackground(Colors.BACKGROUND_COLOR.getValue());
		container.setLayout(null);
		setContentPane(container);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel(Constants.REGISTERED_USERS_TITLE, Bounds.LABEL_MANAGE_TITLE, container);
	}

	/**
	 * Populates the table with data from the usuarios table in the database.
	 *
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private void populateTable() {
		try {
			List<User> users = userController.getAllUsers();
			createTableAndScrollPane();
			addColumnsToTable();
			fillTableWithData(users);
		} catch (SQLException e) {
			StringUtils.handleQueryError(e, Constants.ERROR_SHOWING_INFORMATION);
		}
	}

	/**
	 * Creates a table and scroll pane.
	 */
	private void createTableAndScrollPane() {
		tableUsers = GUIComponents.createTable(model, container);
		GUIComponents.createScrollPanel(tableUsers, Bounds.SCROLL_MANAGE, container);
	}

	/**
	 * Adds columns to the table.
	 */
	private void addColumnsToTable() {
		model.addColumn(" ");
		model.addColumn(Users.NAME.getValue());
		model.addColumn(Users.USERNAME.getValue());
		model.addColumn(Users.PERMISIONS.getValue());
		model.addColumn(Users.STATUS.getValue());
	}

	/**
	 * Fills the table with data using the given list of users.
	 *
	 * @param users the list of users to fill the table with data
	 * @throws SQLException if there is an error accessing the database
	 */
	private void fillTableWithData(List<User> users) throws SQLException {
		if (users != null) {
			for (User user : users) {
				model.addRow(new Object[] {
						user.getUserID(),
						user.getUserName(),
						user.getUsername(),
						user.getLevelType(),
						user.getStatus()
				});
			}
		}
	}

	/**
	 * Adds a mouse event listener to the tableUsers object.
	 *
	 * @param e the MouseEvent object representing the mouse click event
	 */
	private void addTableMouseEvent() {
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableUsers.rowAtPoint(e.getPoint());
				int column = 2;

				if (row >= 0) {
					user_update = (String) model.getValueAt(row, column);
					ViewUtils.openPanel(new UserInformationView(), ManagementUsersView.this);
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

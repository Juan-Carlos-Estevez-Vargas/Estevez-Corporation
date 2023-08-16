package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.Utils;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * Vista con el listado de los usuario registrados en el sistema.
 *
 * @author Juan Carlos Estevez Vargas
 */
public class ManagementUsersView extends JFrame {

	private static final long serialVersionUID = 1L;
	public static String user_update = "";
	private JPanel container;
	private JTable tableUsers;
	private JLabel title;
	private JScrollPane scrollPaneUsers;
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

	/**
	 * Initializes the frame.
	 */
	private void initializeFrame() {
        setSize(630, 340);
        setTitle("Usuarios registrados - Sesión de " + this.user);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

	/**
	 * Initializes the components of the Java function.
	 */
	private void initComponents() {
		container = new JPanel();
		container.setBackground(Constants.BACKGROUND_COLOR);
		container.setLayout(null);
		setContentPane(container);

		title = GUIComponents.createLabel(Constants.REGISTERED_USERS_TITLE, Bounds.MANAGE_USER_TITLE_BOUNDS, Constants.LABEL_FONT, Constants.ERROR_COLOR, container);
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
			Utils.handleQueryError(e, Constants.ERROR_SHOWING_INFORMATION);
		}
	}

	/**
	 * Creates a table and scroll pane.
	 */
	private void createTableAndScrollPane() {
		tableUsers = new JTable(model);
		tableUsers.setFont(Constants.PANEL_LABEL_FONT);
		tableUsers.setForeground(Color.BLACK);

		scrollPaneUsers = new JScrollPane(tableUsers);
		scrollPaneUsers.setBounds(10, 55, 593, 230);
		container.add(scrollPaneUsers);
	}

	/**
	 * Adds columns to the table.
	 */
	private void addColumnsToTable() {
		model.addColumn(" ");
		model.addColumn("Nombre Usuario");
		model.addColumn("Username");
		model.addColumn("Permisos");
		model.addColumn("Estatus");
	}

	/**
	 * Fills the table with data using the given list of users.
	 *
	 * @param  users  the list of users to fill the table with data
	 * @throws SQLException  if there is an error accessing the database
	 */
	private void fillTableWithData(List<User> users) throws SQLException {
		if (users != null) {
			for (User user : users) {
				model.addRow(new Object[] { user.getUserID(), user.getUserName(), user.getUsername(), user.getLevelType(),
						user.getStatus() });
			}
		}
	}

	/**
	 * Adds a mouse event listener to the tableUsers object.
	 *
	 * @param  e  the MouseEvent object representing the mouse click event
	 */
	private void addTableMouseEvent() {
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableUsers.rowAtPoint(e.getPoint());
				int column = 2;
	
				if (row >= 0) {
					String username = (String) model.getValueAt(row, column);
					openUserInformation(username);
				}
			}
		});
	}
	
	/**
	 * Opens the user information window.
	 *
	 * @param  username	the username of the user
	 */
	private void openUserInformation(String username) {
		user_update = username;
		UserInformation userInformation = new UserInformation();
		userInformation.setVisible(true);
		dispose();
	}
	
}

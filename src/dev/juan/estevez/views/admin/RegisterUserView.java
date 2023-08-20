package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.FieldValidator;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ValidateCharacters;
import dev.juan.estevez.utils.ValidateNumbers;
import dev.juan.estevez.utils.enums.Colors;
import dev.juan.estevez.utils.enums.Fonts;
import dev.juan.estevez.utils.enums.Icons;
import dev.juan.estevez.utils.enums.Roles;
import dev.juan.estevez.utils.enums.Users;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class RegisterUserView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private JTextField txtNameUser;
	private JTextField txtEmailUser;
	private JTextField txtPhoneUser;
	private JTextField txtUsername;
	private JPanel panelBackUser;
	private JButton btnRegisterUser;
	private JComboBox<String> cmbPermissions;
	private String user;
	private UserController userController;

	public RegisterUserView() {
		this.user = LoginView.user;
		userController = new UserController(new UserDAO());
		initializeFrame();
		initComponents();
	}

	@Override
	public void initializeFrame() {
        setSize(590, 340);
        setTitle("Registrar Usuario - Sesión de " + user);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

	@Override
	public void initComponents() {
		setupMainPanel();
		setupLabels();
        setupTextFields();
		setupButtons();
		setupPermissionsComboBox(Bounds.REGISTER_USER_PERMISIONS_CMB_BOUNDS);
		setupEvents();
    }

	@Override
	public void setupMainPanel() {
		panelBackUser = new JPanel();
		panelBackUser.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panelBackUser.setLayout(null);
		setContentPane(panelBackUser);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.REGISTER_USER_TITLE_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Users.NAME.getValue(), Bounds.REGISTER_USER_NAME_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Users.EMAIL.getValue(), Bounds.REGISTER_USER_EMAIL_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Users.PHONE.getValue(), Bounds.REGISTER_USER_PHONE_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Users.PERMISIONS_OF.getValue(), Bounds.REGISTER_USER_PERMISIONS_OF_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Users.USERNAME.getValue(), Bounds.REGISTER_USERNAME_BOUNDS, panelBackUser);
		GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.REGISTER_USER_BOUNDS, panelBackUser);
	}

	@Override
	public void setupTextFields() {
		txtNameUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_NAME_BOUNDS, panelBackUser);
		txtEmailUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_EMAIL_BOUNDS, panelBackUser);
		txtPhoneUser = GUIComponents.createTextField(Bounds.TEXT_FIELD_USER_PHONE_BOUNDS, panelBackUser);
		txtUsername = GUIComponents.createTextField(Bounds.TEXT_FIELD_USERNAME_BOUNDS, panelBackUser);
	}

	@Override
	public void setupButtons() {
		btnRegisterUser = GUIComponents.createButton(Icons.REGISTER_USER_BUTTON_ICON.getValue(), Bounds.REGISTER_USER_BUTTON_BOUNDS, Colors.BACKGROUND_COLOR.getValue(), panelBackUser);
	}

	@Override
	public void setupEvents() {
		txtNameUser.addKeyListener(new ValidateCharacters());
		txtPhoneUser.addKeyListener(new ValidateNumbers());
        btnRegisterUser.addActionListener(this);
	}

	/**
	 * Clears all the input fields and resets the permissions dropdown to the
	 * default value.
	 */
	public void clean() {
		txtEmailUser.setText("");
		txtNameUser.setText("");
		txtPhoneUser.setText("");
		txtUsername.setText("");
		cmbPermissions.setSelectedIndex(0);
	}

	/**
	 * Sets up the permissions combo box.
	 */
	private void setupPermissionsComboBox(int[] bounds) {
		cmbPermissions = new JComboBox<>();
		cmbPermissions.addItem(Roles.ROLE_ADMIN.getValue());
		cmbPermissions.addItem(Roles.ROLE_TECH.getValue());
		cmbPermissions.addItem(Roles.ROLE_CAPTURISTA.getValue());
		cmbPermissions.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		cmbPermissions.setBackground(Colors.TEXT_FIELD_COLOR.getValue());
		cmbPermissions.setFont(Fonts.PANEL_LABEL_FONT.getValue());
		cmbPermissions.setForeground(Color.WHITE);
		panelBackUser.add(cmbPermissions);
	}

	/**
	 * Performs an action when an event is triggered.
	 *
	 * @param e the event that triggered the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterUser) {
			validateAndRegisterUser();
		}
	}

	/**
	 * Validates and registers a user.
	 */
	private void validateAndRegisterUser() {
		User user = createUserFromInputs();
		int validation = validateUserFields(user);
	
		if (validation == 0) {
			handleValidUser(user);
		} else if (validation == 4) {
			StringUtils.showEmptyFieldsMessage();
		}
	}
	
	/**
	 * Creates a new User object based on the inputs provided.
	 *
	 * @return  a User object populated with the values from the input fields
	 */
	private User createUserFromInputs() {
		User user = new User();
		user.setUserEmail(txtEmailUser.getText().trim());
		user.setUserPhone(txtPhoneUser.getText().trim());
		user.setUsername(txtUsername.getText().trim());
		user.setUserName(txtNameUser.getText().trim());
		return user;
	}
	
	/**
	 * Validates the user fields and returns the total number of validation errors.
	 *
	 * @param  user  the user object containing the user's information
	 * @return       the total number of validation errors
	 */
	private int validateUserFields(User user) {
		int validation = FieldValidator.validateEmailField(user.getUserEmail(), txtEmailUser)
				+ FieldValidator.validateNameField(user.getUserName(), txtNameUser)
				+ FieldValidator.validatePhoneField(user.getUserPhone(), txtPhoneUser);
		return validation;
	}
	
	/**
	 * Handles a valid user.
	 *
	 * @param  user  the user object
	 */
	private void handleValidUser(User user) {
		int permissionsCmb = cmbPermissions.getSelectedIndex() + 1;
		String permissionsString = StringUtils.getPermissionsString(permissionsCmb);
		User existingUser = userController.getUserByUsername(user.getUsername());
	
		if (existingUser != null && !existingUser.getUsername().equals(user.getUsername())) {
			txtUsername.setBackground(Color.red);
			StringUtils.showMessage(Constants.USERNAME_NOT_AVAILABLE_MESSAGE);
		} else {
			user.setpermissions(permissionsString);
			user.setPassword(Constants.DEFAULT_PASSWORD);
			user.setRegisterBy(this.user);
			registerUser(user);
		}
	}

	/**
	 * Registers a user.
	 *
	 * @param  user  the user to be registered
	 */
	private void registerUser(User user) {
		if (userController.registerUser(user) == 1) {
			clean();
			txtEmailUser.setBackground(Color.green);
			txtNameUser.setBackground(Color.green);
			txtPhoneUser.setBackground(Color.green);
			txtUsername.setBackground(Color.green);
			StringUtils.showMessage(Constants.REGISTRATION_SUCCESS_MESSAGE);
			dispose();
		};	
	}
}

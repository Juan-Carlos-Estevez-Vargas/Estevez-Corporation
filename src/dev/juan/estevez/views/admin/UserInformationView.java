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
import dev.juan.estevez.utils.enums.Roles;
import dev.juan.estevez.utils.enums.States;
import dev.juan.estevez.utils.enums.Users;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class UserInformationView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private int ID, idSuperAdministrador;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtUsername;
	private JTextField txtRegisterBy;
	private JComboBox<String> cmbLevels;
	private JComboBox<String> cmbStatus;
	private JPanel container;
	private JButton btnUpdate;
	private UserController userController;

	public UserInformationView() {
		this.user = LoginView.user;
		user_update = ManagementUsersView.user_update;
		userController = new UserController(new UserDAO());
		initializeFrame();
		initComponents();
		loadUserData();
		loadSuperAdministratorData();
	}

	@Override
	public void initializeFrame() {
		setSize(630, 360);
		setTitle("Información del usuario " + user_update + " - Sesión de " + user);
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
		createComboBoxes();
		setupEvents();
	}

	@Override
	public void setupMainPanel() {
		container = new JPanel();
		container.setBackground(Colors.BACKGROUND_COLOR.getValue());
		container.setLayout(null);
		container.setBounds(630, 460, 630, 460);
		setContentPane(container);
	}

	@Override
	public void setupLabels() {
		GUIComponents.createLabel("Información del usuario " + user_update, Bounds.USER_INFORMATION_TITLE_BOUNDS, container);
		GUIComponents.createLabel(Users.NAME.getValue(), Bounds.USER_INFORMATION_USER_NAME_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.EMAIL.getValue(), Bounds.USER_INFORMATION_USER_EMAIL_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.PHONE.getValue(), Bounds.USER_INFORMATION_USER_PHONE_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.PERMISIONS_OF.getValue(), Bounds.USER_INFORMATION_CMB_LEVEL_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.USERNAME.getValue(), Bounds.USER_INFORMATION_USER_USERNAME_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.STATUS.getValue(), Bounds.USER_INFORMATION_CMB_STATUS_LABEL_BOUNDS, container);
		GUIComponents.createLabel(Users.REGISTERED_BY.getValue(), Bounds.USER_INFORMATION_USER_REGISTER_BY_LABEL_BOUNDS, container);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(Bounds.USER_INFORMATION_USER_NAME_BOUNDS, container);
		txtEmail = GUIComponents.createTextField(Bounds.USER_INFORMATION_USER_EMAIL_BOUNDS, container);
		txtPhone = GUIComponents.createTextField(Bounds.USER_INFORMATION_USER_PHONE_BOUNDS, container);
		txtUsername = GUIComponents.createTextField(Bounds.USER_INFORMATION_USER_USERNAME_BOUNDS, container);
		txtRegisterBy = GUIComponents.createTextField(Bounds.USER_INFORMATION_USER_REGISTER_BY_BOUNDS, container);
		txtRegisterBy.setEnabled(false);
	}

	@Override
	public void setupButtons() {
		btnUpdate = GUIComponents.createButton(Constants.UPDATED_USER, Bounds.UPDATE_USER_BUTTON_BOUNDS, Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), container);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		txtPhone.addKeyListener(new ValidateNumbers());
		btnUpdate.addActionListener(this);
	}

	/**
	 * This function loads the user data from the user controller based on the provided username.
	 *
	 * @param  user_update  the username of the user
	 */
	private void loadUserData() {
		User user = userController.getUserByUsername(user_update);

		if (user != null) {
			ID = user.getUserID();
			txtName.setText(user.getUserName());
			txtEmail.setText(user.getUserEmail());
			txtPhone.setText(user.getUserPhone());
			txtUsername.setText(user.getUsername());
			txtRegisterBy.setText(user.getRegisterBy());
			cmbLevels.setSelectedItem(user.getLevelType());
			cmbStatus.setSelectedItem(user.getStatus());
		}
	}

	/**
	 * Loads the data of the super administrator.
	 */
	private void loadSuperAdministratorData() {
		User user = userController.getUserById(1);

		if (user != null) {
			idSuperAdministrador = user.getUserID();
		} 
	}

	/**
	 * Creates the combo boxes.
	 *
	 * @param paramName description of parameter
	 * @return description of return value
	 */
	private void createComboBoxes() {
		cmbStatus = GUIComponents.createComboBox(Bounds.USER_INFORMATION_CMB_STATUS_BOUNDS, States.getAllValues(), container);
		cmbLevels = GUIComponents.createComboBox(Bounds.USER_INFORMATION_CMB_LEVEL_BOUNDS, Roles.getAllValues(), container);
	}

	/**
	 * A description of the actionPerformed method.
	 *
	 * @param e The ActionEvent object that triggered the event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			updateUser();
		}
	}

	/**
	 * Updates the user based on the inputs provided.
	 */
	private void updateUser() {
		User user = createUserFromInputs();
		int cmbPermissions = cmbLevels.getSelectedIndex() + 1;
		int cmbStatus = this.cmbStatus.getSelectedIndex() + 1;
	
		int validation = validateUserFields(user);
		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}
	
		if (idSuperAdministrador == ID) {
			StringUtils.showMessage(Constants.CANNOT_UPDATE_SUPER_ADMIN);
			return;
		}
	
		if (validation == 0) {
			user.setpermissions(StringUtils.getPermissionsString(cmbPermissions));
			user.setStatus(StringUtils.getStatusString(cmbStatus));
	
			if (canUpdateUser(user)) {
				if (userController.updateUser(user) == 1) {
					StringUtils.showMessage(Constants.SUCCESSFUL_MODIFICATION);
					dispose();
					openManagementUsersView();
				}
			} else {
				txtUsername.setBackground(Color.red);
				StringUtils.showMessage(Constants.USERNAME_NOT_AVAILABLE_MESSAGE);
			}
		}
	}
	
	/**
	 * Checks if the user can be updated.
	 *
	 * @param  user  the user to be updated
	 * @return       true if the user can be updated, false otherwise
	 */
	private boolean canUpdateUser(User user) {
		User userNotAdmin = userController.getUserByUsername(user.getUsername());
		return userNotAdmin == null || userNotAdmin.getUserID() == ID;
	}
	
	/**
	 * Opens the management users view.
	 */
	private void openManagementUsersView() {
		ManagementUsersView managementUsers = new ManagementUsersView();
		managementUsers.setVisible(true);
	}	

	/**
	 * Creates a new User object based on the inputs provided.
	 *
	 * @return  a User object populated with the values from the input fields
	 */
	private User createUserFromInputs() {
		User user = new User();
		user.setUserID(ID);
		user.setUserEmail(txtEmail.getText().trim());
		user.setUserName(txtName.getText().trim());
		user.setUsername(txtUsername.getText().trim());
		user.setUserPhone(txtPhone.getText().trim());
		return user;
	}

	/**
	 * Validates the user fields and returns the total number of validation errors.
	 *
	 * @param  user  the user object containing the user's information
	 * @return       the total number of validation errors
	 */
	private int validateUserFields(User user) {
		int validation = FieldValidator.validateEmailField(user.getUserEmail(), txtEmail)
				+ FieldValidator.validateNameField(user.getUserName(), txtName)
				+ FieldValidator.validatePhoneField(user.getUserPhone(), txtPhone)
				+ FieldValidator.validateUsernameField(user.getUsername(), txtUsername);

		return validation;
	}

}

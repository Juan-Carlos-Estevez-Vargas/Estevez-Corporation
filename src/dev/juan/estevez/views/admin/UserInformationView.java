package dev.juan.estevez.views.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.States;
import dev.juan.estevez.enums.Users;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Role;
import dev.juan.estevez.models.User;
import dev.juan.estevez.models.UserRole;
import dev.juan.estevez.persistence.RoleDAO;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.services.impl.RoleService;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.admin.UserInformationBounds;
import dev.juan.estevez.utils.constants.AdminConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.FieldValidator;
import dev.juan.estevez.utils.validators.ValidateCharacters;
import dev.juan.estevez.utils.validators.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class UserInformationView extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private int ID, idSuperAdministrador;
	private JTextField txtName, txtEmail, txtPhone, txtUsername, txtRegisterBy;
	private JComboBox<String> cmbRoles, cmbStatus;
	private JPanel panel;
	private JButton btnUpdate;
	private UserService userController;
	private RoleService roleService;
	private List<Role> roles;

	public UserInformationView() {
		this.user = LoginView.user;
		user_update = ManagementUsersView.user_update;
		userController = new UserService(new UserDAO());
		roleService = new RoleService(new RoleDAO());
		roles = roleService.getAll();
		initializeFrame();
		initComponents();
		loadUserData();
		loadSuperAdministratorData();
	}

	@Override
	public void initializeFrame() {
		setSize(630, 360);
		setTitle(String.format(AdminConstants.USER_INFORMATION_SESION, user_update, user));
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
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		panel.setBounds(630, 460, 630, 460);
		setContentPane(panel);
	}

	@Override
	public void setupLabels() {
		JLabel titleLabel = GUIComponents.createLabel(String.format(AdminConstants.USER_INFORMATION_TITLE, user_update),
				UserInformationBounds.LABEL_TITLE, panel);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(Fonts.BUTTON_FONT.getValue());

		GUIComponents.createLabel(Users.NAME.getValue(), UserInformationBounds.LABEL_NAME, panel);
		GUIComponents.createLabel(Users.EMAIL.getValue(), UserInformationBounds.LABEL_EMAIL, panel);
		GUIComponents.createLabel(Users.PHONE.getValue(), UserInformationBounds.LABEL_PHONE, panel);
		GUIComponents.createLabel(Users.PERMISIONS_OF.getValue(), UserInformationBounds.LABEL_LEVEL, panel);
		GUIComponents.createLabel(Users.USERNAME.getValue(), UserInformationBounds.LABEL_USERNAME, panel);
		GUIComponents.createLabel(Users.STATUS.getValue(), UserInformationBounds.LABEL_STATUS, panel);
		GUIComponents.createLabel(Users.REGISTERED_BY.getValue(), UserInformationBounds.LABEL_REGISTER_BY, panel);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(UserInformationBounds.TXT_NAME, panel);
		txtEmail = GUIComponents.createTextField(UserInformationBounds.TXT_EMAIL, panel);
		txtPhone = GUIComponents.createTextField(UserInformationBounds.TXT_PHONE, panel);
		txtUsername = GUIComponents.createTextField(UserInformationBounds.TXT_USERNAME, panel);
		txtRegisterBy = GUIComponents.createTextField(UserInformationBounds.TXT_REGISTER_BY, panel);
		txtRegisterBy.setEnabled(false);
	}

	@Override
	public void setupButtons() {
		btnUpdate = GUIComponents.createButton(AdminConstants.UPDATE_USER, UserInformationBounds.BUTTON_UPDATE,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), panel);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		txtPhone.addKeyListener(new ValidateNumbers());
		btnUpdate.addActionListener(this);
	}

	/**
	 * This function loads the user data from the user controller based on the
	 * provided username.
	 *
	 * @param user_update the username of the user
	 */
	private void loadUserData() {
		User user = userController.getByUsername(user_update);

		if (user != null) {
			UserRole userRole = userController.getRoleListByUser(user).get(0);
			ID = user.getId();
			txtName.setText(user.getName());
			txtEmail.setText(user.getEmail());
			txtPhone.setText(user.getPhone());
			txtUsername.setText(user.getUsername());
			txtRegisterBy.setText(user.getRegisterBy());
			cmbRoles.setSelectedItem(userRole.getRole().getRoleName());
			cmbStatus.setSelectedItem(user.getStatus());
		}
	}

	/**
	 * Loads the data of the super administrator.
	 */
	private void loadSuperAdministratorData() {
		// TODO: crear nuevo perfil pal super admin con funcionalidades explicitas de el
		// como crear nuevas cosas como categoias estados roles, etc
		User user = userController.getById(1);

		if (user != null) {
			idSuperAdministrador = user.getId();
		}
	}

	private void createComboBoxes() {
		List<String> statusOptions = createStatusOptions();
		List<String> roleNames = getRoleNames();

		cmbStatus = GUIComponents.createComboBox(UserInformationBounds.CMB_STATUS, statusOptions, panel);
		cmbRoles = GUIComponents.createComboBox(UserInformationBounds.CMB_LEVEL, roleNames, panel);
	}

	private List<String> createStatusOptions() {
		List<String> options = new ArrayList<>();
		options.add(String.valueOf(States.ACTIVE));
		options.add(String.valueOf(States.INACTIVE));
		return options;
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
		Role role = roles.get(cmbRoles.getSelectedIndex());
		int cmbStatusIndex = cmbStatus.getSelectedIndex() + 1;
		int validation = validateUserFields(user);

		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}

		if (idSuperAdministrador == ID) {
			StringUtils.showMessage(AdminConstants.CANNOT_UPDATE_SUPER_ADMIN);
			return;
		}

		if (validation == 0) {
			user.setStatus(StringUtils.getStatusString(cmbStatusIndex));

			if (canUpdateUser(user, role)) {
				if (userController.updateUser(user, role) == 1) {
					StringUtils.showMessage(Constants.SUCCESSFUL_MODIFICATION);
					ViewUtils.openPanel(new ManagementUsersView(), this);
				}
			} else {
				txtUsername.setBackground(Color.red);
				StringUtils.showMessage(AdminConstants.USERNAME_NOT_AVAILABLE);
			}
		}
	}

	/**
	 * Checks if the user can be updated.
	 *
	 * @param user the user to be updated
	 * @return true if the user can be updated, false otherwise
	 */
	private boolean canUpdateUser(User user, Role role) {
		User userNotAdmin = userController.getByUsername(user.getUsername());
		return userNotAdmin == null || userNotAdmin.getId() == ID;
	}

	/**
	 * Creates a new User object based on the inputs provided.
	 *
	 * @return a User object populated with the values from the input fields
	 */
	private User createUserFromInputs() {
		User user = new User();
		user.setId(ID);
		user.setEmail(txtEmail.getText().trim());
		user.setName(txtName.getText().trim());
		user.setUsername(txtUsername.getText().trim());
		user.setPhone(txtPhone.getText().trim());
		return user;
	}

	/**
	 * Validates the user fields and returns the total number of validation errors.
	 *
	 * @param user the user object containing the user's information
	 * @return the total number of validation errors
	 */
	private int validateUserFields(User user) {
		int validation = FieldValidator.validateEmailField(user.getEmail(), txtEmail)
				+ FieldValidator.validateNameField(user.getName(), txtName)
				+ FieldValidator.validatePhoneField(user.getPhone(), txtPhone)
				+ FieldValidator.validateUsernameField(user.getUsername(), txtUsername);

		return validation;
	}

	private List<String> getRoleNames() {
		List<String> roleNames = new ArrayList<>();

		for (Role role : roles) {
			roleNames.add(role.getRoleName());
		}
		return roleNames;
	}

}

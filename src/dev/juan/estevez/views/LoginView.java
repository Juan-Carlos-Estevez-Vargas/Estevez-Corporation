package dev.juan.estevez.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.auth.LoginBounds;
import dev.juan.estevez.utils.constants.AuthConstants;
import dev.juan.estevez.utils.constants.DbConstants;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.enums.Roles;
import dev.juan.estevez.enums.States;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.User;
import dev.juan.estevez.services.impl.UserService;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.FieldValidator;
import dev.juan.estevez.views.admin.AdministratorPanelView;
import dev.juan.estevez.views.employee.EmployeePanelView;
import dev.juan.estevez.views.technical.PanelTechnical;

import panel.utilities.ForgotPassword;

/**
 * @author Juan Carlos Estevez Vargas
 */
public final class LoginView extends JFrame implements ActionListener, IGui {

    public static String user = "";
    private boolean eyeEstate;
    private final UserService loginController;
    private JButton btnLogin, btnEye, btnForgot;
    private JLabel jlError;
    private JPanel panel;
    private JPasswordField txtPassword;
    private JTextField txtUser, txtPassword2;

    public LoginView(UserService loginController) {
        this.loginController = loginController;
        initializeFrame();
        initComponents();
    }

    @Override
    public void initializeFrame() {
        setSize(350, 600);
        setTitle(Constants.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void initComponents() {
        setupMainPanel();
        setupLabels();
        setupTextFields();
        setupButtons();
        GUIComponents.createSeparator(LoginBounds.SEPARATOR_LOGIN, panel);
        setupEvents();
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
        GUIComponents.createImageLabel(AuthConstants.LOGO_URL, LoginBounds.LABEL_LOGIN_LOGO, panel);
        GUIComponents.createLabel(AuthConstants.LABEL_USER, LoginBounds.LABEL_LOGIN_USER, panel);
        GUIComponents.createLabel(AuthConstants.LABEL_PASSWORD, LoginBounds.LABEL_LOGIN_PASSWORD, panel);

        jlError = GUIComponents.createLabel("", LoginBounds.LABEL_LOGIN_ERROR, panel);
        jlError.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void setupTextFields() {
        txtUser = GUIComponents.createTextField(LoginBounds.TXT_LOGIN_USER, panel);
        txtPassword = GUIComponents.createPasswordField(LoginBounds.TXT_LOGIN_PASSWORD, panel);
        txtPassword2 = GUIComponents.createTextField(LoginBounds.TXT_LOGIN_PASSWORD2, panel);
        txtPassword2.setVisible(false);
    }

    @Override
    public void setupButtons() {
        btnEye = GUIComponents.createButton(Icons.EYE.getValue(), LoginBounds.BUTTON_LOGIN_EYE, Colors.BACKGROUND_COLOR.getValue(), panel);
        btnLogin = GUIComponents.createButton(AuthConstants.SIGN_IN, LoginBounds.BUTTON_LOGIN, Colors.BUTTON_COLOR.getValue(), Fonts.BUTTON_FONT.getValue(), panel);
        btnForgot = createForgotButton();
    }

    @Override
    public void setupEvents() {
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLoginAction();
                }
            }
        });

        btnEye.addActionListener(this);
        btnLogin.addActionListener(this);
        btnForgot.addActionListener(this);
    }

    private JButton createForgotButton() {
        JButton btn = GUIComponents.createButton(AuthConstants.FORGOT_TEXT, LoginBounds.BUTTON_LOGIN_FORGOT, Colors.BACKGROUND_COLOR.getValue(), Fonts.BUTTON_FONT.getValue(), panel);
        btn.setForeground(Colors.ERROR_COLOR.getValue());
        btn.setBorder(null);
        btn.setOpaque(true);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnForgot) {
            ViewUtils.openPanel(new ForgotPassword(), this);
        } else if (e.getSource() == btnEye) {
            togglePasswordVisibility();
        } else if (e.getSource() == btnLogin) {
            performLoginAction();
        }
    }

    private void performLoginAction() {
        try {
            performLogin();
        } catch (SQLException ex) {
            handleLoginError(DbConstants.ERROR_DB_CONNECTION);
        }
    }

    private void performLogin() throws SQLException {
        String password = new String(txtPassword.getPassword());
        String username = txtUser.getText().trim();
        int validation = validateFields(username, password);

        if (!username.isEmpty() && !password.isEmpty() && validation == 0) {
            User user = loginController.getByUsernameAndPassword(username, password);

            if (user != null) {
                LoginView.user = user.getName();
                handleUserLogin(user);
            } else {
                handleLoginError(AuthConstants.AUTH_ERROR);
            }
        } else {
            StringUtils.showEmptyFieldsMessage();
        }
    }

    private int validateFields(String username, String password) {
        int validation = FieldValidator.validateUsernameField(username, txtUser)
                + FieldValidator.validateUsernameField(password, txtPassword2)
                + FieldValidator.validatePasswordField(password, txtPassword);
        return validation;
    }

    private void handleUserLogin(User user) throws SQLException {
        // TODO: el level type validarlo su el usuario tiene mas roles preguntarle como
        // desea ingresa
        // si admin o que
        String levelType = loginController.getRoleListByUser(user).get(0).getRole().getRoleName();
        String status = user.getStatus();

        if (status.equalsIgnoreCase(States.ACTIVE.getValue())) {
            openAppropriatePanel(levelType);
        } else {
            jlError.setText(AuthConstants.INACTIVE_USER);
        }
    }

    private void openAppropriatePanel(String levelType) throws SQLException {
        if (levelType.equals(Roles.ROLE_ADMIN.getValue())) {
            ViewUtils.openPanel(new AdministratorPanelView(), this);
        } else if (levelType.equals(Roles.ROLE_CAPTURISTA.getValue())) {
            ViewUtils.openPanel(new EmployeePanelView(), this);
        } else if (levelType.equals(Roles.ROLE_TECH.getValue())) {
            ViewUtils.openPanel(new PanelTechnical(), this);
        } else {
            jlError.setText(AuthConstants.INACTIVE_USER);
        }
    }

    private void togglePasswordVisibility() {
        if (!eyeEstate) {
            String passwordEye = new String(txtPassword.getPassword());
            txtPassword2.setText(passwordEye);
            txtPassword2.setVisible(true);
            txtPassword.setVisible(false);
            eyeEstate = true;
        } else {
            txtPassword.setText(txtPassword2.getText().trim());
            txtPassword2.setVisible(false);
            txtPassword.setVisible(true);
            eyeEstate = false;
        }
    }

    private void handleLoginError(String errorMessage) {
        jlError.setText(AuthConstants.CREDENTIALS_ERROR);
        jlError.setFont(Fonts.ERROR_FONT.getValue());
        jlError.setForeground(Colors.ERROR_COLOR.getValue());
        jlError.setBounds(45, 390, 250, 25);

        txtUser.setText("");
        txtUser.requestFocus();
        txtPassword.setText("");
        txtPassword2.setText("");
    }

}

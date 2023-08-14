package dev.juan.estevez.views.administrator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.reports.GeneratePDFReport;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

public class AdministratorPanel extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel labelTitle;
    private JLabel labelRegisterUser;
    private JLabel labelManageUser;
    private JLabel labelPrintUsers;
    private JPanel panelBack;
    private JButton btnRegisterUser;
    private JButton btnManageUser;
    private JButton btnPrintUsers;
    private JButton btnRestorePass;
    private JButton btnLogout;
    private String user;

    public AdministratorPanel() throws SQLException {
        this.user = LoginView.user;
        initializeFrame();
        initComponents();
    }

    /**
     * Initializes the frame with specific size, title, and layout.
     */
    private void initializeFrame() {
        setSize(630, 280);
        setTitle("Administrador - Sesión de " + user);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the components of the Java function.
     */
    private void initComponents() {
        panelBack = new JPanel();
        panelBack.setBackground(new Color(46, 59, 104));
        panelBack.setLayout(null);
        setContentPane(panelBack);

        labelTitle = GUIComponents.createLabel(this.user, Bounds.ADMINISTRATOR_LABEL_TITLE_BOUNDS, Constants.LABEL_FONT, Constants.ERROR_COLOR, panelBack);
		labelRegisterUser = GUIComponents.createLabel(Constants.USER_REGISTER_TEXT, Bounds.ADMINISTRATOR_REGISTER_USER_LABEL_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBack);
        labelManageUser = GUIComponents.createLabel(Constants.MANAGE_USERS_TEXT, Bounds.ADMINISTRATOR_MANAGE_USERS_LABEL_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBack);
        labelPrintUsers = GUIComponents.createLabel(Constants.PRINT_USERS_TEXT, Bounds.ADMINISTRATOR_PRINT_USERS_LABEL_BOUNDS, Constants.PANEL_LABEL_FONT, Constants.ERROR_COLOR, panelBack);

        btnRegisterUser = GUIComponents.createButton(Constants.ADD_USER_ICON, Bounds.REGISTER_BUTTON_BOUNDS, null, panelBack);
        btnManageUser = GUIComponents.createButton(Constants.INFORMATION_USER_ICON, Bounds.MANAGE_USERS_BUTTON_BOUNDS, null, panelBack);
        btnPrintUsers = GUIComponents.createButton(Constants.PRINT_USERS_ICON, Bounds.PRINT_USERS_BUTTON_BOUNDS, null, panelBack);
        btnRestorePass = GUIComponents.createButton(Constants.RESTORE_PASS_ICON, Bounds.RESTORE_PASS_BUTTON_BOUNDS, null, panelBack);
        btnLogout = GUIComponents.createLogoutButton(panelBack);

        btnRegisterUser.addActionListener(this);
        btnManageUser.addActionListener(this);
        btnPrintUsers.addActionListener(this);        
        btnRestorePass.addActionListener(this);
        btnLogout.addActionListener(this);
    }

    /**
     * Overrides the actionPerformed method of the ActionListener interface.
     * Checks the source of the action event and calls the appropriate handler method.
     *
     * @param  e  the ActionEvent object representing the user action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRestorePass) {
            handleRestorePassword();
        } else if (e.getSource() == btnRegisterUser) {
            handleRegisterUser();
        } else if (e.getSource() == btnManageUser) {
            handleManageUser();
        } else if (e.getSource() == btnLogout) {
            handleLogout();
        } else if (e.getSource() == btnPrintUsers) {
            handlePrintUsers();
        }
    }

    /**
     * Handles the restore password functionality.
     */
    private void handleRestorePassword() {
        RestorePassword restorePassword = new RestorePassword();
        restorePassword.setVisible(true);
        dispose();
    }

    /**
     * Handles the registration of a new user.
     */
    private void handleRegisterUser() {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setVisible(true);
    }

    /**
     * This function handles the management of users.
     */
    private void handleManageUser() {
        ManagementUsers managementUsers = new ManagementUsers();
        managementUsers.setVisible(true);
    }

    /**
     * Handles the logout functionality.
     */
    private void handleLogout() {
        int confirmationResult = JOptionPane.showConfirmDialog(null, Constants.ARE_YOU_SURE_TO_LOGOUT);
        if (confirmationResult == JOptionPane.OK_OPTION) {
            LoginView loginView = new LoginView(new UserController(new UserDAO()));
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            dispose();
        }
    }

    /**
     * Handles the action of printing users.
     */
    private void handlePrintUsers() {
        JFileChooser fc = new JFileChooser();
        int response = fc.showSaveDialog(this);

        if (response != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File chosenFile = fc.getSelectedFile();
        String outputPath = chosenFile.getAbsolutePath() + ".pdf";

        try {
            GeneratePDFReport.createPDF(outputPath);
            JOptionPane.showMessageDialog(null, Constants.USER_LIST_CREATED_SUCCESSFULLY);
        } catch (IOException | DocumentException ex) {
            JOptionPane.showMessageDialog(null, Constants.GENERATE_ERROR_PDF);
        }
    }
}

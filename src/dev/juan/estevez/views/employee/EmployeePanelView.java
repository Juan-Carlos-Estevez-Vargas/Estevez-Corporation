package dev.juan.estevez.views.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.itextpdf.text.DocumentException;

import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.enums.Icons;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.reports.CustomPDFReport;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.Bounds;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * @author Juan Carlos Estevez Vargas
 */
public class EmployeePanelView extends JFrame implements ActionListener, IGui {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton btnRegisterClient;
    private JButton btnManageClient;
    private JButton btnPrintClients;
    private JButton btnRestorePass;
    private JButton btnLogout;
    private String user;

    public EmployeePanelView() {
        user = LoginView.user;
        initializeFrame();
        initComponents();
    }

    @Override
    public void initializeFrame() {
        setSize(630, 280);
        setTitle("Capturista - Sesión de " + user);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    @Override
    public void initComponents() {
        setupMainPanel();
        setupLabels();
        setupButtons();
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
        GUIComponents.createLabel(this.user, Bounds.LABEL_ADMIN_TITLE, panel);
        GUIComponents.createLabel(Constants.CLIENT_REGISTER_TEXT, Bounds.LABEL_ADMIN_USER_REGISTER, panel)
                .setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.MANAGE_CLIENTS_TEXT, Bounds.LABEL_ADMIN_MANAGE_USERS, panel)
                .setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.PRINT_CLIENTS_TEXT, Bounds.LABEL_ADMIN_PRINT_USERS, panel)
                .setFont(Fonts.PANEL_LABEL_FONT.getValue());
    }

    @Override
    public void setupButtons() {
        btnRegisterClient = GUIComponents.createButton(Icons.ADD_CLIENT_ICON.getValue(), Bounds.BUTTON_ADMIN_REGISTER,
                null, panel);
        btnManageClient = GUIComponents.createButton(Icons.INFORMATION_USER_ICON.getValue(),
                Bounds.BUTTON_ADMIN_MANAGE_USERS, null, panel);
        btnPrintClients = GUIComponents.createButton(Icons.PRINT_USERS_ICON.getValue(), Bounds.BUTTON_ADMIN_PRINT_USERS,
                null, panel);
        btnRestorePass = GUIComponents.createButton(Icons.RESTORE_PASS_ICON.getValue(), Bounds.BUTTON_RESTORE_PASSWORD,
                null, panel);
        btnLogout = GUIComponents.createLogoutButton(panel);
    }

    @Override
    public void setupEvents() {
        btnRegisterClient.addActionListener(this);
        btnManageClient.addActionListener(this);
        btnPrintClients.addActionListener(this);
        btnRestorePass.addActionListener(this);
        btnLogout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRestorePass) {
            ViewUtils.openPanel(new RestorePassword(), this);
        } else if (e.getSource() == btnRegisterClient) {
            ViewUtils.openPanel(new RegisterClientView());
        } else if (e.getSource() == btnLogout) {
            ViewUtils.handleLogout(this);
        } else if (e.getSource() == btnManageClient) {
            ViewUtils.openPanel(new ManagementClientsView());
        } else if (e.getSource() == btnPrintClients) {
            handlePrintClients();
        }
    }

    /**
     * Handles the print clients operation.
     */
    private void handlePrintClients() {
        JFileChooser fc = new JFileChooser();
        int response = fc.showSaveDialog(this);

        if (response != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File chosenFile = fc.getSelectedFile();
        String outputPath = chosenFile.getAbsolutePath() + Constants.PDF_EXTENSION;

        try {
            List<Client> clients = fetchAllClients();
            CustomPDFReport.generateClientsPDFReport(clients, outputPath);
            JOptionPane.showMessageDialog(null, Constants.CLIENT_LIST_CREATED_SUCCESSFULLY);
        } catch (IOException | DocumentException | SQLException ex) {
            JOptionPane.showMessageDialog(null, Constants.GENERATE_ERROR_PDF);
        }
    }

    /**
     * Fetches all clients from the database.
     *
     * @return a list of Client objects representing all clients in the database
     * @throws SQLException if there is an error while fetching the clients
     */
    private List<Client> fetchAllClients() throws SQLException {
        ClientService clientController = new ClientService(new ClientDAO());
        return clientController.getAll();
    }

    @Override
    public void setupTextFields() {
        throw new UnsupportedOperationException("Unimplemented method 'setupTextFields'");
    }

}

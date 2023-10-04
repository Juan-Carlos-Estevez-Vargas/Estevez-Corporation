package dev.juan.estevez.views.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.controllers.ClientController;
import dev.juan.estevez.interfaces.GUIInterface;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.reports.GeneratePDFReport;
import dev.juan.estevez.utils.Bounds;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.enums.Clients;
import dev.juan.estevez.utils.enums.Colors;
import dev.juan.estevez.utils.enums.Fonts;
import dev.juan.estevez.utils.enums.Icons;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.views.LoginView;
import panel.utilities.RestorePassword;

/**
 * @author Juan Carlos Estevez Vargas
 */
public class EmployeePanelView extends JFrame implements ActionListener, GUIInterface {

	private static final long serialVersionUID = 1L;
	private JPanel panelBack;
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
        panelBack = new JPanel();
        panelBack.setBackground(Colors.BACKGROUND_COLOR.getValue());
        panelBack.setLayout(null);
        setContentPane(panelBack);
    }

	@Override
    public void setupLabels() {
        GUIComponents.createLabel(this.user, Bounds.LABEL_ADMIN_TITLE, panelBack);
		GUIComponents.createLabel(Constants.CLIENT_REGISTER_TEXT, Bounds.LABEL_ADMIN_USER_REGISTER, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.MANAGE_CLIENTS_TEXT, Bounds.LABEL_ADMIN_MANAGE_USERS, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
        GUIComponents.createLabel(Constants.PRINT_CLIENTS_TEXT, Bounds.LABEL_ADMIN_PRINT_USERS, panelBack).setFont(Fonts.PANEL_LABEL_FONT.getValue());
    }

	@Override
    public void setupButtons() {
        btnRegisterClient = GUIComponents.createButton(Icons.ADD_CLIENT_ICON.getValue(), Bounds.BUTTON_ADMIN_REGISTER, null, panelBack);
		btnManageClient = GUIComponents.createButton(Icons.INFORMATION_USER_ICON.getValue(), Bounds.BUTTON_ADMIN_MANAGE_USERS, null, panelBack);
        btnPrintClients = GUIComponents.createButton(Icons.PRINT_USERS_ICON.getValue(), Bounds.BUTTON_ADMIN_PRINT_USERS, null, panelBack);	
        btnRestorePass = GUIComponents.createButton(Icons.RESTORE_PASS_ICON.getValue(), Bounds.BUTTON_RESTORE_PASSWORD, null, panelBack);
        btnLogout = GUIComponents.createLogoutButton(panelBack);
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
            generateClientsPDFReport(clients, outputPath);
            JOptionPane.showMessageDialog(null, Constants.CLIENT_LIST_CREATED_SUCCESSFULLY);
        } catch (IOException | DocumentException | SQLException ex) {
            JOptionPane.showMessageDialog(null, Constants.GENERATE_ERROR_PDF);
        }
    }
    
    /**
     * Fetches all clients from the database.
     *
     * @return  a list of Client objects representing all clients in the database
     * @throws  SQLException  if there is an error while fetching the clients
     */
    private List<Client> fetchAllClients() throws SQLException {
        ClientController clientController = new ClientController(new ClientDAO());
        return clientController.getAllClients();
    }
    
    /**
     * Generates a PDF report for the given list of clients and saves it to the specified output path.
     *
     * @param  clients     the list of clients to include in the report
     * @param  outputPath  the path where the generated PDF report will be saved
     * @throws IOException         if there is an error reading or writing to the file
     * @throws DocumentException   if there is an error creating the PDF document
     */
    private void generateClientsPDFReport(List<Client> clients, String outputPath) throws IOException, DocumentException {
        String[] clientHeaders = { Clients.ID.getValue(), Clients.NAME.getValue(), Clients.EMAIL.getValue(), 
			Clients.PHONE.getValue(), Clients.ADDRESS.getValue() };
        List<String[]> clientData = new ArrayList<>();
    
        for (Client client : clients) {
            String[] clientRow = {
                String.valueOf(client.getClientID()),
                client.getClientName(),
                client.getClientEmail(),
                client.getClientPhone(),
                client.getClientAddress()
            };

            clientData.add(clientRow);
        }
    
        GeneratePDFReport.generatePDFReport(clientData, clientHeaders, Constants.CLIENT_LIST, outputPath);
    }

	@Override
	public void setupTextFields() {
		throw new UnsupportedOperationException("Unimplemented method 'setupTextFields'");
	}

}

package dev.juan.estevez.views.employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Colors;
import dev.juan.estevez.enums.Equipments;
import dev.juan.estevez.enums.Fonts;
import dev.juan.estevez.interfaces.IGui;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.models.Equipment;
import dev.juan.estevez.persistence.ClientDAO;
import dev.juan.estevez.persistence.EquipmentDAO;
import dev.juan.estevez.reports.CustomPDFReport;
import dev.juan.estevez.services.impl.ClientService;
import dev.juan.estevez.services.impl.EquipmentService;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.ViewUtils;
import dev.juan.estevez.utils.bounds.Bounds;
import dev.juan.estevez.utils.bounds.cap.ClientInfoBounds;
import dev.juan.estevez.utils.constants.CapConstants;
import dev.juan.estevez.utils.constants.ReportConstants;
import dev.juan.estevez.utils.gui.GUIComponents;
import dev.juan.estevez.utils.validators.FieldValidator;
import dev.juan.estevez.utils.validators.ValidateCharacters;
import dev.juan.estevez.utils.validators.ValidateNumbers;
import dev.juan.estevez.views.LoginView;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class ClientInformationView extends JFrame implements ActionListener, IGui {

	private static final long serialVersionUID = 1L;
	private String user = "", user_update = "";
	private DefaultTableModel model = new DefaultTableModel();
	private ClientService clientController;
	private EquipmentService equipmentController;
	public static int idEquipment = 0;
	public static int idClient = 0;
	private JLabel labelTitle;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtAdress;
	private JTextField txtModifyBy;
	private JTable tableEquipment;
	private JPanel panel;
	private JButton btnRegisterEquipment;
	private JButton btnUpdateClient;
	private JButton btnPrint;

	public ClientInformationView() {
		this.user = LoginView.user;
		user_update = ManagementClientsView.user_update;
		idClient = ManagementClientsView.id_cliente_update;
		clientController = new ClientService(new ClientDAO());
		equipmentController = new EquipmentService(new EquipmentDAO());
		initializeFrame();
		initComponents();
		loadClientData();
		loadEquipmentTable();
		setupEquipmentTableEvent();
	}

	@Override
	public void initializeFrame() {
		setSize(930, 400);
		setTitle(String.format(CapConstants.CLIENT_INFO_PANEL_SESION, user_update, user));
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
		setupEvents();
	}

	@Override
	public void setupMainPanel() {
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND_COLOR.getValue());
		panel.setLayout(null);
		panel.setBounds(930, 400, 930, 400);
		setContentPane(panel);
	}

	@Override
	public void setupLabels() {
		labelTitle = GUIComponents.createLabel(String.format(CapConstants.CLIENT_INFO_TITLE, user_update), ClientInfoBounds.LABEL_TITLE, panel);
		labelTitle.setFont(Fonts.BUTTON_FONT.getValue());
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

		GUIComponents.createLabel(Clients.NAME.getValue(), ClientInfoBounds.LABEL_NAME, panel);
		GUIComponents.createLabel(Clients.EMAIL.getValue(), ClientInfoBounds.LABEL_EMAIL, panel);
		GUIComponents.createLabel(Clients.PHONE.getValue(), ClientInfoBounds.LABEL_PHONE, panel);
		GUIComponents.createLabel(Clients.ADDRESS.getValue(), ClientInfoBounds.LABEL_ADDRESS, panel);
		GUIComponents.createLabel(Clients.MODIFY_BY.getValue(), ClientInfoBounds.LABEL_MODIFY_BY, panel);
	}

	@Override
	public void setupTextFields() {
		txtName = GUIComponents.createTextField(ClientInfoBounds.TXT_NAME, panel);
		txtEmail = GUIComponents.createTextField(ClientInfoBounds.TXT_EMAIL, panel);
		txtPhone = GUIComponents.createTextField(ClientInfoBounds.TXT_PHONE, panel);
		txtAdress = GUIComponents.createTextField(ClientInfoBounds.TXT_ADDRESS, panel);
		txtModifyBy = GUIComponents.createTextField(ClientInfoBounds.TXT_MODIFY_BY, panel);
		txtModifyBy.setEnabled(false);
	}

	@Override
	public void setupButtons() {
		btnUpdateClient = GUIComponents.createButton(CapConstants.UPDATE_CLIENT, ClientInfoBounds.BUTTON_UPDATE,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), panel);
		btnRegisterEquipment = GUIComponents.createButton(CapConstants.EQUIPMENT_REGISTER,
				ClientInfoBounds.BUTTON_REGISTER_EQUIP, Colors.BUTTON_COLOR.getValue(),
				Fonts.LABEL_FONT.getValue(), panel);
		btnPrint = GUIComponents.createButton(CapConstants.EQUIPMENT_PRINT, ClientInfoBounds.BUTTON_PRINT,
				Colors.BUTTON_COLOR.getValue(), Fonts.LABEL_FONT.getValue(), panel);
	}

	@Override
	public void setupEvents() {
		txtName.addKeyListener(new ValidateCharacters());
		txtPhone.addKeyListener(new ValidateNumbers());
		btnPrint.addActionListener(this);
		btnUpdateClient.addActionListener(this);
		btnRegisterEquipment.addActionListener(this);
	}

	/**
	 * Loads the client data and populates the corresponding fields in the UI.
	 */
	private void loadClientData() {
		Client client = clientController.getById(idClient);

		if (client != null) {
			txtName.setText(client.getName());
			txtEmail.setText(client.getEmail());
			txtPhone.setText(client.getPhone());
			txtAdress.setText(client.getAddress());
			txtModifyBy.setText(client.getLastModification());
			labelTitle.setText(labelTitle.getText() + " " + client.getName());
		}
	}

	/**
	 * Loads the equipment table from the database.
	 */
	private void loadEquipmentTable() {
		List<Equipment> equipments = equipmentController.getAllByClientId(idClient);

		if (!equipments.isEmpty() && equipments != null) {
			createTableAndScrollPane();
			addColumnsToTable();

			try {
				fillTableWithData(equipments);
			} catch (SQLException e) {
				StringUtils.handleQueryError(e, Constants.ERROR_SHOWING_INFORMATION);
			}
		}
	}

	/**
	 * Creates a table and scroll pane.
	 */
	private void createTableAndScrollPane() {
		tableEquipment = GUIComponents.createTable(model, panel);
		GUIComponents.createScrollPanel(tableEquipment, Bounds.SCROLL_MANAGE, panel);
	}

	/**
	 * Adds columns to the table.
	 */
	private void addColumnsToTable() {
		model.addColumn(Equipments.ID.getValue());
		model.addColumn(Equipments.TYPE.getValue());
		model.addColumn(Equipments.MARK.getValue());
		model.addColumn(Equipments.STATUS.getValue());

		// Configurar la columna "id" como oculta
		TableColumn idColumn = tableEquipment.getColumnModel().getColumn(0);
		idColumn.setMinWidth(0);
		idColumn.setMaxWidth(0);
		idColumn.setPreferredWidth(0);
		idColumn.setResizable(false);
	}

	/**
	 * Fills the table with data from a list of Equipment objects.
	 *
	 * @param equipmentList the list of Equipment objects to populate the table with
	 * @throws SQLException if there is an error accessing the database
	 */
	private void fillTableWithData(List<Equipment> equipmentList) throws SQLException {
		DefaultTableModel model = (DefaultTableModel) tableEquipment.getModel();

		equipmentList.forEach(equipment -> {
			if (equipment != null) {
				model.addRow(new Object[] {
					equipment.getId(),
					equipment.getType(),
					equipment.getMark(),
					equipment.getStatus()
				});
			}
		});
	}

	/**
	 * Sets up the event listener for the equipment table.
	 */
	private void setupEquipmentTableEvent() {
		this.tableEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableEquipment.rowAtPoint(e.getPoint());
				int column = 0;

				if (row > -1) {
					idEquipment = (int) model.getValueAt(row, column);
					ViewUtils.openPanel(new EquipmentInformationView(idEquipment), ClientInformationView.this);
				}
			}
		});
	}

	public void clean() {
		this.txtName.setText("");
		this.txtAdress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegisterEquipment) {
			ViewUtils.openPanel(new RegisterEquipmentView());
		} else if (e.getSource() == btnUpdateClient) {
			updateClient();
		} else if (e.getSource() == btnPrint) {
			handlePrintEquipments();
		}
	}

	/**
	 * Updates the client information based on the inputs provided.
	 */
	private void updateClient() {
		Client client = createClientFromInputs();
		int validation = validateClientFields(client);

		if (validation == 4) {
			StringUtils.showMessage(Constants.EMPTY_FIELDS);
			return;
		}

		if (validation == 0) {
			if (clientController.updateClient(client) == 1) {
				StringUtils.showMessage(Constants.SUCCESSFUL_MODIFICATION);
				clean();
				setFieldsBackground(Color.green);
				ViewUtils.openPanel(new ManagementClientsView(), this);
			}
		}
	}

	/**
	 * Creates a client object from the input fields.
	 *
	 * @return the created client object
	 */
	private Client createClientFromInputs() {
		return Client.builder()
			.id(idClient)
			.name(txtName.getText().trim())
			.email(txtEmail.getText().trim())
			.phone(txtPhone.getText().trim())
			.address(txtAdress.getText().trim())
			.lastModification(user)
			.build();
	}

	/**
	 * Validates the fields of a client object and returns the total number of
	 * validation errors.
	 *
	 * @param client the client object to be validated
	 * @return the total number of validation errors
	 */
	private int validateClientFields(Client client) {
		int validation = FieldValidator.validateEmailField(client.getEmail(), txtEmail)
				+ FieldValidator.validateNameField(client.getName(), txtName)
				+ FieldValidator.validatePhoneField(client.getPhone(), txtPhone)
				+ FieldValidator.validateAdressField(client.getAddress(), txtAdress);

		return validation;
	}

	/**
	 * Sets the background color of the fields.
	 *
	 * @param color the color to set the background to
	 */
	private void setFieldsBackground(Color color) {
		txtName.setBackground(color);
		txtEmail.setBackground(color);
		txtAdress.setBackground(color);
		txtPhone.setBackground(color);
	}

	/**
	 * Handles the action of printing the equipments.
	 */
	private void handlePrintEquipments() {
		JFileChooser fc = new JFileChooser();
		int response = fc.showSaveDialog(this);

		if (response != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File chosenFile = fc.getSelectedFile();
		String outputPath = chosenFile.getAbsolutePath() + ReportConstants.PDF_EXTENSION;

		try {
			List<Equipment> equipments = equipmentController.getAllByClientId(idClient);
			CustomPDFReport.generateEquipmentsPDFReport(equipments, outputPath);
			JOptionPane.showMessageDialog(null, ReportConstants.PDF_REPORT_CREATED);
		} catch (IOException | DocumentException ex) {
			JOptionPane.showMessageDialog(null, ReportConstants.GENERATE_PDF_ERROR);
		}
	}

}

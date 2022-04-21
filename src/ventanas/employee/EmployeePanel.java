package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import modelo.DatabaseConnection;
import ventanas.Login;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 *
 */
public class EmployeePanel extends JFrame implements ActionListener {

	/**
	 * Definición de Variables.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTittle;
	private JLabel labelRegisterClient;
	private JLabel labelManageClient;
	private JLabel labelPrintClients;
	private JPanel panelBack;
	private JButton btnRegisterClient;
	private JButton btnManageClient;
	private JButton btnPrintClients;
	private JComboBox<String> cmbRole;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public EmployeePanel() {
		this.user = Login.user;
		this.setSize(670, 310);
		this.setTitle("Capturista - Sesión de " + this.user);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.initComponents();

		/**
		 * Recuperando el nombre del usuario.
		 */
		try {
			Connection cn = (Connection) DatabaseConnection.conectar();
			PreparedStatement pst = (PreparedStatement) cn
					.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = '" + user + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameUser = rs.getString("nombre_usuario");
				this.labelTittle.setText(nameUser);
			}
		} catch (SQLException e) {
			System.err.println("Error en consultar capturista");
		}
	}

	/**
	 * Inicializa y construye los componentes Swing en el Frame principal.
	 */
	public void initComponents() {

		/**
		 * Panel Principal.
		 */
		this.panelBack = new JPanel();
		this.panelBack.setBackground(new Color(46, 59, 104));
		this.panelBack.setLayout(null);
		this.setContentPane(this.panelBack);

		/*
		 * Label Principal.
		 */
		this.labelTittle = new JLabel();
		this.labelTittle.setBounds(10, 10, 280, 27);
		this.labelTittle.setForeground(new Color(192, 192, 192));
		this.labelTittle.setFont(new Font("serif", Font.BOLD, 20));
		this.panelBack.add(this.labelTittle);

		/*
		 * Botón para registrar un nuevo Cliente en el sistema.
		 */
		this.btnRegisterClient = new JButton();
		this.btnRegisterClient.setBounds(40, 80, 120, 100);
		this.btnRegisterClient.setIcon(new ImageIcon("src/img/add.png"));
		this.btnRegisterClient.addActionListener(this);
		this.panelBack.add(this.btnRegisterClient);

		/**
		 * Label Registrar Cliente.
		 */
		this.labelRegisterClient = new JLabel("Registrar Cliente");
		this.labelRegisterClient.setBounds(45, 190, 120, 15);
		this.labelRegisterClient.setForeground(new Color(192, 192, 192));
		this.labelRegisterClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelRegisterClient);

		/**
		 * Botón encargado de mostrar la lista de Clientes registrados en el sistema.
		 */
		this.btnManageClient = new JButton();
		this.btnManageClient.setBounds(270, 80, 120, 100);
		this.btnManageClient.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageClient.addActionListener(this);
		this.panelBack.add(this.btnManageClient);

		/**
		 * Label Gestionar Clientes.
		 */
		this.labelManageClient = new JLabel("Gestionar Clientes");
		this.labelManageClient.setBounds(270, 190, 120, 15);
		this.labelManageClient.setForeground(new Color(192, 192, 192));
		this.labelManageClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManageClient);

		/**
		 * Label Imprimir Clientes.
		 */
		this.labelPrintClients = new JLabel("Imprimir Clientes");
		this.labelPrintClients.setBounds(500, 190, 120, 15);
		this.labelPrintClients.setForeground(new Color(192, 192, 192));
		this.labelPrintClients.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelPrintClients);

		/**
		 * Botón para imprimir los clientes.
		 */
		this.btnPrintClients = new JButton();
		this.btnPrintClients.setBounds(500, 80, 120, 100);
		this.btnPrintClients.setIcon(new ImageIcon("src/img/impresora.png"));
		this.btnPrintClients.addActionListener(this);
		this.panelBack.add(this.btnPrintClients);

		/**
		 * ComboBox encargado de mostrar los roles a los que puede acceder el cliente
		 * Capturista junto con el apartado de cerrar sesión.
		 */
		this.cmbRole = new JComboBox<>();
		this.cmbRole.addItem("Capturista");
		this.cmbRole.addItem("Cerrar Sesión");
		this.cmbRole.setBounds(500, 20, 120, 20);
		this.panelBack.add(this.cmbRole);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnRegisterClient) {
			RegisterClient registerClient = new RegisterClient();
			registerClient.setVisible(true);
		}

		if (e.getSource() == this.btnManageClient) {
			ManagementClients managementClientes = new ManagementClients();
			managementClientes.setVisible(true);
		}

		if (e.getSource() == this.btnPrintClients) {
			Document document = new Document();
			try {
				String ruta = System.getProperty("user.home");
				PdfWriter.getInstance(document, new FileOutputStream(ruta + "\\Reporte_clientes.pdf"));

				com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/img/BannerPDF2.jpg");
				header.scaleToFit(650, 1000);
				header.setAlignment(Chunk.ALIGN_CENTER);

				Paragraph paragraph = new Paragraph();
				paragraph.setAlignment(Paragraph.ALIGN_CENTER);
				paragraph.add("Lista de Clientes\n\n");
				paragraph.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));

				document.open();
				document.add(header);
				document.add(paragraph);

				PdfPTable table = new PdfPTable(5);
				table.addCell("ID Cliente");
				table.addCell("Nombre");
				table.addCell("Email");
				table.addCell("Teléfono");
				table.addCell("Dirección");

				try {
					Connection cn = (Connection) DatabaseConnection.conectar();
					PreparedStatement pst = (PreparedStatement) cn.prepareStatement("SELECT * FROM clientes");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {
						do {
							table.addCell(rs.getString(1));
							table.addCell(rs.getString(2));
							table.addCell(rs.getString(3));
							table.addCell(rs.getString(4));
							table.addCell(rs.getString(5));
						} while (rs.next());
						document.add(table);
					}
				} catch (SQLException ex) {
					System.err.println("Error al generar lista de clientes " + ex);
				}
				document.close();
				JOptionPane.showMessageDialog(null, "Lista de clientes creada correctamente");
			} catch (DocumentException | IOException ex) {
				System.err.println("Error al generar PDF " + ex);
				JOptionPane.showMessageDialog(null, "¡¡Error al generar PDF!! Contacte al Administrador");
			}

		}

	}

}

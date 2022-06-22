package ventanas.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
 * Frame principal del empleado (Vendedor, Capturista).
 * 
 * @author
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
	private JButton btnLogout;
	private String user;
	private String nameUser;

	/**
	 * Constructor de clase.
	 */
	public EmployeePanel() {
		this.user = Login.user;
		this.setSize(630, 280);
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
		this.btnRegisterClient.setIcon(new ImageIcon("src/img/addClient.png"));
		this.btnRegisterClient.addActionListener(this);
		this.btnRegisterClient.setBorder(null);
		this.btnRegisterClient.setBackground(new Color(46, 59, 104));
		this.btnRegisterClient.setOpaque(true);
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
		this.btnManageClient.setBounds(250, 80, 120, 100);
		this.btnManageClient.setIcon(new ImageIcon("src/img/informationuser.png"));
		this.btnManageClient.addActionListener(this);
		this.btnManageClient.setBorder(null);
		this.btnManageClient.setBackground(new Color(46, 59, 104));
		this.btnManageClient.setOpaque(true);
		this.panelBack.add(this.btnManageClient);

		/**
		 * Label Gestionar Clientes.
		 */
		this.labelManageClient = new JLabel("Gestionar Clientes");
		this.labelManageClient.setBounds(250, 190, 120, 15);
		this.labelManageClient.setForeground(new Color(192, 192, 192));
		this.labelManageClient.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelManageClient);

		/**
		 * Label Imprimir Clientes.
		 */
		this.labelPrintClients = new JLabel("Imprimir Clientes");
		this.labelPrintClients.setBounds(460, 190, 200, 15);
		this.labelPrintClients.setForeground(new Color(192, 192, 192));
		this.labelPrintClients.setFont(new Font("serif", Font.BOLD, 14));
		this.panelBack.add(this.labelPrintClients);

		/**
		 * Botón para imprimir los clientes.
		 */
		this.btnPrintClients = new JButton();
		this.btnPrintClients.setBounds(460, 80, 120, 100);
		this.btnPrintClients.setIcon(new ImageIcon("src/img/impresora.png"));
		this.btnPrintClients.addActionListener(this);
		this.btnPrintClients.setBorder(null);
		this.btnPrintClients.setBackground(new Color(46, 59, 104));
		this.btnPrintClients.setOpaque(true);
		this.panelBack.add(this.btnPrintClients);

		/**
		 * Botón para cerrar sesión.
		 */
		this.btnLogout = new JButton("Cerrar Sesión");
		this.btnLogout.setBounds(470, 20, 120, 30);
		this.btnLogout.setFont(new Font("serif", Font.BOLD, 14));
		this.btnLogout.setBackground(new Color(8, 85, 224));
		this.btnLogout.setForeground(Color.WHITE);
		this.btnLogout.setHorizontalAlignment(JButton.CENTER);
		this.btnLogout.addActionListener(this);
		this.panelBack.add(this.btnLogout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/**
		 * Registro de clientes en la base de datos.
		 */
		if (e.getSource() == this.btnRegisterClient) {
			RegisterClient registerClient = new RegisterClient();
			registerClient.setVisible(true);
		}

		/**
		 * Cierra la sesión del usuario actual.
		 */
		if (e.getSource() == this.btnLogout) {
			Login login = new Login();
			login.setLocationRelativeTo(null);
			login.setVisible(true);
			this.dispose();
		}

		/**
		 * Redirección al panel con los clientes registrados.
		 */
		if (e.getSource() == this.btnManageClient) {
			ManagementClients managementClientes = new ManagementClients();
			managementClientes.setVisible(true);
		}

		/**
		 * Impresión de los clientes registrados en el sistema.
		 */
		if (e.getSource() == this.btnPrintClients) {
			Document document = new Document();

			try {
				JFileChooser fc = new JFileChooser();
				// Mostrar la ventana para abrir archivo y recoger la respuesta
				// En el parámetro del showOpenDialog se indica la ventana
				// al que estará asociado. Con el valor this se asocia a la
				// ventana que la abre.
				int response = fc.showSaveDialog(this);
				// Comprobar si se ha pulsado Aceptar
				if (response == JFileChooser.APPROVE_OPTION) {
					// Crear un objeto File con el archivo elegido
					File chosenFile = fc.getSelectedFile();

					PdfWriter.getInstance(document, new FileOutputStream(chosenFile + ".pdf"));

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

package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.DatabaseConnection;

public class ManagementUsers extends JFrame implements ActionListener {

	private JLabel jlabel1;
	private JPanel container;
	private JTable tableUsers;
	private JScrollPane scrollPaneUsers;
	DefaultTableModel model = new DefaultTableModel();
	
	public ManagementUsers() {
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Usuarios registrados - Sesi�n de ");
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.initComponents();
		//Hacemos la conexion a la base de datos
        try {
            Connection cn = DatabaseConnection.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT id_usuario, nombre_usuario, username, tipo_nivel, estatus FROM usuarios");

            ResultSet rs = pst.executeQuery();

            //Creamos la tabla y la a�adimos al jScrollPanel
            this.tableUsers = new JTable(model);
    		this.scrollPaneUsers = new JScrollPane(this.tableUsers);
    		this.scrollPaneUsers.setBounds(45,55,520,200);
    		

            //A�adimos las columnas a la tabla
            model.addColumn(" ");
            model.addColumn("Nombre Usuario");
            model.addColumn("Username");
            model.addColumn("Permisos");
            model.addColumn("Estatus");

            //Llenado de la tabla
            while (rs.next()) { //Cada que la base de datos encuentre resultados
                Object[] fila = new Object[5];

                for (int i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);
                }

                model.addRow(fila);
            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar Tabla " + e);
            JOptionPane.showMessageDialog(null, "��Error al mostrar informacion!! Contacte al Administrador");
        }

        this.container.add(scrollPaneUsers);
	}

	public void initComponents() {
		this.container = new JPanel();
		this.container.setBackground(new Color(46, 59, 104));
		this.container.setLayout(null);
		this.setContentPane(container);

		this.jlabel1 = new JLabel("Usuarios Registrados");
		this.jlabel1.setBounds(210, 10, 250, 20);
		this.jlabel1.setForeground(new Color(192, 192, 192));
		this.jlabel1.setFont(new Font("serif", Font.BOLD, 20));
		this.container.add(jlabel1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

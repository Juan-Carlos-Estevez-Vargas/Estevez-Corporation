package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

	String[] cabe = {"1","2"};
	String [][] data = {{"a","b"},{"c","d"},{"e","f"}};
	private JLabel jlabel1, labelFooter;
	private JPanel container;
	private JTable tableUsers;
	private JScrollPane scrollPaneUsers;
	DefaultTableModel model = new DefaultTableModel(data, cabe);
	
	public ManagementUsers() {
		this.setSize(630, 340);
		this.setResizable(false);
		this.setTitle("Usuarios registrados - Sesión de ");
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.initComponents();
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
		
		this.tableUsers = new JTable(model);
		this.scrollPaneUsers = new JScrollPane(this.tableUsers);
		this.scrollPaneUsers.setBounds(45,55,520,200);
		this.container.add(scrollPaneUsers);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

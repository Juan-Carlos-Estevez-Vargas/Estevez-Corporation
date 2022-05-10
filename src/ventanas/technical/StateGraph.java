package ventanas.technical;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import modelo.DatabaseConnection;
import ventanas.Login;

public class StateGraph extends JFrame{


	private static final long serialVersionUID = 1L;

	String user;
    int nuevo_ingreso, no_reparado, en_revision, reparado, entregado;

    String[] vector_estatus_nombre = new String[5];
    int[] vector_estatus_cantidad = new int[5];
    
    public StateGraph() {
    	//initComponents();
        user = Login.user;
        this.setSize(515, 360);
        this.setResizable(false);
        this.setTitle("Técnico - Sesión de " + user);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {

            Connection cn = (Connection) DatabaseConnection.conectar();
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(
                    "SELECT estatus, COUNT(estatus) AS Cantidad FROM equipos GROUP BY estatus");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int posicion = 0;

                do {
                    vector_estatus_nombre[posicion] = rs.getString(1);
                    vector_estatus_cantidad[posicion] = rs.getInt(2);

                    if (vector_estatus_nombre[posicion].equalsIgnoreCase("En revision")) {
                        en_revision = vector_estatus_cantidad[posicion];
                    } else if (vector_estatus_nombre[posicion].equalsIgnoreCase("Entregado")) {
                        entregado = vector_estatus_cantidad[posicion];
                    } else if (vector_estatus_nombre[posicion].equalsIgnoreCase("No reparado")) {
                        no_reparado = vector_estatus_cantidad[posicion];
                    } else if (vector_estatus_nombre[posicion].equalsIgnoreCase("Nuevo ingreso")) {
                        nuevo_ingreso = vector_estatus_cantidad[posicion];
                    } else if (vector_estatus_nombre[posicion].equalsIgnoreCase("Reparado")) {
                        reparado = vector_estatus_cantidad[posicion];
                    }

                    posicion++;

                } while (rs.next());

            }

        } catch (SQLException e) {
            System.err.println("Error en conectar con la base de datos " + e);
            JOptionPane.showMessageDialog(null, "¡¡Error!! Contacte al Administrador");
        }
        
    }

    public int estatusMasRepetido(int NuevoIngreso, int NoReparado, int EnRevision, int Reparado, int Entregado) {

        //Validando estatus mas grande
        if (NuevoIngreso > NoReparado && NuevoIngreso > EnRevision && NuevoIngreso > Reparado && NuevoIngreso > Entregado) {
            return NuevoIngreso;
        } else if (NoReparado > EnRevision && NoReparado > Reparado && NoReparado > Entregado) {
            return NoReparado;
        } else if (EnRevision > Reparado && EnRevision > Entregado) {
            return EnRevision;
        } else if (Reparado > Entregado) {
            return Reparado;
        } else {
            return Entregado;
        }

    }

    //Graficando las barras
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        int estatus_mas_repetido = estatusMasRepetido(nuevo_ingreso, no_reparado, en_revision, reparado, entregado);

        int largo_nuevo_ingreso = nuevo_ingreso * 400 / estatus_mas_repetido;
        int largo_no_reparado = no_reparado * 400 / estatus_mas_repetido;
        int largo_en_revision = en_revision * 400 / estatus_mas_repetido;
        int largo_reparado = reparado * 400 / estatus_mas_repetido;
        int largo_entragado = entregado * 400 / estatus_mas_repetido;
        
        g.setColor(new Color(46, 59, 104));
        g.fillRect(0, 0, 515, 360);

        //Pintando la gráfica de barras
        g.setColor(Color.yellow);
        g.fillRect(100, 100, largo_nuevo_ingreso, 40);
        g.drawString("Nuevo Ingreso", 10, 118);
        g.drawString("Cantidad " + nuevo_ingreso, 10, 133);
        
        g.setColor(Color.red);
        g.fillRect(100, 150, largo_no_reparado, 40);
        g.drawString("No Reparado", 10, 168);
        g.drawString("Cantidad " + no_reparado, 10, 183);
        
        g.setColor(Color.black);
        g.fillRect(100, 200, largo_en_revision, 40);
        g.drawString("En Revisión", 10, 218);
        g.drawString("Cantidad " + en_revision, 10, 233);
        
        g.setColor(Color.white);
        g.fillRect(100, 250, largo_reparado, 40);
        g.drawString("Reparado", 10, 268);
        g.drawString("Cantidad " + reparado, 10, 283);
        
        g.setColor(Color.green);
        g.fillRect(100, 300, largo_entragado, 40);
        g.drawString("Entregado", 10, 318);
        g.drawString("Cantidad " + entregado, 10, 333);
        

    }



}

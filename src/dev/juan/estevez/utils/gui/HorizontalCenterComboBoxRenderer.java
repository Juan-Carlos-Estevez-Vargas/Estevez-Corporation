package dev.juan.estevez.utils.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public class HorizontalCenterComboBoxRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setHorizontalAlignment(CENTER); // Centrar horizontalmente el contenido
        return this;
    }
    
}

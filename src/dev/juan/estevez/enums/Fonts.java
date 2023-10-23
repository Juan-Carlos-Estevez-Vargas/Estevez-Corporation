package dev.juan.estevez.enums;

import java.awt.Font;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public enum Fonts {

    ERROR_FONT(new Font("serif", Font.PLAIN, 17)),
    LABEL_FONT(new Font("serif", Font.PLAIN, 20)),
    BUTTON_FONT(new Font("serif", Font.BOLD, 22)),
    PANEL_LABEL_FONT(new Font("serif", Font.BOLD, 14)),
    HEADER_TABLE_FONT(new Font("serif", Font.BOLD, 18)),
    CONTENT_TABLE_FONT(new Font("serif", Font.PLAIN, 14));

    private final Font value;

    Fonts(Font value) {
        this.value = value;
    }

    public Font getValue() {
        return value;
    }
}

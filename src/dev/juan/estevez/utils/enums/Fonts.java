package dev.juan.estevez.utils.enums;

import java.awt.Font;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Fonts {

    BUTTON_FONT(new Font("serif", Font.BOLD, 22)),
    ERROR_FONT(new Font("serif", Font.PLAIN, 17)),
    LABEL_FONT(new Font("serif", Font.BOLD, 20)),
    PANEL_LABEL_FONT(new Font("serif", Font.BOLD, 14));

    private final Font value;

    Fonts(Font value) {
        this.value = value;
    }

    public Font getValue() {
        return value;
    }
}

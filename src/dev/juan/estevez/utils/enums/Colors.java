package dev.juan.estevez.utils.enums;

import java.awt.Color;

public enum Colors {

    BACKGROUND_COLOR(new Color(46, 59, 104)),
    BUTTON_COLOR(new Color(8, 85, 224)),
    ERROR_COLOR(new Color(192, 192, 192)),
    TEXT_FIELD_COLOR(new Color(127, 140, 141));

    private final Color value;

    Colors(Color value) {
        this.value = value;
    }

    public Color getValue() {
        return value;
    }
}


package dev.juan.estevez.utils.enums;

import javax.swing.ImageIcon;

public enum Icons {

    ADD_CLIENT_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/addClient.png")),
    ADD_USER_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/addUser.png")),
    EYE_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/ojo_opt.png")),
    INFORMATION_USER_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/informationuser.png")),
    PRINT_USERS_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/impresora.png")),
    REGISTER_USER_BUTTON_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/addClient.png")),
    RESTORE_PASS_ICON(new ImageIcon("src/dev/juan/estevez/assets/img/padlock.png"));

    private final ImageIcon value;

    Icons(ImageIcon value) {
        this.value = value;
    }

    public ImageIcon getValue() {
        return value;
    }
}

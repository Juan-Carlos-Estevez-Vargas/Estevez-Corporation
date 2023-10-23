package dev.juan.estevez.enums;

import javax.swing.ImageIcon;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public enum Icons {

    EYE(new ImageIcon("src/dev/juan/estevez/assets/img/ojo_opt.png")),
    ADD_USER(new ImageIcon("src/dev/juan/estevez/assets/img/addUser.png")),
    ADD_CLIENT(new ImageIcon("src/dev/juan/estevez/assets/img/addClient.png")),
    PRINT_USERS(new ImageIcon("src/dev/juan/estevez/assets/img/impresora.png")),
    RESTORE_PASS(new ImageIcon("src/dev/juan/estevez/assets/img/padlock.png")),
    REGISTER_CLIENT(new ImageIcon("src/dev/juan/estevez/assets/img/registerClient.png")),
    INFORMATION_USER(new ImageIcon("src/dev/juan/estevez/assets/img/informationuser.png")),
    REGISTER_USER_BUTTON(new ImageIcon("src/dev/juan/estevez/assets/img/addClient.png"));

    private final ImageIcon value;

    Icons(ImageIcon value) {
        this.value = value;
    }

    public ImageIcon getValue() {
        return value;
    }
}

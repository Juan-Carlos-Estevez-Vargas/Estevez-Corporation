package dev.juan.estevez.utils.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Clients {

    ID("ID Cliente"),
    NAME("Nombre"),
    EMAIL("Correo"),
    PHONE("Teléfono"),
    ADDRESS("Dirección");

    private final String value;

    Clients(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

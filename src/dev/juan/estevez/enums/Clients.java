package dev.juan.estevez.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Clients {

    ID("ID Cliente"),
    NAME("Nombre"),
    EMAIL("Correo"),
    PHONE("Teléfono"),
    ADDRESS("Dirección"),
    MODIFY_BY("Última modificación por");

    private final String value;

    Clients(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

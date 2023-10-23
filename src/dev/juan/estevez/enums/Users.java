package dev.juan.estevez.enums;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public enum Users {

    NAME("Nombre"),
    LEVEL("Nivel"),
    EMAIL("Correo"),
    PHONE("Teléfono"),
    STATUS("Estado"),
    USERNAME("Usuario"),
    PERMISIONS("Permisos"),
    PERMISIONS_OF("Permisos de"),
    REGISTERED_BY("Registrado por");

    private final String value;

    Users(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

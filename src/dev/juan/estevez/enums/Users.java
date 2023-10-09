package dev.juan.estevez.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Users {
    
    NAME("Nombre"),
    EMAIL("Correo"),
    LEVEL("Nivel"),
    PERMISIONS("Permisos"),
    PERMISIONS_OF("Permisos de"),
    PHONE("Teléfono"),
    REGISTERED_BY("Registrado por"),
    STATUS("Estado"),
    USERNAME("Usuario");

    private final String value;

    Users(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
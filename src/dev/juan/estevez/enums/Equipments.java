package dev.juan.estevez.enums;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public enum Equipments {

    ID("ID"),
    TYPE("Tipo"),
    MARK("Marca"),
    MODEL("Modelo"),
    STATUS("Estatus"),
    CLIENT_ID("ID Cliente"),
    OBSERVATIONS("Observaciones"),
    SERIAL_NUMBER("Número Serial"),
    CREATION_DATE("Fecha de Creación"),
    DATE_OF_ADMISSION("Fecha de Ingreso"),
    LAST_MODIFICATION("Última Modificación"),
    MODIFICATION_DATE("Fecha de Modificación"),
    TECHNICAL_COMMENTS("Comentarios Técnicos"),
    TECHNICAL_REVISION_OF("Revisión Técnica de");

    private final String value;

    Equipments(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package dev.juan.estevez.utils.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Equipments {
    
    ADMISSION_DAY("Día de Ingreso"),
    ADMISSION_MONTH("Mes de Ingreso"),
    ADMISSION_YEAR("Año de Ingreso"),
    CLIENT_ID("ID Cliente"),
    ID("ID"),
    LAST_MODIFICATION("Última Modificación"),
    MARK("Marca"),
    MODEL("Modelo"),
    OBSERVATION("Observación"),
    SERIAL_NUMBER("Número Serial"),
    STATUS("Estatus"),
    TECHNICAL_COMMENTS("Comentarios Técnicos"),
    TECHNICAL_REVISION_OF("Revisión Técnica de"),
    TYPE("Tipo");

    private final String value;

    Equipments(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

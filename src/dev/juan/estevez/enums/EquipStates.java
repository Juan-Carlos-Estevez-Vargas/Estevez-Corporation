package dev.juan.estevez.enums;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public enum EquipStates {

    NEW_ENTRY("Nuevo Ingreso");

    private final String value;

    EquipStates(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String[] getAllValues() {
        EquipStates[] states = EquipStates.values();
        String[] valuesArray = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            valuesArray[i] = states[i].getValue();
        }

        return valuesArray;
    }

}

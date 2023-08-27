package dev.juan.estevez.utils.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum States {

    ACTIVE("Activo"), INACTIVE("Inactivo");

    private final String value;

    States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Retrieves all the values from the States enum and returns them as an array of strings.
     *
     * @return  an array of strings containing all the values from the States enum
     */
    public static String[] getAllValues() {
        States[] states = States.values();
        String[] valuesArray = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            valuesArray[i] = states[i].getValue();
        }

        return valuesArray;
    }
}

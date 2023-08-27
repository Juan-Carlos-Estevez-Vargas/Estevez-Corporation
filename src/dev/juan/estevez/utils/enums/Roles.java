package dev.juan.estevez.utils.enums;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public enum Roles {
    
    ROLE_ADMIN("Administrador"),
    ROLE_CAPTURISTA("Capturista"),
    ROLE_TECH("Tecnico");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Retrieves all the values from the Roles enum and returns them as an array of strings.
     *
     * @return  an array of strings containing all the values from the Roles enum
     */
    public static String[] getAllValues() {
        Roles[] roles = Roles.values();
        String[] valuesArray = new String[roles.length];

        for (int i = 0; i < roles.length; i++) {
            valuesArray[i] = roles[i].getValue();
        }

        return valuesArray;
    }

}

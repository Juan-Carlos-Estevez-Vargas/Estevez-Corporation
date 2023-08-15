package dev.juan.estevez.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

public class Constants {

    // Colors
    public static final Color BACKGROUND_COLOR = new Color(46, 59, 104);
    public static final Color BUTTON_COLOR = new Color(8, 85, 224);
    public static final Color BUTTON_BACKGROUND_COLOR = new Color(46, 59, 104);
    public static final Color ERROR_COLOR = new Color(192, 192, 192);
    public static final Color TEXT_FIELD_COLOR = new Color(127, 140, 141);

    // Fonts
    public static final Font BUTTON_FONT = new Font("serif", Font.BOLD, 22);
    public static final Font ERROR_FONT = new Font("serif", Font.PLAIN, 17);
    public static final Font LABEL_FONT = new Font("serif", Font.BOLD, 20);
    public static final Font PANEL_LABEL_FONT = new Font("serif", Font.BOLD, 14);

    // Icons
    public static final ImageIcon ADD_USER_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/addUser.png");
    public static final ImageIcon EYE_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/ojo_opt.png");
    public static final ImageIcon INFORMATION_USER_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/informationuser.png");
    public static final ImageIcon PRINT_USERS_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/impresora.png");
    public static final ImageIcon REGISTER_USER_BUTTON_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/addClient.png");
    public static final ImageIcon RESTORE_PASS_ICON = new ImageIcon("src/dev/juan/estevez/assets/img/padlock.png");

    // lOGIN VIEW
    public static final String FORGOT_TEXT = "¿Olvidó su contraseña?";
    public static final String LOGIN_ERROR_TEXT = "Usuario y/o contraseña erróneos";
    public static final String LOGIN_LOGO_URL = "src/dev/juan/estevez/assets/img/logo.png";
    public static final String LOGIN_PASSWORD_TEXT = "Contraseña:";
    public static final String LOGIN_USER_TEXT = "Usuario:";
    public static final String LOGOUT_TEXT = "Cerrar Sesión";

    // ROLES
    public static final String ROLE_ADMIN = "Administrador";
    public static final String ROLE_CAPTURISTA = "Capturista";
    public static final String ROLE_TECH = "Tecnico";
    
    // UTILITIES
    public static final String ARE_YOU_SURE_TO_LOGOUT = "¿Está seguro de cerrar la sesión?";
    public static final String EMPTY_FIELDS = "!Debes llenar todos los campos!";
    public static final String SIGN_IN = "Iniciar Sesión";
    
    // REPORTS
    public static final String PDF_BANNER_IMG = "src/dev/juan/estevez/assets/img/BannerPDF2.jpg";
    public static final String PRINT_USERS_TEXT = "Imprimir Usuarios";
    
    // ERRORS
    public static final String INTERNAL_LOGIN_ERROR = "¡Error al iniciar sesión! Contacta al administrador.";
    public static final String INTERNAL_REGISTER_USER_ERROR = "¡Error al registrar usuario! Contacte al Administrador";
    public static final String GENERATE_ERROR_PDF = "¡Error al generar PDF! Contacte al Administrador";
    public static final String USERNAME_COMPARISON_ERROR_MESSAGE = "¡Error al comparar usuario! Contacte con el Administrador";
    public static final String USER_FETCH_ERROR_MESSAGE = "Error al obtener usuario";
    
    // USER
    public static final String INACTIVE_USER = "Usuario Inactivo";
    public static final String MANAGE_USERS_TEXT = "Administrar Usuarios";
    public static final String USER_LIST = "Listado de Usuarios";
    public static final String USER_LIST_CREATED_SUCCESSFULLY = "Listado de usuarios creado correctamente";
    public static final String USER_NAME = "Nombre";
    public static final String USER_REGISTER_TEXT = "Registrar Usuario";
    
    // TOOLS
    public static final String ACTIVE = "Activo";
    public static final String DEFAULT_PASSWORD = "1234";
    public static final String EMAIL = "Correo";
    public static final String INVALID_EMAIL = "El campo EMAIL no es válido";
    public static final String LEVEL = "Nivel";
    public static final String PERMISIONS_OF = "Permisos de:";
    public static final String PHONE = "Teléfono";
    public static final String REGISTERED_BY = "Registrado por";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "Registro exitoso";
    public static final String STATUS = "Estado";
    public static final String USERNAME = "Usuario";
    
    // VALIDATE FIELDS
    public static final String EMAIL_LENGTH_LIMIT_MESSAGE = "El campo EMAIL no debe contener más de 50 caracteres";
    public static final String NAME_LENGTH_LIMIT_MESSAGE = "El campo NOMBRE no debe contener más de 40 caracteres ni menos de 4";
    public static final String PHONE_LENGTH_LIMIT_MESSAGE = "El campo TELEFONO no debe contener más de 12 caracteres ni menos de 10";
    public static final String USERNAME_NOT_AVAILABLE_MESSAGE = "Nombre de usuario no disponible";
}

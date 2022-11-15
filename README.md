# Estevez Corporation
Sistema de información encargado de gestionar el mantenimiento de equipos de computo en una empresa, diseñado para la materia "Programación 2" de la Fundación Universitaria de San Gil, sexto semestre.

Este proyecto es una adaptación del proyecto "Data System" realizado en el canal de YouTube la Geekipedia de Ernesto; esta realizado en código puro y cuenta con 3 roles.

El sistema utiliza diferentes librerías para envío de correos electrónicos, generación de pdf's y conexión con la base de datos, por lo que es imprescindibke agregarlas al proyecto paar su buen funcionamiento.

A la hora de crear usuarios desde el perfil Administrador, se genera automáticamente la contraseña la cuál es > 1234 por lo que se recomienda que los usuarios nuevos cambien su clave lo mas pronto posible.

A la hora de registrar los usuarios es necesario hacerlo con correos reales para que a la hora de intentar restaurar la clave esta pueda ser enviada directamente al correo electrónico del usuario (debe ser Gmail).

No olvides importar a algún servidor como XAMPP la [base de datos del proyecto](https://github.com/Juan-Carlos-Estevez-Vargas/Estevez-Corporation/tree/master/database) para que el programa funcione correctamente; por último, para ejecutar el programa debes hacerlo desde la clase Main.java

## Roles
### Administrador: 
Es quien registra los usuarios con sus respectivos roles, los modifica y también, puede generar reporte en PDF de dichos usuarios.

  > Usuario    =   JCEV                 
  > Contraseña =   admin123

### Capturista o Empleado de mostrador:
Ingresa los clientes, es capaz de registrar los equipos puestos para mantenimiento a un cliente en específico; también, administra la información de los mismos y genera reportes de los clientes en específico junto con los equipos registrados.

  > Usuario    =   CAPGEN   
  > Contraseña =   capgen123

### Técnico:
Este rol es capaz de observar los equipos registrados, la descripción general de los problemas suministrados por el cliente, el tipo de equipo y sus datos básicos para así poder arreglar dicho equipo; además, es capaz de cambiar el estado del equipo a "En revisión", "Entregado", "Reparado", etc.

  > Usuario    =   TECGEN   
  > Contraseña =   tecgen123
  
Si te ha gustado el proyecto invitame un café
<div align="left">
  <a href="https://paypal.me/JEstevezVargas" target="_blank" style="display: inline-block;">
    <img
      src="https://img.shields.io/badge/Donate-Buy%20Me%20A%20Coffee-orange.svg?style=flat-square&logo=buymeacoffee" 
      align="center"
     />
  </a>
</div>
<br />

# Mis redes sociales

 <a href="https://www.youtube.com/channel/UCEUrVWPMTrXIWzn5CwnjYhQ" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/youtube.svg" alt="uceurvwpmtrxiwzn5cwnjyhq" height="30" width="40" /></a> 
<a href="https://instagram.com/juankestevez" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/instagram.svg" alt="juankestevez" height="30" width="40" /></a>
 <a href="https://linkedin.com/in/juan-carlos-estevez-vargas-4abb8b14a/" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="juan-carlos-estevez-vargas-4abb8b14a/" height="30" width="40" /></a> 
 <a href="https://codepen.io/juan-carlos-estevez-vargas" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/codepen.svg" alt="juan-carlos-estevez-vargas" height="30" width="40" /></a>

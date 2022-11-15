-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2022 a las 21:18:30
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `estevez_corporation`
--
CREATE DATABASE IF NOT EXISTS `estevez_corporation` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `estevez_corporation`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre_cliente` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mail_cliente` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tel_cliente` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `dir_cliente` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ultima_modificacion` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='esta tabla es para el registro de los clientes';

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre_cliente`, `mail_cliente`, `tel_cliente`, `dir_cliente`, `ultima_modificacion`) VALUES
(15, 'Marco Antonio Solis Guerra', 'marco.solis@gmail.com', '3214567721', 'Colombia', 'capgen'),
(16, 'Marina Gonzales Cabanzo', 'marina.g@gmail.com', '3214596533', 'Pereira - Colombia', 'capgen');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `id_equipo` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `tipo_equipo` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `marca` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `modelo` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `num_serie` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `dia_ingreso` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mes_ingreso` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `annio_ingreso` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `observaciones` longtext COLLATE utf8_unicode_ci NOT NULL,
  `estatus` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ultima_modificacion` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `comentarios_tecnicos` longtext COLLATE utf8_unicode_ci NOT NULL,
  `revision_tecnica_de` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla para el registro de equipos';

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`id_equipo`, `id_cliente`, `tipo_equipo`, `marca`, `modelo`, `num_serie`, `dia_ingreso`, `mes_ingreso`, `annio_ingreso`, `observaciones`, `estatus`, `ultima_modificacion`, `comentarios_tecnicos`, `revision_tecnica_de`) VALUES
(18, 15, 'Desktop', 'Samsung', 'AFZ-78F-3', '2349762927-G', '15', '11', '2022', 'El cliente informa que el equipo se calienta mucho y en ocasiones llega a oler un poco a quemado, además, comenta que se torna un poco lento.', 'En Revisión', 'tecgen', 'El equipo se encuentra en revisión en este momento, se estima su reparación para el dia 16/11/2022.', ''),
(19, 15, 'Laptop', 'HP', 'ASD-65G-D', '29725271-K', '15', '11', '2022', 'El cliente afirma problemas en la batería, no carga y la poca batería que llega a tener se termina muy rápido. Se requiere prioridad en su reparación.', 'Reparado', 'tecgen', 'Se cambio la bateria junto con el puerto de carga por componente totalmente nuevos.', ''),
(20, 16, 'Impresora', 'Brother', 'IMP-RE45-F', '98765-AMI', '15', '11', '2022', 'No detecta los cartuchos de color verde, además, el cliente menciona mucha demora a la hora de imprimir.', 'Nuevo Ingreso', 'capgen', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_nivel` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `estatus` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `registrado_por` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla registro usuarios';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `email`, `telefono`, `username`, `password`, `tipo_nivel`, `estatus`, `registrado_por`) VALUES
(1, 'Administrador General', 'juank2001estevez@gmail.com', '3152550000', 'JCEV', 'admin123', 'Administrador', 'Activo', 'JCEV'),
(43, 'Capturista General', 'capgen@gmail.com', '3154567890', 'CAPGEN', 'capgen123', 'Capturista', 'Activo', 'JCEV'),
(44, 'Tecnico Gneral', 'tecgen@gmail.com', '3457864000', 'TECGEN', 'tecgen123', 'Tecnico', 'Activo', 'JCEV');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indices de la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD PRIMARY KEY (`id_equipo`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `equipos`
--
ALTER TABLE `equipos`
  MODIFY `id_equipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

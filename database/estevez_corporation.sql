-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-05-2022 a las 20:33:43
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `data_system`
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
(1, 'Lucía', 'lucia@example.com', '323422', 'Chiquinquirá', 'marta'),
(2, 'cliente2', 'prueba@mail.com', '3217865423', 'Chiquinquirá', 'marta'),
(3, 'prueba', 'no tiene', '2', 'ayer', 'hola'),
(4, 'chao', 'si', '23', 'poh', 'marta'),
(5, 'clienteeeee', 'c@mail.com', '9875432', 'CAlle 2', 'hola'),
(6, 'ppp', 'ppp', '123', 'calle', 'hola'),
(7, 'ppp2', 'ppp2', '1234', 'calle', 'juank');

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
(1, 0, 'Laptop', 'Brother', 'onix-series', '12345678', '28', '9', '2021', 'Daño en el puerto de carga y en la tecla espaciadora', 'Reparado', 'pedro', '', ''),
(2, 2, 'Laptop', 'Acer', 'prueba', '0987', '16', '3', '2022', 'Sin observaciones.', 'Entregado', 'pedro', '', ''),
(3, 1, 'Laptop', 'Acer', 'mod', '12', '16', '3', '2022', 'viene sin pantalla xd', 'En revision', 'marta', 'perro hpta', 'pedro'),
(4, 1, 'Laptop', 'Acer', 'mod78', '78', '16', '3', '2022', '', 'Entregado', 'pedro', '', ''),
(5, 4, 'Desktop', 'Apple', 'no se', '12345', '19', '3', '2022', 'el equipo viene dañado', 'No repaired', 'pedro', '', ''),
(6, 0, 'Impresora', 'Dell', 'e34', '12h86', '21', '3', '2022', 'Esta es una prueba en clase.', 'Nuevo Ingreso', 'pedro', '', ''),
(7, 6, 'Laptop', 'Acer', '8', '2', '5', '4', '2022', 'loiuf', 'Nuevo Ingreso', 'marta', '', ''),
(8, 7, 'Laptop', 'Acer', '9', '3', '5', '4', '2022', 'oiuytd', 'Nuevo Ingreso', 'juank', '', ''),
(9, 6, 'Laptop', 'Lenovo', '8', '2', '5', '4', '2022', 'Sin observaciones.', 'Nuevo Ingreso', 'juank', '', ''),
(10, 1, 'Multifuncional', 'Asus', 'perro', 'perro1', '7', '4', '2022', 'prueba perro 1', 'Nuevo Ingreso', 'marta', '', ''),
(11, 1, 'Laptop', 'HP', 'perro2', 'perro22', '7', '4', '2022', 'prueba perro 2222', 'Nuevo Ingreso', 'marta', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_nivel` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `estatus` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `registrado_por` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla registro usuarios';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `email`, `telefono`, `username`, `password`, `tipo_nivel`, `estatus`, `registrado_por`) VALUES
(1, 'Juan Carlos Estevez Vargas', 'juank2001estevez@gmail.com', '3219113202', 'JuanK', '1234', 'Administrador', 'Activo', 'JuanK'),
(2, 'Marta Lozano', 'marta@example.com', '3214597648', 'Marta', '1234', 'Capturista', 'Activo', 'JuanK'),
(3, 'Pedro Jimenez', 'pedro@example.com', '321356', 'Pedro', '12345', 'Tecnico', 'Activo', 'JuanK'),
(4, 'Marlon', 'marica@mail.com', '3124567823', 'Mamarlon', 'megustanlaspollas', 'Capturista', 'Activo', 'xd'),
(5, 'Nicolas', 'mail@mail.com', '543725', 'nicolas', 'meGustanLasPerras', 'Capturista', 'Activo', 'xd'),
(6, 'Hice este', 'nuevomail@mail.com', '4567', 'a', 'a', 'Administrador', 'Inactivo', 'xd'),
(7, 'bb', 'b', '3', 'b', 'Perrito', 'Tecnico', 'Inactivo', 'xd'),
(8, 'amaranto', 'z', '4', 'x', 'z', 'Administrador', 'Activo', 'xd'),
(9, 'lkhfs', 'puf', '1', 'lh', 'lkjhg', 'Tecnico', 'Activo', 'xd'),
(10, 'bbbbb', 'bbbb', 'bbbbb', 'bbb', 'bbbbb', 'Capturista', 'Activo', 'xd'),
(11, 'Blanca', 'blanca@example.com', '3138208555', 'Kita', 'blanca123', 'Capturista', 'Activo', 'Hola'),
(12, 'Imelda', 'imelda@example.com', '768', 'Ime', '123455', 'Administrador', 'Activo', 'Hola'),
(13, 'Saira', 's@mail.com', '656789', 'saira', 'gatis', 'Tecnico', 'Activo', 'Hola'),
(14, 'Martina', 'mar@mail.com', '3127893456', 'martuchis', '12345', 'Capturista', 'Activo', 'juank'),
(15, 'proiuyf', 'jj@jhh.com', '123', 'iubhb', 'pass', 'Capturista', 'Activo', 'Hola'),
(16, 'diegays', 'iufdcj@gonogay.com', '12345', 'puta3', '1234', 'Tecnico', 'Activo', 'Hola'),
(17, 'Nelson Santos', 'nelson@example.com', '31095468', 'Nelson', '1235', 'Administrador', 'Activo', 'Hola'),
(18, 'ultimo', 'sd', '0', 'u', '987654', 'Administrador', 'Activo', 'Hola'),
(19, 'ultimo prueba', 'p0987yt', 'p098765', 'iuytre', 'p876r', 'Tecnico', 'Activo', 'juank'),
(20, 'pruebapenultima', 'prueba@mail.com', '3214567890', 'prueba', '1234', 'Capturista', 'Inactivo', 'juank');

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
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `equipos`
--
ALTER TABLE `equipos`
  MODIFY `id_equipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-06-2026 a las 03:31:21
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemaasistencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aprendiz`
--

CREATE TABLE `aprendiz` (
  `idAprendiz` int(11) NOT NULL,
  `documento` varchar(20) DEFAULT NULL,
  `programa` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `numeroFicha` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `aprendiz`
--

INSERT INTO `aprendiz` (`idAprendiz`, `documento`, `programa`, `telefono`, `id`, `numeroFicha`) VALUES
(1, '123456789', 'Programación de Software', '3101234567', 1, '3364343'),
(2, '987654321', 'Diseño Gráfico', '3209876543', 2, '4455667');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `estado` varchar(30) DEFAULT NULL,
  `idAprendiz` int(11) DEFAULT NULL,
  `idInstructor` int(11) DEFAULT NULL,
  `justificada` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`idAsistencia`, `fecha`, `hora`, `estado`, `idAprendiz`, `idInstructor`, `justificada`) VALUES
(1, '2026-05-14', '07:00:00', 'Presente', 1, 5, 0),
(2, '2026-05-13', '07:00:00', 'Presente', 1, 5, 0),
(3, '2026-05-12', '07:00:00', 'Falta', 1, 5, 0),
(4, '2026-05-11', '07:00:00', 'Presente', 1, 5, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clase`
--

CREATE TABLE `clase` (
  `idClase` int(11) NOT NULL,
  `idInstructor` int(11) NOT NULL,
  `numeroFicha` varchar(20) NOT NULL,
  `fecha` date NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFin` time DEFAULT NULL,
  `tema` varchar(150) DEFAULT NULL,
  `estado` enum('PROGRAMADA','EN CURSO','FINALIZADA') NOT NULL DEFAULT 'PROGRAMADA',
  `asistenciaRegistrada` tinyint(1) DEFAULT 0,
  `visible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clase_aprendiz`
--

CREATE TABLE `clase_aprendiz` (
  `idClaseAprendiz` int(11) NOT NULL,
  `idClase` int(11) NOT NULL,
  `idAprendiz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coordinador`
--

CREATE TABLE `coordinador` (
  `idCoordinador` int(11) NOT NULL,
  `area` varchar(100) DEFAULT NULL,
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `excusa`
--

CREATE TABLE `excusa` (
  `idExcusa` int(11) NOT NULL,
  `idAprendiz` int(11) NOT NULL,
  `idInstructor` int(11) NOT NULL,
  `fechaEnvio` datetime DEFAULT current_timestamp(),
  `motivo` varchar(300) NOT NULL,
  `archivo` varchar(500) DEFAULT NULL,
  `estado` enum('Pendiente','Aprobada','Rechazada') DEFAULT 'Pendiente',
  `observacion` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `excusa`
--

INSERT INTO `excusa` (`idExcusa`, `idAprendiz`, `idInstructor`, `fechaEnvio`, `motivo`, `archivo`, `estado`, `observacion`) VALUES
(1, 1, 5, '2026-06-26 16:39:07', 'Incapacidad', 'C:\\Users\\dazav\\Desktop\\sistema asistencia\\Excusas\\1782509947162_T.I 1028863730 Gabriel daza.pdf', 'Aprobada', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ficha`
--

CREATE TABLE `ficha` (
  `numeroFicha` varchar(20) NOT NULL,
  `programa` varchar(100) DEFAULT NULL,
  `jornada` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ficha`
--

INSERT INTO `ficha` (`numeroFicha`, `programa`, `jornada`) VALUES
('0', 'popo', 'Noche'),
('3364343', 'Programación de Software', 'Mañana'),
('4455667', 'Diseño Gráfico', 'Tarde');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instructor`
--

CREATE TABLE `instructor` (
  `idInstructor` int(11) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `numeroFicha` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `instructor`
--

INSERT INTO `instructor` (`idInstructor`, `especialidad`, `id`, `numeroFicha`) VALUES
(5, 'Programación', 2, '3364343'),
(6, 'ADSO', 3, '4455667'),
(7, 'ADSO', 4, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte`
--

CREATE TABLE `reporte` (
  `idReporte` int(11) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `fechaGeneracion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `identificacion` varchar(20) NOT NULL,
  `tipoDocumento` enum('CC','CE','TI','PPT') NOT NULL,
  `rol` enum('Coordinador','Instructor','Aprendiz') NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `identificacion`, `tipoDocumento`, `rol`, `password`, `nombre`) VALUES
(1, '123456', 'CC', 'Aprendiz', '1234', 'Carlos Pérez'),
(2, '654321', 'CE', 'Instructor', '321', 'María Gómez'),
(3, '121212', 'CC', 'Coordinador', '456', 'Luis Rodríguez'),
(4, '11', 'CC', 'Instructor', '123456', 'Andres');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD PRIMARY KEY (`idAprendiz`),
  ADD KEY `id` (`id`),
  ADD KEY `numeroFicha` (`numeroFicha`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`),
  ADD KEY `idAprendiz` (`idAprendiz`),
  ADD KEY `idInstructor` (`idInstructor`);

--
-- Indices de la tabla `clase`
--
ALTER TABLE `clase`
  ADD PRIMARY KEY (`idClase`),
  ADD KEY `idInstructor` (`idInstructor`);

--
-- Indices de la tabla `clase_aprendiz`
--
ALTER TABLE `clase_aprendiz`
  ADD PRIMARY KEY (`idClaseAprendiz`),
  ADD KEY `idClase` (`idClase`),
  ADD KEY `idAprendiz` (`idAprendiz`);

--
-- Indices de la tabla `coordinador`
--
ALTER TABLE `coordinador`
  ADD PRIMARY KEY (`idCoordinador`),
  ADD KEY `id` (`id`);

--
-- Indices de la tabla `excusa`
--
ALTER TABLE `excusa`
  ADD PRIMARY KEY (`idExcusa`),
  ADD KEY `idAprendiz` (`idAprendiz`),
  ADD KEY `idInstructor` (`idInstructor`);

--
-- Indices de la tabla `ficha`
--
ALTER TABLE `ficha`
  ADD PRIMARY KEY (`numeroFicha`);

--
-- Indices de la tabla `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`idInstructor`),
  ADD KEY `id` (`id`),
  ADD KEY `fk_instructor_ficha` (`numeroFicha`);

--
-- Indices de la tabla `reporte`
--
ALTER TABLE `reporte`
  ADD PRIMARY KEY (`idReporte`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  MODIFY `idAprendiz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `clase`
--
ALTER TABLE `clase`
  MODIFY `idClase` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `clase_aprendiz`
--
ALTER TABLE `clase_aprendiz`
  MODIFY `idClaseAprendiz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `coordinador`
--
ALTER TABLE `coordinador`
  MODIFY `idCoordinador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `excusa`
--
ALTER TABLE `excusa`
  MODIFY `idExcusa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `instructor`
--
ALTER TABLE `instructor`
  MODIFY `idInstructor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `reporte`
--
ALTER TABLE `reporte`
  MODIFY `idReporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD CONSTRAINT `aprendiz_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `aprendiz_ibfk_2` FOREIGN KEY (`numeroFicha`) REFERENCES `ficha` (`numeroFicha`);

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendiz` (`idAprendiz`),
  ADD CONSTRAINT `asistencia_ibfk_2` FOREIGN KEY (`idInstructor`) REFERENCES `instructor` (`idInstructor`);

--
-- Filtros para la tabla `clase`
--
ALTER TABLE `clase`
  ADD CONSTRAINT `clase_ibfk_1` FOREIGN KEY (`idInstructor`) REFERENCES `instructor` (`idInstructor`);

--
-- Filtros para la tabla `clase_aprendiz`
--
ALTER TABLE `clase_aprendiz`
  ADD CONSTRAINT `clase_aprendiz_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`) ON DELETE CASCADE,
  ADD CONSTRAINT `clase_aprendiz_ibfk_2` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendiz` (`idAprendiz`) ON DELETE CASCADE;

--
-- Filtros para la tabla `coordinador`
--
ALTER TABLE `coordinador`
  ADD CONSTRAINT `coordinador_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `excusa`
--
ALTER TABLE `excusa`
  ADD CONSTRAINT `excusa_ibfk_1` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendiz` (`idAprendiz`),
  ADD CONSTRAINT `excusa_ibfk_2` FOREIGN KEY (`idInstructor`) REFERENCES `instructor` (`idInstructor`);

--
-- Filtros para la tabla `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `fk_instructor_ficha` FOREIGN KEY (`numeroFicha`) REFERENCES `ficha` (`numeroFicha`),
  ADD CONSTRAINT `instructor_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

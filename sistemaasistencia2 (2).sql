-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 30-06-2026 a las 00:31:03
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemaasistencia2`
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
  `numeroFicha` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `aprendiz`
--

INSERT INTO `aprendiz` (`idAprendiz`, `documento`, `programa`, `telefono`, `numeroFicha`) VALUES
(1, '123456', 'Programación de Software', '3101234567', '3364343'),
(2, '987654321', 'Diseño Gráfico', '3209876543', '4455667'),
(3, '1000352146', 'Programación de Software', '3214215811', '3364343'),
(4, '111111111', 'Análisis y Desarrollo de Software', '3001111111', '4455667'),
(5, '222222222', 'Análisis y Desarrollo de Software', '3002222222', '4455667');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `idClase` int(11) NOT NULL,
  `idAprendiz` int(11) NOT NULL,
  `horaRegistro` time DEFAULT NULL,
  `fechaRegistro` date DEFAULT NULL,
  `estado` enum('ASISTIO','TARDE','FALTA','EXCUSA') DEFAULT NULL,
  `observacion` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`idAsistencia`, `idClase`, `idAprendiz`, `horaRegistro`, `fechaRegistro`, `estado`, `observacion`) VALUES
(2, 13, 5, '01:01:39', '2026-06-29', 'EXCUSA', 'Revisar en sistema'),
(3, 13, 4, '16:35:28', '2026-06-29', 'ASISTIO', '');

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

--
-- Volcado de datos para la tabla `clase`
--

INSERT INTO `clase` (`idClase`, `idInstructor`, `numeroFicha`, `fecha`, `horaInicio`, `horaFin`, `tema`, `estado`, `asistenciaRegistrada`, `visible`) VALUES
(1, 2, '3364343', '2025-02-20', '07:00:00', '07:15:00', 'Programación Orientada a Objetos', 'PROGRAMADA', 0, 1),
(2, 2, '3364343', '2025-02-20', '08:00:00', '08:15:00', 'Consultas SQL', 'PROGRAMADA', 0, 1),
(3, 1, '3423421', '2025-02-20', '09:00:00', '09:15:00', 'HTML y CSS', 'PROGRAMADA', 0, 1),
(10, 2, '3364343', '2026-07-02', '12:04:00', '14:04:00', 'FrontEnd', 'PROGRAMADA', 0, 1),
(11, 2, '3364343', '2026-07-09', '20:13:35', '20:13:35', 'Redes Y Comunicacion', 'PROGRAMADA', 0, 1),
(12, 2, '3364343', '2026-07-16', '20:20:02', '20:20:02', 'Redes', 'PROGRAMADA', 0, 1),
(13, 2, '4455667', '2026-07-31', '20:42:03', '20:42:03', 'Programacion Orientada Objetos', 'PROGRAMADA', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clase_aprendiz`
--

CREATE TABLE `clase_aprendiz` (
  `idClaseAprendiz` int(11) NOT NULL,
  `idClase` int(11) NOT NULL,
  `idAprendiz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clase_aprendiz`
--

INSERT INTO `clase_aprendiz` (`idClaseAprendiz`, `idClase`, `idAprendiz`) VALUES
(1, 10, 1),
(2, 10, 3),
(7, 13, 2),
(8, 13, 4),
(9, 13, 5);

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
  `fechaSolicitud` date DEFAULT NULL,
  `motivo` varchar(300) DEFAULT NULL,
  `estado` enum('PENDIENTE','ACEPTADA','RECHAZADA') DEFAULT NULL,
  `archivo` varchar(255) DEFAULT NULL,
  `idAprendiz` int(11) DEFAULT NULL,
  `idClase` int(11) NOT NULL,
  `idAsistencia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `excusa`
--

INSERT INTO `excusa` (`idExcusa`, `fechaSolicitud`, `motivo`, `estado`, `archivo`, `idAprendiz`, `idClase`, `idAsistencia`) VALUES
(2, '2026-06-29', 'Incapacidad médica por enfermedad respiratoria.', 'PENDIENTE', 'D:\\10 SEMESTRE\\Hojas de Vida\\Scrum', 5, 13, 3);

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
('3364343', 'Programación de Software', 'Mañana'),
('3364587', 'Redes y Telecomunicaciones', 'Noche'),
('3423421', 'Analsis de IT', 'Tarde'),
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
(1, 'Programación', 2, '3364343'),
(2, 'ADSO', 3, '4455667'),
(3, 'Tecnologias de la Informacion', 10, '3423421'),
(4, 'Programación Web', 10, '3423421');

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
(9, '1000352146', 'CC', 'Aprendiz', '123456', 'Dilan Peñaloza'),
(10, '1500024152', 'CC', 'Instructor', '123456', 'Edwin Gomez'),
(11, '111111111', 'CC', 'Aprendiz', '1234', 'Carlos Pérez'),
(12, '222222222', 'CC', 'Aprendiz', '1234', 'Laura Gómez');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD PRIMARY KEY (`idAprendiz`),
  ADD KEY `numeroFicha` (`numeroFicha`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`),
  ADD KEY `idClase` (`idClase`),
  ADD KEY `idAprendiz` (`idAprendiz`);

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
  ADD KEY `fk_excusa_clase` (`idClase`),
  ADD KEY `fk_excusa_asistencia` (`idAsistencia`);

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
  MODIFY `idAprendiz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
  MODIFY `idExcusa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `instructor`
--
ALTER TABLE `instructor`
  MODIFY `idInstructor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `reporte`
--
ALTER TABLE `reporte`
  MODIFY `idReporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aprendiz`
--
ALTER TABLE `aprendiz`
  ADD CONSTRAINT `aprendiz_ibfk_2` FOREIGN KEY (`numeroFicha`) REFERENCES `ficha` (`numeroFicha`);

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`),
  ADD CONSTRAINT `asistencia_ibfk_2` FOREIGN KEY (`idAprendiz`) REFERENCES `aprendiz` (`idAprendiz`);

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
  ADD CONSTRAINT `fk_excusa_asistencia` FOREIGN KEY (`idAsistencia`) REFERENCES `asistencia` (`idAsistencia`),
  ADD CONSTRAINT `fk_excusa_clase` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`);

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

-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Apr 2014 um 18:26
-- Server Version: 5.5.27
-- PHP-Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `clojureprojekt`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tbl_publisher`
--

CREATE TABLE IF NOT EXISTS `tbl_publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `tbl_publisher`
--

INSERT INTO `tbl_publisher` (`id`, `name`) VALUES
(2, 'Nordarchiv Verlag'),
(4, 'Grotto');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tbl_title`
--

CREATE TABLE IF NOT EXISTS `tbl_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `isbn` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `tbl_title`
--

INSERT INTO `tbl_title` (`id`, `name`, `isbn`, `author`, `publisher_id`) VALUES
(1, 'Grundlagen der Informatik', '978-3-8348-2296-3', 'Hans Gruber', 2),
(4, 'Grundlagen Informatik', '923-53445-23542-1', 'Albert Einstein', 4);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

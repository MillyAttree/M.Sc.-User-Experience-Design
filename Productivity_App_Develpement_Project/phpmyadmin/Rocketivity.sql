-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 22, 2020 at 04:05 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Rocketivity`
--

-- --------------------------------------------------------

--
-- Table structure for table `Reminder`
--

CREATE TABLE `Reminder` (
  `name` varchar(50) NOT NULL,
  `time` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `turn_on` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Reminder`
--

INSERT INTO `Reminder` (`name`, `time`, `date`, `turn_on`) VALUES
('Grandmaa Birthday', '09:00', '2021-01-21', 1),
('Bake Birthday cake', '10:30', '2021-01-14', 1),
('Cut down tree', '14:35', '2021-01-26', 0),
('Rake lawn', '15:40', '2021-01-20', 0),
('Get salt for snow', '18:00', '2020-12-30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Sign_up`
--

CREATE TABLE `Sign_up` (
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Sign_up`
--

INSERT INTO `Sign_up` (`email`, `password`) VALUES
('mes3198@thi.de', 'megan123'),
('mi.0375@thi.de', 'milly123'),
('nip5311@thi.de', 'niklas432'),
('til4301@thi.de', 'tim456');

-- --------------------------------------------------------

--
-- Table structure for table `Todo`
--

CREATE TABLE `Todo` (
  `add_task` varchar(50) NOT NULL,
  `sub_task1` varchar(50) NOT NULL,
  `sub_task2` varchar(50) NOT NULL,
  `sub_task3` varchar(50) NOT NULL,
  `sub_task4` varchar(50) NOT NULL,
  `time` varchar(10) NOT NULL,
  `date` date NOT NULL,
  `category` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Todo`
--

INSERT INTO `Todo` (`add_task`, `sub_task1`, `sub_task2`, `sub_task3`, `sub_task4`, `time`, `date`, `category`) VALUES
('Hosehold chores', 'Make bed', 'Empty Dishwasher', 'Fold laundry', '', '14:35', '2021-01-14', 'Planet'),
('Project meeting', 'AR discussion', 'Blender model design', '', '', '15:40', '2021-01-14', 'Planet'),
('Meeting with mark', '', '', '', '', '18:00', '2021-01-14', 'Star'),
('Groceries today', 'Tomatoes', 'Fruits', 'eggs and fish', 'milk and curd', '19:20', '2021-01-14', 'Star');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Sign_up`
--
ALTER TABLE `Sign_up`
  ADD UNIQUE KEY `email` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

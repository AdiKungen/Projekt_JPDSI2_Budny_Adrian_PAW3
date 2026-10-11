-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Paź 11, 2026 at 08:06 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `booker`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `book`
--

CREATE TABLE `book` (
  `idbook` int(11) NOT NULL,
  `author` varchar(30) DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `genre_idgenre` int(11) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `whenreleased` date DEFAULT NULL,
  `isavailable` char(1) DEFAULT '1',
  `cover` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`idbook`, `author`, `title`, `description`, `genre_idgenre`, `price`, `whenreleased`, `isavailable`, `cover`) VALUES
(1, 'Marek Wiśniewski', 'Cienie Zapomnianych Gór', 'Tajemnicza wyprawa w głąb mglistych pasm górskich, gdzie pradawne legendy okazują się niebezpieczną rzeczywistością.', 1, 4, '2021-04-15', '1', 'cienie_zapomnianych_gor.jpg'),
(2, 'Katarzyna Zielińska', 'Milczenie Północy', 'W małym miasteczku dochodzi do zagadkowego zniknięcia. Młoda detektyw odkrywa sieć lokalnych intryg i sekretów.', 2, 5, '2022-09-10', '1', 'milczenie_polnocy.jpg'),
(3, 'Tomasz Nowicki', 'Stacja Orbitalna Orion', 'Załoga stacji badawczej na obrzeżach układu słonecznego traci kontakt z Ziemią po odebraniu anomalnego sygnału.', 3, 6, '2023-01-20', '1', 'stacja_orbitalna_orion.jpg'),
(4, 'Anna Jabłońska', 'Jesień w Dolinie Róż', 'Poruszająca opowieść o powrotach, poszukiwaniu spokoju i odbudowywaniu zerwanych przed laty więzi rodzinnych.', 4, 3, '2020-11-05', '1', 'jesien_w_dolinie_roz.jpg'),
(5, 'Piotr Kaczmarek', 'Krypta Dawnych Mistrzów', 'Mroczny horror o starym dworze na odludziu, w którym badacze zjawisk paranormalnych budzą uśpione zło.', 5, 4, '2019-10-31', '1', 'krypta_dawnych_mistrzow.jpg'),
(6, 'Michał Dąbrowski', 'Kroniki Zaginionego Miasta', 'Archeolog trafia na ślady osady, która według oficjalnej historii nigdy nie powinna była istnieć.', 6, 5, '2021-06-18', '1', 'kroniki_zaginionego_miasta.jpg'),
(7, 'Ewa Kozłowska', 'Przełęcz Ciemności', 'Grupa wędrowców zostaje odcięta od świata przez śnieżycę w schronisku kryjącym ponurą tajemnicę.', 2, 4, '2022-12-01', '1', 'przelecz_ciemnosci.jpg'),
(8, 'Krzysztof Mazur', 'Kod Nowego Świata', 'W niedalekiej przyszłości sztuczna inteligencja przejmuje kontrolę nad kluczowymi zasobami cyfrowymi.', 3, 6, '2023-05-14', '1', 'kod_nowego_swiata.jpg');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `genre`
--

CREATE TABLE `genre` (
  `idgenre` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`idgenre`, `name`) VALUES
(1, 'Fantastyka'),
(2, 'Kryminał i thriller'),
(3, 'Science Fiction'),
(4, 'Literatura obyczajowa'),
(5, 'Horror'),
(6, 'Historia i biografia');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `permission`
--

CREATE TABLE `permission` (
  `idpermission` int(11) NOT NULL,
  `users_iduser` int(11) NOT NULL,
  `role_idrole` int(11) NOT NULL,
  `whengiven` timestamp NULL DEFAULT current_timestamp(),
  `whenrevoked` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`idpermission`, `users_iduser`, `role_idrole`, `whengiven`, `whenrevoked`) VALUES
(1, 1, 1, '2026-10-06 18:26:29', NULL),
(2, 2, 2, '2026-10-07 14:14:53', NULL),
(3, 3, 3, '2026-10-07 14:15:02', NULL),
(4, 4, 1, '2026-10-07 14:30:54', NULL),
(5, 4, 2, '2026-10-07 14:30:59', NULL),
(6, 4, 3, '2026-10-07 14:31:04', NULL),
(14, 13, 3, '2026-10-11 17:36:08', '2026-10-11 17:37:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rental`
--

CREATE TABLE `rental` (
  `idrental` int(11) NOT NULL,
  `users_iduser` int(11) NOT NULL,
  `whenrented` timestamp NULL DEFAULT NULL,
  `whenends` timestamp NULL DEFAULT NULL,
  `whenreturned` timestamp NULL DEFAULT NULL,
  `issent` char(1) DEFAULT NULL,
  `isaccepted` char(1) DEFAULT NULL,
  `isreturned` char(1) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `latecost` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentalitem`
--

CREATE TABLE `rentalitem` (
  `idrentalitem` int(11) NOT NULL,
  `rental_idrental` int(11) NOT NULL,
  `book_idbook` int(11) NOT NULL,
  `price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role`
--

CREATE TABLE `role` (
  `idrole` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `whencreated` timestamp NULL DEFAULT current_timestamp(),
  `whendisabled` timestamp NULL DEFAULT NULL,
  `isactive` char(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`idrole`, `name`, `whencreated`, `whendisabled`, `isactive`) VALUES
(1, 'admin', '2026-10-06 18:26:19', NULL, '1'),
(2, 'worker', '2026-10-07 14:13:44', NULL, '1'),
(3, 'user', '2026-10-07 14:13:52', NULL, '1');

--
-- Wyzwalacze `role`
--
DELIMITER $$
CREATE TRIGGER `disabledTrigger` BEFORE UPDATE ON `role` FOR EACH ROW BEGIN
	IF(NEW.isactive = 0) THEN
    SET NEW.whendisabled = CURRENT_TIMESTAMP;
    ELSE
    SET NEW.whendisabled = null;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `iduser` int(11) NOT NULL,
  `login` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `whocreated` int(11) DEFAULT NULL,
  `whencreated` timestamp NULL DEFAULT current_timestamp(),
  `wholastmodified` int(11) DEFAULT NULL,
  `whenlastmodified` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`iduser`, `login`, `password`, `whocreated`, `whencreated`, `wholastmodified`, `whenlastmodified`) VALUES
(1, 'admin', 'admin123', NULL, '2026-10-02 17:26:54', 4, '2026-10-11 17:39:15'),
(2, 'worker', 'worker123', 1, '2026-10-07 14:14:07', 4, '2026-10-11 17:39:26'),
(3, 'user', 'user123', 1, '2026-10-07 14:14:19', 4, '2026-10-11 17:39:34'),
(4, 'all', 'all123', NULL, '2026-10-07 14:30:44', NULL, '2026-10-07 14:30:44'),
(13, 'inactive', 'inactive123', NULL, '2026-10-11 17:36:08', 4, '2026-10-11 17:37:00');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`idbook`),
  ADD KEY `cons1` (`genre_idgenre`);

--
-- Indeksy dla tabeli `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`idgenre`);

--
-- Indeksy dla tabeli `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`idpermission`),
  ADD KEY `cons2` (`role_idrole`),
  ADD KEY `cons3` (`users_iduser`);

--
-- Indeksy dla tabeli `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`idrental`),
  ADD KEY `cons4` (`users_iduser`);

--
-- Indeksy dla tabeli `rentalitem`
--
ALTER TABLE `rentalitem`
  ADD PRIMARY KEY (`idrentalitem`),
  ADD KEY `cons5` (`book_idbook`),
  ADD KEY `cons6` (`rental_idrental`);

--
-- Indeksy dla tabeli `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`idrole`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`iduser`),
  ADD KEY `cons7` (`whocreated`),
  ADD KEY `cons8` (`wholastmodified`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `idbook` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `idgenre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `idpermission` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `rental`
--
ALTER TABLE `rental`
  MODIFY `idrental` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `rentalitem`
--
ALTER TABLE `rentalitem`
  MODIFY `idrentalitem` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `idrole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `iduser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `cons1` FOREIGN KEY (`genre_idgenre`) REFERENCES `genre` (`idgenre`);

--
-- Constraints for table `permission`
--
ALTER TABLE `permission`
  ADD CONSTRAINT `cons2` FOREIGN KEY (`role_idrole`) REFERENCES `role` (`idrole`),
  ADD CONSTRAINT `cons3` FOREIGN KEY (`users_iduser`) REFERENCES `users` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `cons4` FOREIGN KEY (`users_iduser`) REFERENCES `users` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rentalitem`
--
ALTER TABLE `rentalitem`
  ADD CONSTRAINT `cons5` FOREIGN KEY (`book_idbook`) REFERENCES `book` (`idbook`),
  ADD CONSTRAINT `cons6` FOREIGN KEY (`rental_idrental`) REFERENCES `rental` (`idrental`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `cons7` FOREIGN KEY (`whocreated`) REFERENCES `users` (`iduser`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `cons8` FOREIGN KEY (`wholastmodified`) REFERENCES `users` (`iduser`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

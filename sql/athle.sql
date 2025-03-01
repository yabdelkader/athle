SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES latin1 */;

CREATE TABLE IF NOT EXISTS licence (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPersonne` int(11) NOT NULL,
  `idClub` int(11) NOT NULL,
  `annee` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) NOT NULL,
  `idLigue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=110 ;

CREATE TABLE IF NOT EXISTS `competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) NOT NULL,
  `lieu` varchar(56) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `cotation` (
  `sexe` int(1) NOT NULL,
  `idEpreuve` int(11) NOT NULL,
  `perf` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

CREATE TABLE IF NOT EXISTS `epreuve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) NOT NULL,
  `idClub` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1501 ;

CREATE TABLE IF NOT EXISTS `ligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rang` int(11) DEFAULT '0',
  `perf` varchar(12) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `sexe` int(1) NOT NULL DEFAULT '0',
  `naissance` int(11) NOT NULL,
  `club` varchar(30) DEFAULT NULL,
  `ligue` varchar(10) DEFAULT NULL,
  `idPays` int(11) NOT NULL,
  `ville` varchar(65) DEFAULT NULL,
  `datePerf` varchar(10) NOT NULL,
  `idEpreuve` int(11) NOT NULL,
  `annee` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nom` (`nom`),
  KEY `idEpreuve` (`idEpreuve`),
  KEY `annee` (`annee`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=71253 ;

CREATE TABLE IF NOT EXISTS `ligue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `pays` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=5 ;

CREATE TABLE IF NOT EXISTS `perf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idPersonne` int(11) NOT NULL,
  `valeur` varchar(65) NOT NULL,
  `rang` int(11) NOT NULL,
  `idEpreuve` int(11) NOT NULL,
  `point` int(11) DEFAULT NULL,
  `idCompetition` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idPersonne` (`idPersonne`),
  KEY `idEpreuve` (`idEpreuve`),
  KEY `idCompetition` (`idCompetition`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=201 ;

CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(65) NOT NULL,
  `sexe` varchar(1) NOT NULL,
  `dateNaissance` int(11) DEFAULT NULL,
  `idClub` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idClub` (`idClub`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=203 ;


ALTER TABLE `perf`
  ADD CONSTRAINT `perf_ibfk_1` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`id`),
  ADD CONSTRAINT `perf_ibfk_2` FOREIGN KEY (`idEpreuve`) REFERENCES `epreuve` (`id`),
  ADD CONSTRAINT `perf_ibfk_3` FOREIGN KEY (`idCompetition`) REFERENCES `competition` (`id`);

ALTER TABLE `personne`
  ADD CONSTRAINT `personne_ibfk_1` FOREIGN KEY (`idClub`) REFERENCES `club` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


CREATE TABLE IF NOT EXISTS cros (
	id			int(11)		NOT NULL AUTO_INCREMENT,
	rang		int(4)		NOT NULL,
	chrono		varchar(5)	NOT NULL,	
	nom			varchar(32)	NOT NULL,
	club		varchar(32)	NOT NULL,
	dep			char(3)	NOT NULL,	
	sexe		char(1)		NOT NULL,
	cat			varchar(10)	NOT NULL,
	naissance	int(10)		NOT NULL,	
	championnat	varchar(4)	NOT NULL,
	annee		int(4)		NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;



CREATE TABLE `base_athle`.`log` (
`id` INT NOT NULL ,
`utilisateur` VARCHAR( 32 ) NOT NULL ,
`departement` VARCHAR( 32 ) NOT NULL ,
`temps` DATETIME NOT NULL ,
PRIMARY KEY ( `id` )
) ENGINE = MYISAM ;
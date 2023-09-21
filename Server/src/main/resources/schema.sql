-- PUBLIC.SPIEL definition

-- Drop table

-- DROP TABLE PUBLIC.SPIEL;

CREATE TABLE IF NOT EXISTS PUBLIC.SPIEL (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	DATUM DATE,
	TEAMS VARCHAR_IGNORECASE,
	TORET1 INTEGER,
	TORET2 INTEGER,
	QUALIFIKATION INTEGER,
	SPIELE VARCHAR_IGNORECASE,
	TurnierID INTEGER,
	GEWINNERID INTEGER
);

-- PUBLIC.SPIELER definition

-- Drop table

-- DROP TABLE PUBLIC.SPIELER;

CREATE TABLE IF NOT EXISTS PUBLIC.SPIELER (
	NAME VARCHAR_IGNORECASE,
	ID INTEGER NOT NULL AUTO_INCREMENT
);

-- PUBLIC.TEAM definition

-- Drop table

-- DROP TABLE PUBLIC.TEAM;

CREATE TABLE IF NOT EXISTS PUBLIC.TEAM (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR_IGNORECASE,
	SPIELER VARCHAR_IGNORECASE,
	GESAMTTORE INTEGER,
	GEGENTORE INTEGER,
	AUFGEGEBEN BOOLEAN
);

-- PUBLIC.TURNIER definition

-- Drop table

-- DROP TABLE PUBLIC.TURNIER;

CREATE TABLE IF NOT EXISTS PUBLIC.TURNIER (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	STARTDATUM VARCHAR_IGNORECASE,
	ENDDATUM VARCHAR_IGNORECASE,
	SPIELE VARCHAR_IGNORECASE,
    GESPIELT BOOLEAN,
	TEAMS VARCHAR_IGNORECASE,
	Turniername VARCHAR_IGNORECASE
);
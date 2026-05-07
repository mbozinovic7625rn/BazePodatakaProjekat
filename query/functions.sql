-- Funkcija koja vraća broj aktivnih alata odredjenog tipa u laboratoriji. Aktivni alati su oni koji su trenutno u sesiji (nalaze se u tabeli sesija_alat).
-- Parametri funkcije su: id laboratorije i naziv tipa alata.
USE `laboratorija_db`;
DROP function IF EXISTS `f_broj_aktivnih_alata_u_laboratoriji`;

DELIMITER $$
USE `laboratorija_db`$$
CREATE FUNCTION `f_broj_aktivnih_alata_u_laboratoriji` (f_id_lab INT, f_naziv VARCHAR(100))
RETURNS INTEGER
READS SQL DATA
BEGIN
	DECLARE broj_aktivnih_alata INT DEFAULT 0;
    
    SELECT COUNT(DISTINCT a.id_alat) INTO broj_aktivnih_alata
    FROM alat AS a
    JOIN tip_alata AS ta ON (a.id_tip_alata = ta.id_tip_alata)
    JOIN sesija_alat AS sa ON (sa.id_alat = a.id_alat)
    WHERE a.id_lab = f_id_lab AND ta.naziv = f_naziv;
    
	RETURN broj_aktivnih_alata;
END$$

DELIMITER;

-- Test funkcija za ovo bi bila:
USE `laboratorija_db`;
DROP function IF EXISTS `f_test_broj_aktvinih_alata`;

DELIMITER $$
USE `laboratorija_db`$$
CREATE FUNCTION `f_test_broj_aktvinih_alata` ()
RETURNS BOOLEAN
READS SQL DATA
BEGIN
	DECLARE v_test1 BOOLEAN;
    DECLARE v_test2 BOOLEAN;
    DECLARE v_test3 BOOLEAN;
    DECLARE v_test4 BOOLEAN;
    DECLARE v_test5 BOOLEAN;
    DECLARE v_all BOOLEAN;
	
    -- Test 1: kombinacija sa vise aktivnih alata (lab 6, ultracentrifuga = 3)
	SET v_test1 = (f_broj_aktivnih_alata_u_laboratoriji(6, 'Centrifuga ultracentrifuga') = 3);
	-- Test 2: kombinacija sa tacno 1 aktivnim alatom (lab 8, Citometar = 1)
    SET v_test2 = (f_broj_aktivnih_alata_u_laboratoriji(8, 'Citometar') = 1);
    -- Test 3: srednji broj alata (lab 6, Vorteks meshalica = 2)
    SET v_test3 = (f_broj_aktivnih_alata_u_laboratoriji(6, 'Vorteks meshalica') = 2);
    -- Test 4: nepostojeca laboratorija - mora vratiti 0
    SET v_test4 = (f_broj_aktivnih_alata_u_laboratoriji(999, 'Mikroskop opticki') = 0);
    -- Test 5: nepostojeci tip alata - mora vratiti 0
    SET v_test5 = (f_broj_aktivnih_alata_u_laboratoriji(1, 'NepostojeciTipAlata') = 0);
    -- Kombinujemo sve testove - svi moraju biti tacni da bi funkcija vratila TRUE
    SET v_all = v_test1 AND v_test2 AND v_test3 AND v_test4 AND v_test5;
    
    RETURN v_all;
END$$
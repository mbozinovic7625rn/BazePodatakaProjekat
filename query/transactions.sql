-- Procedura koja dobija dva parametra: id_izvodjenja i trajanje sesije u minutima.
-- Deklarise varijable koje ce se koristiti u proceduri i postavlja handler za SQLEXCEPTION koji ce izvrsiti ROLLBACK i proslediti gresku dalje.
-- Provarava nekoliko uslova: 
-- 1) Da li postoji eksperiment za dato izvodjenje?
-- 2) Da li je izvodjenje u statusu "Zavrseno" ili "Otkazano"?
-- 3) Da li ima dovoljno resursa u laboratoriji za pokretanje sesije
-- Ako je sve to u redu idemo dalje na sledeci korak:
-- 1) Racunamo vreme pocetka i zavrsetka sesije
-- 2) Pokrecemo transakciju
-- 3) Ubacujemo novi red u tabelu sesija sa datumom, vremenom pocetka, vremenom zavrsetka i id-jem izvodjenja
-- 4) Cuvamo id novog reda u sesija tabeli u promenljivu v_id_sesija
-- 5) Azuriramo kolicinu resursa u inventar_resursa tabeli tako sto oduzimamo potrebnu kolicinu resursa za eksperiment od trenutne kolicine resursa u laboratoriji
-- 6) Ubacujemo redove u sesija_resurs tabelu za svaki resurs koji je potreban za eksperiment, sa id-jem sesije, id-jem resursa i iskoriscenom kolicinom resursa
-- 7) Potvrdujemo transakciju
-- Na kraju procedura vraca id sesije, id eksperimenta, id laboratorije, vreme pocetka i vreme zavrsetka sesije.
USE `laboratorija_db`;
DROP procedure IF EXISTS `p_zapocni_sesiju`;

USE `laboratorija_db`;
DROP procedure IF EXISTS `laboratorija_db`.`p_zapocni_sesiju`;
;

DELIMITER $$
USE `laboratorija_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_zapocni_sesiju`(
	IN p_id_izvodjenje INT,
	IN p_trajanje_min INT
)
BEGIN

DECLARE v_id_eksperiment INT;
DECLARE v_id_lab INT;
DECLARE v_status_izvodjenja VARCHAR(50);
DECLARE v_id_sesija INT;
DECLARE v_vreme_pocetka TIME;
DECLARE v_vreme_zavrsetka TIME;
DECLARE v_nedostupan_resurs INT DEFAULT 0;

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
	ROLLBACK; -- rollback, vracanje na prethodno stanje
	RESIGNAL; -- prosledjivanje greske koju dobijemo
END;
    
SELECT id_eksperiment, id_lab, status
INTO v_id_eksperiment, v_id_lab, v_status_izvodjenja
FROM izvodjenje
WHERE id_izvodjenje = p_id_izvodjenje;

IF v_id_eksperiment IS NULL THEN
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Izvodjenje sa datim ID-jem ne postoji.';
END IF;

IF v_status_izvodjenja IN ('Zavrseno', 'Otkazano') THEN
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ovo izvodjenje je zavrseno ili otkazano.';
END IF;

SELECT COUNT(*) INTO v_nedostupan_resurs
FROM eksperiment_resurs AS er
LEFT JOIN inventar_resursa AS ir ON (er.id_resurs = ir.id_resurs AND ir.id_lab = v_id_lab)
WHERE id_eksperiment = v_id_eksperiment AND (ir.kolicina IS NULL OR ir.kolicina < er.potrebna_kolicina);

IF v_nedostupan_resurs > 0 THEN
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Nemate dovoljno resursa za pokretanje ove sesije.";
END IF;

SET v_vreme_pocetka = CURTIME();
SET v_vreme_zavrsetka = ADDTIME(v_vreme_pocetka, SEC_TO_TIME(p_trajanje_min * 60));

START TRANSACTION;

INSERT INTO sesija (datum, vreme_pocetka, vreme_zavrsetka, id_izvodjenje)
VALUES (CURDATE(), v_vreme_pocetka, v_vreme_zavrsetka, p_id_izvodjenje);

SET v_id_sesija = LAST_INSERT_ID();

UPDATE inventar_resursa AS ir
JOIN eksperiment_resurs AS er ON (ir.id_resurs = er.id_resurs)
SET ir.kolicina = ir.kolicina - er.potrebna_kolicina
WHERE er.id_eksperiment = v_id_eksperiment AND ir.id_lab = v_id_lab;

INSERT INTO sesija_resurs (id_sesija, id_resurs, iskoriscena_kolicina)
SELECT v_id_sesija, er.id_resurs, er.potrebna_kolicina
FROM eksperiment_resurs AS er
WHERE er.id_eksperiment = v_id_eksperiment;

COMMIT;

SELECT v_id_sesija AS id_sesija,
v_id_eksperiment AS id_eksperiment,
v_id_lab AS id_laboratorija,
v_vreme_pocetka AS vreme_pocetka,
v_vreme_zavrsetka AS vreme_zavrsetka;

END$$

DELIMITER;

-- Testiranje ove procedure:
-- CALL p_zapocni_sesiju(4, 60), p_zapocni_sesiju(29, 90), p_zapocni_sesiju(34, 60), p_zapocni_sesiju(37, 90)
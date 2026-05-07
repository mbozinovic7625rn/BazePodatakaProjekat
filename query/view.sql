-- Pogled koji prikazuje laboratorije koje su završile više od 5 eksperimenata, sortiran po broju završenih eksperimenata i ID-u laboratorije --
CREATE VIEW `v_aktvine_laboratorije` AS
SELECT l.id_lab, l.naziv, l.BSL_nivo, COUNT(e.id_eksperiment) AS broj_zavrsenih_eksperimenata
FROM laboratorija AS l
JOIN izvodjenje AS i ON i.id_lab = l.id_lab
JOIN eksperiment AS e ON e.id_eksperiment = i.id_eksperiment
WHERE i.status = 'Zavrseno'
GROUP BY l.id_lab
HAVING COUNT(e.id_eksperiment) > 5
ORDER BY broj_zavrsenih_eksperimenata, l.id_lab

-- Pogled koji prikazuje resurse koji su iskorišćeni više od 200 puta, zajedno sa ukupnom iskorišćenom količinom i ukupnom količinom na stanju, sortiran po ukupnoj iskorišćenosti --
CREATE VIEW `v_iskoriscenost_resursa` AS
SELECT r.id_resurs, r.naziv, r.dobavljac, r.barkod,
SUM(sr.iskoriscena_kolicina) AS ukupno_iskoriscena,
(SELECT SUM(ir.kolicina) FROM inventar_resursa ir WHERE ir.id_resurs = r.id_resurs) AS ukupno_na_stanju
FROM resurs AS r
JOIN sesija_resurs AS sr ON (r.id_resurs = sr.id_resurs)
JOIN sesija AS s ON (sr.id_sesija = s.id_sesija)
JOIN izvodjenje AS i ON (s.id_izvodjenje = i.id_izvodjenje)
GROUP BY r.id_resurs, r.naziv, r.dobavljac, r.barkod
HAVING SUM(sr.iskoriscena_kolicina) > 200
ORDER BY ukupno_iskoriscena ASC
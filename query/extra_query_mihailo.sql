-- Upit koji gleda koliko puta su dizajner i izvodjac saradjivali prilikom izvodjenja eksperimenata.
-- Motivacija za ovaj upit je bila to sto bi na ovaj nacin mogli da pravimo "idealne" timove za izvodjenje eksperimenata.
-- Odavde lako zakljucujemo ko sa kim najvise "voli" da saradjuje i ko sa kim ima najvise zajednickih eksperimenata.
-- Ovaj upit je dosta zahtevan, jer se spajaju mnoge tabele, ali mislim da je vredan truda jer daje vrlo korisne informacije o saradnji izmedju dizajnera i izvodjaca.

SELECT 
CONCAT(i1.ime, ' ', i1.prezime) AS dizajner, 
i1.oblast_specijalizacije AS oblast_dizajnera,
CONCAT(i2.ime, ' ', i2.prezime) AS izvodjac,
i2.oblast_specijalizacije AS oblast_izvodjaca, 
COUNT(DISTINCT e.id_eksperiment) AS broj_saradnji
FROM dizajner AS d
JOIN istrazivac AS i1 ON (d.id_istrazivac = i1.id_istrazivac)
JOIN eksperiment_dizajner AS ed ON (i1.id_istrazivac = ed.id_istrazivac)
JOIN eksperiment AS e ON (ed.id_eksperiment = e.id_eksperiment)
JOIN izvodjenje AS i ON (e.id_eksperiment = i.id_eksperiment)
JOIN izvodjenje_izvodjac AS ii ON (i.id_izvodjenje = ii.id_izvodjenje)
JOIN izvodjac AS iz ON (ii.id_istrazivac = iz.id_istrazivac)
JOIN istrazivac AS i2 ON (iz.id_istrazivac = i2.id_istrazivac)
WHERE i1.id_istrazivac <> i2.id_istrazivac -- da ne bi gledali saradnju sa samim sobom
GROUP BY i1.id_istrazivac, i2.id_istrazivac
HAVING COUNT(DISTINCT e.id_eksperiment) >= 2
ORDER BY broj_saradnji DESC;

-- KANDIDAT: Milica Samardžić
-- SAMOSTALNI UPIT: Skor angažovanosti istraživača (HR KPI Scoring)

-- Opis: Računa kompozitni skor produktivnosti za svakog istraživača na osnovu
-- formule: (broj eksperimenata kao dizajner * 2) + 
--          (broj izvođenja kao izvođač * 1) + 
--          (broj teorijskih okvira * 3)
-- Sortiranje je opadajuće prema skor-u, tako da prikazuje rang listu
-- najproduktivnijih zaposlenih u laboratorijskom sistemu.


USE laboratorija_db;

SELECT 
    i.id_istrazivac,
    i.ime,
    i.prezime,
    i.naucno_zvanje,
    
    -- 1. Broj eksperimenata gde je istraživač dizajner
    (SELECT COUNT(*) 
     FROM EKSPERIMENT_DIZAJNER ed 
     WHERE ed.id_istrazivac = i.id_istrazivac) AS broj_eksperimenata_dizajner,
     
    -- 2. Broj izvođenja gde je istraživač izvođač
    (SELECT COUNT(*) 
     FROM IZVODJENJE_IZVODJAC ii 
     WHERE ii.id_istrazivac = i.id_istrazivac) AS broj_izvodjenja_izvodjac,
     
    -- 3. Broj teorijskih okvira koje istraživač pokriva
    (SELECT COUNT(*) 
     FROM DIZAJNER_TEORIJA dt 
     WHERE dt.id_istrazivac = i.id_istrazivac) AS broj_teorijskih_okvira,
     
    -- 4. Izračunavanje kompozitnog skora prema zadatoj formuli
    (
        ((SELECT COUNT(*) FROM EKSPERIMENT_DIZAJNER ed WHERE ed.id_istrazivac = i.id_istrazivac) * 2) +
        ((SELECT COUNT(*) FROM IZVODJENJE_IZVODJAC ii WHERE ii.id_istrazivac = i.id_istrazivac) * 1) +
        ((SELECT COUNT(*) FROM DIZAJNER_TEORIJA dt WHERE dt.id_istrazivac = i.id_istrazivac) * 3)
    ) AS ukupni_kpi_skor

FROM 
    ISTRAZIVAC i
ORDER BY 
    ukupni_kpi_skor DESC, 
    i.prezime ASC;
package model;

public class Laboratorija {

    private final int idLab;
    private final String naziv;
    private final String opisLokacije;
    private final Integer bslNivo;
    private final Integer brojKomora;
    private final Integer brojInkubatora;

    public Laboratorija(int idLab, String naziv, String opisLokacije,
                        Integer bslNivo, Integer brojKomora, Integer brojInkubatora) {
        this.idLab = idLab;
        this.naziv = naziv;
        this.opisLokacije = opisLokacije;
        this.bslNivo = bslNivo;
        this.brojKomora = brojKomora;
        this.brojInkubatora = brojInkubatora;
    }

    public int getIdLab() { return idLab; }
    public String getNaziv() { return naziv; }
    public String getOpisLokacije() { return opisLokacije; }
    public Integer getBslNivo() { return bslNivo; }
    public Integer getBrojKomora() { return brojKomora; }
    public Integer getBrojInkubatora() { return brojInkubatora; }

    @Override
    public String toString() {
        return "[" + idLab + "] " + naziv;
    }
}

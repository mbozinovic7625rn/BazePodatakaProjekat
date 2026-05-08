package model;

import java.time.LocalDate;

public class Istrazivac {

    private final int idIstrazivac;
    private final String ime;
    private final String prezime;
    private final String kontakt;
    private final LocalDate datumZaposlenja;
    private final String naucnoZvanje;
    private final String oblastSpecijalizacije;
    private final Boolean bslSertifikat;

    public Istrazivac(int idIstrazivac, String ime, String prezime, String kontakt,
                      LocalDate datumZaposlenja, String naucnoZvanje,
                      String oblastSpecijalizacije, Boolean bslSertifikat) {
        this.idIstrazivac = idIstrazivac;
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.datumZaposlenja = datumZaposlenja;
        this.naucnoZvanje = naucnoZvanje;
        this.oblastSpecijalizacije = oblastSpecijalizacije;
        this.bslSertifikat = bslSertifikat;
    }

    public int getIdIstrazivac() { return idIstrazivac; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }
    public String getKontakt() { return kontakt; }
    public LocalDate getDatumZaposlenja() { return datumZaposlenja; }
    public String getNaucnoZvanje() { return naucnoZvanje; }
    public String getOblastSpecijalizacije() { return oblastSpecijalizacije; }
    public Boolean getBslSertifikat() { return bslSertifikat; }
}

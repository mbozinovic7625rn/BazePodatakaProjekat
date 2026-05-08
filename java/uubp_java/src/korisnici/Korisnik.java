package korisnici;

public class Korisnik {
    private final String korisnickoIme;
    private final String lozinka;

    public Korisnik(String korisnickoIme, String lozinka) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() { return korisnickoIme; }
    public String getLozinka() { return lozinka; }
}

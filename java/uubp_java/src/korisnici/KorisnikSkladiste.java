package korisnici;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class KorisnikSkladiste {

    private static final Path FAJL = Paths.get("korisnici.txt");
    private static final String SEP = ":";

    private synchronized List<Korisnik> ucitaj() throws IOException {
        List<Korisnik> lista = new ArrayList<>();
        if (!Files.exists(FAJL)) {
            return lista;
        }
        for (String linija : Files.readAllLines(FAJL, StandardCharsets.UTF_8)) {
            if (linija.isBlank()) continue;
            int idx = linija.indexOf(SEP);
            if (idx <= 0 || idx == linija.length() - 1) continue;
            String u = linija.substring(0, idx);
            String p = linija.substring(idx + 1);
            lista.add(new Korisnik(u, p));
        }
        return lista;
    }

    private synchronized void sacuvaj(List<Korisnik> korisnici) throws IOException {
        List<String> linije = new ArrayList<>();
        for (Korisnik k : korisnici) {
            linije.add(k.getKorisnickoIme() + SEP + k.getLozinka());
        }
        Files.write(FAJL, linije, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void validirajPolja(String korisnickoIme, String lozinka) {
        if (korisnickoIme == null || korisnickoIme.isBlank()) {
            throw new IllegalArgumentException("Korisničko ime ne sme biti prazno.");
        }
        if (lozinka == null || lozinka.isEmpty()) {
            throw new IllegalArgumentException("Lozinka ne sme biti prazna.");
        }
        if (korisnickoIme.contains(SEP) || korisnickoIme.contains("\n") || korisnickoIme.contains("\r")) {
            throw new IllegalArgumentException("Korisničko ime sadrži nedozvoljen karakter (':' ili novi red).");
        }
        if (lozinka.contains("\n") || lozinka.contains("\r")) {
            throw new IllegalArgumentException("Lozinka sadrži nedozvoljen karakter (novi red).");
        }
    }

    public synchronized boolean postoji(String korisnickoIme) throws IOException {
        for (Korisnik k : ucitaj()) {
            if (k.getKorisnickoIme().equals(korisnickoIme)) return true;
        }
        return false;
    }

    public synchronized boolean uloguj(String korisnickoIme, String lozinka) throws IOException {
        for (Korisnik k : ucitaj()) {
            if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void registruj(String korisnickoIme, String lozinka) throws IOException {
        validirajPolja(korisnickoIme, lozinka);
        List<Korisnik> lista = ucitaj();
        for (Korisnik k : lista) {
            if (k.getKorisnickoIme().equals(korisnickoIme)) {
                throw new IllegalStateException("Korisnik sa tim korisničkim imenom već postoji.");
            }
        }
        lista.add(new Korisnik(korisnickoIme, lozinka));
        sacuvaj(lista);
    }

    public synchronized void azuriraj(String staroIme, String novoIme, String novaLozinka) throws IOException {
        validirajPolja(novoIme, novaLozinka);
        List<Korisnik> lista = ucitaj();
        if (!staroIme.equals(novoIme)) {
            for (Korisnik k : lista) {
                if (k.getKorisnickoIme().equals(novoIme)) {
                    throw new IllegalStateException("Korisničko ime je već zauzeto.");
                }
            }
        }
        boolean nadjen = false;
        List<Korisnik> nova = new ArrayList<>();
        for (Korisnik k : lista) {
            if (k.getKorisnickoIme().equals(staroIme)) {
                nova.add(new Korisnik(novoIme, novaLozinka));
                nadjen = true;
            } else {
                nova.add(k);
            }
        }
        if (!nadjen) {
            throw new IllegalStateException("Korisnik nije pronađen.");
        }
        sacuvaj(nova);
    }

    public synchronized boolean obrisi(String korisnickoIme, String lozinka) throws IOException {
        List<Korisnik> lista = ucitaj();
        boolean obrisan = false;
        List<Korisnik> nova = new ArrayList<>();
        for (Korisnik k : lista) {
            if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
                obrisan = true;
                continue;
            }
            nova.add(k);
        }
        if (obrisan) {
            sacuvaj(nova);
        }
        return obrisan;
    }
}

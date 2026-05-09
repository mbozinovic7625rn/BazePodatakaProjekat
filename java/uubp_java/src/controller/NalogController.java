package controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class NalogController {
    private static final String fileName = "korisnici.txt";

    public static boolean azuriraj(
            String staroKorisnickoIme,
            String staraLozinka,
            String novoKorisnickoIme,
            String novaLozinka,
            String potvrdaNoveLozinke
    ) {
        try {
            File file = new File(fileName);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            Map<String, String> users = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                users.put(split[0], split[1]);
            }

            if (!users.containsKey(staroKorisnickoIme)) {
                return false;
            }

            if (!users.get(staroKorisnickoIme).equals(staraLozinka)) {
                return false;
            }

            if (!novaLozinka.equals(potvrdaNoveLozinke)) {
                return false;
            }

            users.remove(staroKorisnickoIme);

            users.put(novoKorisnickoIme, novaLozinka);

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (Map.Entry<String, String> user : users.entrySet()) {
                writer.write(user.getKey() + ":" + user.getValue());
                writer.newLine();
            }

            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean obrisi(String korisnickoIme, String lozinka) {
        try {
            File file = new File(fileName);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            Map<String, String> users = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                users.put(split[0], split[1]);
            }

            reader.close();

            if (!users.containsKey(korisnickoIme)) {
                return false;
            }

            if (!users.get(korisnickoIme).equals(lozinka)) {
                return false;
            }

            users.remove(korisnickoIme);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }

            writer.close();

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NalogController() {}
}

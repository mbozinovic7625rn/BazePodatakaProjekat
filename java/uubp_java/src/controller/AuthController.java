package controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private static final String fileName = "korisnici.txt";

    public static boolean prijava(String korisnickoIme, String lozinka) {
        try {
            File file = new File(fileName);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            Map<String, String> users = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                users.put(split[0], split[1]);
            }

            if (users.containsKey(korisnickoIme)) {
                if (users.get(korisnickoIme).equals(lozinka)) {
                    return true;
                }
            }

            reader.close();

            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean registracija(String korisnickoIme, String lozinka, String potvrdaLozinke) {
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

            if (users.containsKey(korisnickoIme)) {
                return false;
            }

            if (!lozinka.equals(potvrdaLozinke)) {
                return false;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (Map.Entry<String, String> user : users.entrySet()) {
                writer.write(user.getKey() + ":" + user.getValue());
                writer.newLine();
            }

            writer.write(korisnickoIme + ":" + lozinka);
            writer.newLine();

            writer.close();

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthController() {}
}

package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IstrazivacDTO {
    public static List<IstrazivacDTO> selectAllByLab(int id_lab, Connection connection) {
        String query = "SELECT id_istrazivac, ime, prezime, kontakt, naucno_zvanje, oblast_specijalizacije FROM istrazivac WHERE id_lab = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_lab);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<IstrazivacDTO> istrazivaci = new ArrayList<>();

                while (resultSet.next()) {
                    int id  = resultSet.getInt("id_istrazivac");
                    String ime = resultSet.getString("ime");
                    String prezime = resultSet.getString("prezime");
                    String kontakt = resultSet.getString("kontakt");
                    String naucnoZvanje =  resultSet.getString("naucno_zvanje");
                    String oblastSpecijalizacije  =  resultSet.getString("oblast_specijalizacije");

                    IstrazivacDTO istrazivacDTO = new IstrazivacDTO(id, ime, prezime, kontakt,  naucnoZvanje, oblastSpecijalizacije);

                    istrazivaci.add(istrazivacDTO);
                }

                return istrazivaci;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static IstrazivacDTO selectById(int id_istrazivac, Connection connection) {
        String query = "SELECT ime, prezime, kontakt, naucno_zvanje, oblast_specijalizacije FROM istrazivac WHERE id_istrazivac = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_istrazivac);
            try (ResultSet resultSet = statement.executeQuery()) {
                IstrazivacDTO istrazivacDTO = null;

                if (resultSet.next()) {
                    String ime = resultSet.getString("ime");
                    String prezime = resultSet.getString("prezime");
                    String kontakt = resultSet.getString("kontakt");
                    String naucnoZvanje = resultSet.getString("naucno_zvanje");
                    String oblastSpecijalizacije = resultSet.getString("oblast_specijalizacije");

                    istrazivacDTO = new IstrazivacDTO(id_istrazivac, ime, prezime, kontakt, naucnoZvanje, oblastSpecijalizacije);
                }

                return istrazivacDTO;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int id_istrazivac;
    private final String ime;
    private final String prezime;
    private final String kontakt;
    private final String naucnoZvanje;
    private final String oblastSpecijalizacije;

    public IstrazivacDTO(int id_istrazivac, String ime, String prezime, String kontakt, String naucnoZvanje,  String oblastSpecijalizacije) {
        this.id_istrazivac = id_istrazivac;
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.naucnoZvanje = naucnoZvanje;
        this.oblastSpecijalizacije = oblastSpecijalizacije;
    }

    public int getId_istrazivac() {
        return id_istrazivac;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getNaucnoZvanje() {
        return naucnoZvanje;
    }

    public String getOblastSpecijalizacije() {
        return oblastSpecijalizacije;
    }
}
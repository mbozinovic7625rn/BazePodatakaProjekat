package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorijaDTO {
    public static List<LaboratorijaDTO> selectAll(Connection connection) {
        String query = "SELECT id_lab, naziv, opis_lokacije, BSL_nivo FROM laboratorija";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<LaboratorijaDTO> laboratorije = new ArrayList<>();
            while (resultSet.next()) {
                int id =  resultSet.getInt("id_lab");
                String naziv = resultSet.getString("naziv");
                String opisLokacije = resultSet.getString("opis_lokacije");
                int BSLNivo = resultSet.getInt("BSL_nivo");

                LaboratorijaDTO laboratorijaDTO = new LaboratorijaDTO(id, naziv, opisLokacije, BSLNivo);
                laboratorije.add(laboratorijaDTO);
            }
            return laboratorije;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LaboratorijaDTO selectOne(int id, Connection connection) {
        String query = "SELECT id_lab, naziv, opis_lokacije, BSL_nivo FROM laboratorija WHERE id_lab = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                LaboratorijaDTO laboratorijaDTO = null;

                if (resultSet.next()) {
                    int id_lab = resultSet.getInt("id_lab");
                    String naziv = resultSet.getString("naziv");
                    String opisLokacije = resultSet.getString("opis_lokacije");
                    int BSLNivo = resultSet.getInt("BSL_nivo");

                    laboratorijaDTO = new LaboratorijaDTO(id_lab, naziv, opisLokacije, BSLNivo);
                }

                return laboratorijaDTO;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int id;
    private final String naziv;
    private final String opisLokacije;
    private final int BSLNivo;

    public LaboratorijaDTO(int id, String naziv, String opisLokacije, int BSLNivo) {
        this.id = id;
        this.naziv = naziv;
        this.opisLokacije = opisLokacije;
        this.BSLNivo = BSLNivo;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpisLokacije() {
        return opisLokacije;
    }

    public int getBSLNivo() {
        return BSLNivo;
    }
}

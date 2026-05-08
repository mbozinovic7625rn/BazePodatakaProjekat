package baza;

import model.Istrazivac;
import model.Laboratorija;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaboratorijaDAO {

    public List<Laboratorija> sveLaboratorije() throws SQLException {
        String sql = "SELECT id_lab, naziv, opis_lokacije, BSL_nivo, broj_komora, broj_inkubatora " +
                     "FROM laboratorija ORDER BY id_lab";
        List<Laboratorija> lista = new ArrayList<>();
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Laboratorija(
                        rs.getInt("id_lab"),
                        rs.getString("naziv"),
                        rs.getString("opis_lokacije"),
                        readNullableInt(rs, "BSL_nivo"),
                        readNullableInt(rs, "broj_komora"),
                        readNullableInt(rs, "broj_inkubatora")
                ));
            }
        }
        return lista;
    }

    public List<Istrazivac> istrazivaciUzLab(int idLab) throws SQLException {
        String sql = "SELECT id_istrazivac, ime, prezime, kontakt, datum_zaposlenja, " +
                     "naucno_zvanje, oblast_specijalizacije, BSL_sertifikat " +
                     "FROM istrazivac WHERE id_lab = ? ORDER BY prezime, ime";
        List<Istrazivac> lista = new ArrayList<>();
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idLab);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("datum_zaposlenja");
                    int bsl = rs.getInt("BSL_sertifikat");
                    Boolean bslSert = rs.wasNull() ? null : (bsl != 0);
                    lista.add(new Istrazivac(
                            rs.getInt("id_istrazivac"),
                            rs.getString("ime"),
                            rs.getString("prezime"),
                            rs.getString("kontakt"),
                            d == null ? null : d.toLocalDate(),
                            rs.getString("naucno_zvanje"),
                            rs.getString("oblast_specijalizacije"),
                            bslSert
                    ));
                }
            }
        }
        return lista;
    }

    private Integer readNullableInt(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? null : v;
    }
}

package forme;

import baza.LaboratorijaDAO;
import model.Istrazivac;
import model.Laboratorija;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

public class LaboratorijeFrame extends JFrame {

    private final LaboratorijaDAO dao = new LaboratorijaDAO();
    private final JList<Laboratorija> listaLab = new JList<>();
    private final JTextArea detaljiLab = new JTextArea(5, 30);
    private final DefaultTableModel modelIstrazivaca = new DefaultTableModel(
            new Object[]{"ID", "Ime", "Prezime", "Kontakt", "Datum zaposlenja",
                         "Naučno zvanje", "Specijalizacija", "BSL sertifikat"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabelaIstrazivaca = new JTable(modelIstrazivaca);

    public LaboratorijeFrame(JFrame vlasnik) {
        super("Pregled laboratorija i istraživača");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel naslov = new JLabel("Laboratorije i njihovi istraživači", SwingConstants.CENTER);
        naslov.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        naslov.setFont(naslov.getFont().deriveFont(Font.BOLD, 15f));
        add(naslov, BorderLayout.NORTH);

        listaLab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaLab.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) prikaziSelekciju();
        });
        JScrollPane scrLista = new JScrollPane(listaLab);
        scrLista.setBorder(BorderFactory.createTitledBorder("Laboratorije"));
        scrLista.setPreferredSize(new Dimension(280, 400));

        detaljiLab.setEditable(false);
        detaljiLab.setLineWrap(true);
        detaljiLab.setWrapStyleWord(true);
        JScrollPane scrDetalji = new JScrollPane(detaljiLab);
        scrDetalji.setBorder(BorderFactory.createTitledBorder("Detalji laboratorije"));

        JScrollPane scrIstr = new JScrollPane(tabelaIstrazivaca);
        scrIstr.setBorder(BorderFactory.createTitledBorder("Istraživači u izabranoj laboratoriji"));

        JPanel desnoPanel = new JPanel(new BorderLayout(5, 5));
        desnoPanel.add(scrDetalji, BorderLayout.NORTH);
        desnoPanel.add(scrIstr, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrLista, desnoPanel);
        split.setDividerLocation(290);
        split.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(split, BorderLayout.CENTER);

        setSize(950, 560);
        setLocationRelativeTo(vlasnik);

        ucitajLaboratorije();
    }

    private void ucitajLaboratorije() {
        try {
            List<Laboratorija> lab = dao.sveLaboratorije();
            listaLab.setListData(lab.toArray(new Laboratorija[0]));
            if (!lab.isEmpty()) {
                listaLab.setSelectedIndex(0);
            } else {
                detaljiLab.setText("Nema laboratorija u bazi.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri čitanju laboratorija iz baze:\n" + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziSelekciju() {
        Laboratorija l = listaLab.getSelectedValue();
        if (l == null) {
            detaljiLab.setText("");
            modelIstrazivaca.setRowCount(0);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(l.getIdLab()).append('\n');
        sb.append("Naziv: ").append(l.getNaziv()).append('\n');
        sb.append("Lokacija: ").append(l.getOpisLokacije() == null ? "-" : l.getOpisLokacije()).append('\n');
        sb.append("BSL nivo: ").append(l.getBslNivo() == null ? "-" : l.getBslNivo()).append('\n');
        sb.append("Broj komora: ").append(l.getBrojKomora() == null ? "-" : l.getBrojKomora()).append('\n');
        sb.append("Broj inkubatora: ").append(l.getBrojInkubatora() == null ? "-" : l.getBrojInkubatora());
        detaljiLab.setText(sb.toString());
        detaljiLab.setCaretPosition(0);

        modelIstrazivaca.setRowCount(0);
        try {
            List<Istrazivac> istr = dao.istrazivaciUzLab(l.getIdLab());
            for (Istrazivac i : istr) {
                modelIstrazivaca.addRow(new Object[]{
                        i.getIdIstrazivac(),
                        i.getIme(),
                        i.getPrezime(),
                        i.getKontakt() == null ? "" : i.getKontakt(),
                        i.getDatumZaposlenja() == null ? "" : i.getDatumZaposlenja().toString(),
                        i.getNaucnoZvanje() == null ? "" : i.getNaucnoZvanje(),
                        i.getOblastSpecijalizacije() == null ? "" : i.getOblastSpecijalizacije(),
                        i.getBslSertifikat() == null ? "" : (i.getBslSertifikat() ? "DA" : "NE")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri čitanju istraživača iz baze:\n" + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package view;

import controller.LaboratorijaController;
import model.IstrazivacDTO;
import model.LaboratorijaDTO;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;

public class LaboratorijeView extends JDialog {

    private final DefaultTableModel labModel;
    private final DefaultTableModel istrazivacModel;
    private final JTable labTabela;

    public LaboratorijeView(Frame vlasnik) {
        super(vlasnik, "Laboratorije i istrazivaci", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        labModel = new DefaultTableModel(
                new String[] { "ID", "Naziv", "Opis lokacije", "BSL nivo" }, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        labTabela = new JTable(labModel);
        labTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        istrazivacModel = new DefaultTableModel(
                new String[] { "ID", "Ime", "Prezime", "Naucno zvanje", "Specijalizacija" }, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable istrazivacTabela = new JTable(istrazivacModel);

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                opremiNaslov("Laboratorije", new JScrollPane(labTabela)),
                opremiNaslov("Istrazivaci u izabranoj laboratoriji", new JScrollPane(istrazivacTabela))
        );
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        JButton zatvoriDugme = new JButton("Zatvori");
        zatvoriDugme.addActionListener(e -> dispose());
        JPanel jug = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jug.add(zatvoriDugme);
        add(jug, BorderLayout.SOUTH);

        labTabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int red = labTabela.getSelectedRow();
            if (red >= 0) {
                int idLab = (int) labModel.getValueAt(red, 0);
                ucitajIstrazivace(idLab);
            }
        });

        ucitajLaboratorije();

        setSize(720, 480);
        setLocationRelativeTo(vlasnik);
    }

    private JPanel opremiNaslov(String naslov, Component sadrzaj) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createTitledBorder(naslov));
        p.add(sadrzaj, BorderLayout.CENTER);
        return p;
    }

    private void ucitajLaboratorije() {
        labModel.setRowCount(0);
        for (LaboratorijaDTO dto : LaboratorijaController.sveLaboratorije()) {
            labModel.addRow(new Object[] {
                    dto.getId(), dto.getNaziv(), dto.getOpisLokacije(), dto.getBSLNivo()
            });
        }
    }

    private void ucitajIstrazivace(int idLab) {
        istrazivacModel.setRowCount(0);
        for (IstrazivacDTO dto : LaboratorijaController.istrazivaciZaLab(idLab)) {
            istrazivacModel.addRow(new Object[] {
                    dto.getId_istrazivac(), dto.getIme(), dto.getPrezime(),
                    dto.getNaucnoZvanje(), dto.getOblastSpecijalizacije()
            });
        }
    }
}

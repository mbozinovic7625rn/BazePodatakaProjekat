package forme;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class GlavnaFrame extends JFrame {

    private String trenutnoIme;
    private final JLabel pozdrav;

    public GlavnaFrame(String korisnickoIme) {
        super("Sistem laboratorija");
        this.trenutnoIme = korisnickoIme;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pozdrav = new JLabel(tekstPozdrava(), SwingConstants.CENTER);
        pozdrav.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        pozdrav.setFont(pozdrav.getFont().deriveFont(Font.BOLD, 15f));
        add(pozdrav, BorderLayout.NORTH);

        JPanel meni = new JPanel(new GridLayout(0, 1, 10, 10));
        meni.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton bLab = new JButton("Pregled laboratorija i istraživača");
        JButton bAzur = new JButton("Ažuriraj korisničko ime / lozinku");
        JButton bObrisi = new JButton("Obriši nalog");
        JButton bOdjava = new JButton("Odjavi se");

        meni.add(bLab);
        meni.add(bAzur);
        meni.add(bObrisi);
        meni.add(bOdjava);
        add(meni, BorderLayout.CENTER);

        bLab.addActionListener(e -> new LaboratorijeFrame(this).setVisible(true));
        bAzur.addActionListener(e -> {
            AzurirajNalogDialog d = new AzurirajNalogDialog(this, trenutnoIme);
            d.setVisible(true);
            if (d.getNovoIme() != null) {
                trenutnoIme = d.getNovoIme();
                pozdrav.setText(tekstPozdrava());
            }
        });
        bObrisi.addActionListener(e -> {
            ObrisiNalogDialog d = new ObrisiNalogDialog(this, trenutnoIme);
            d.setVisible(true);
            if (d.jeObrisan()) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        bOdjava.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setSize(520, 360);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private String tekstPozdrava() {
        return "Prijavljen korisnik: " + trenutnoIme;
    }
}

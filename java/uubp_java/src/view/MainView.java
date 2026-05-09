package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class MainView extends JFrame {

    private final JLabel pozdrav;
    private String korisnickoIme;

    public MainView(String korisnickoIme) {
        super("Sistem laboratorija");
        this.korisnickoIme = korisnickoIme;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pozdrav = new JLabel(pozdravTekst(), SwingConstants.CENTER);
        pozdrav.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        pozdrav.setFont(pozdrav.getFont().deriveFont(Font.BOLD, 15f));
        add(pozdrav, BorderLayout.NORTH);

        JPanel meni = new JPanel(new GridLayout(0, 1, 10, 10));
        meni.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton laboratorijeDugme = new JButton("Pregled laboratorija i istrazivaca");
        JButton azurirajDugme = new JButton("Azuriraj nalog");
        JButton obrisiDugme = new JButton("Obrisi nalog");
        JButton odjavaDugme = new JButton("Odjavi se");

        meni.add(laboratorijeDugme);
        meni.add(azurirajDugme);
        meni.add(obrisiDugme);
        meni.add(odjavaDugme);
        add(meni, BorderLayout.CENTER);

        laboratorijeDugme.addActionListener(e ->
                new LaboratorijeView(this).setVisible(true));

        azurirajDugme.addActionListener(e -> {
            AzurirajNalogDialog dlg = new AzurirajNalogDialog(this, this.korisnickoIme);
            dlg.setVisible(true);
            String novo = dlg.getNovoKorisnickoIme();
            if (novo != null) {
                this.korisnickoIme = novo;
                pozdrav.setText(pozdravTekst());
            }
        });

        obrisiDugme.addActionListener(e -> {
            ObrisiNalogDialog dlg = new ObrisiNalogDialog(this, this.korisnickoIme);
            dlg.setVisible(true);
            if (dlg.jeObrisan()) {
                dispose();
            }
        });

        odjavaDugme.addActionListener(e -> dispose());

        setSize(520, 360);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private String pozdravTekst() {
        return "Prijavljen korisnik: " + korisnickoIme;
    }
}

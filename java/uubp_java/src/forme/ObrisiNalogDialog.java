package forme;

import korisnici.KorisnikSkladiste;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

public class ObrisiNalogDialog extends JDialog {

    private final String trenutnoIme;
    private final JPasswordField tfLozinka = new JPasswordField(20);
    private final KorisnikSkladiste skladiste = new KorisnikSkladiste();

    private boolean obrisan = false;

    public ObrisiNalogDialog(JFrame vlasnik, String trenutnoIme) {
        super(vlasnik, "Brisanje korisničkog naloga", true);
        this.trenutnoIme = trenutnoIme;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel info = new JLabel(
                "<html><body style='width:320px;text-align:center;padding:10px;'>" +
                "Da biste obrisali nalog <b>" + escape(trenutnoIme) + "</b>,<br>" +
                "unesite svoju lozinku radi potvrde.</body></html>",
                SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(info, BorderLayout.NORTH);

        JPanel forma = new JPanel(new GridBagLayout());
        forma.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; forma.add(new JLabel("Lozinka:"), g);
        g.gridx = 1;              forma.add(tfLozinka, g);
        add(forma, BorderLayout.CENTER);

        JButton bObrisi = new JButton("Obriši nalog");
        JButton bOtkazi = new JButton("Otkaži");
        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dugmad.add(bObrisi);
        dugmad.add(bOtkazi);
        add(dugmad, BorderLayout.SOUTH);

        bObrisi.addActionListener(e -> obrisi());
        bOtkazi.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(bObrisi);
        pack();
        setLocationRelativeTo(vlasnik);
    }

    public boolean jeObrisan() {
        return obrisan;
    }

    private void obrisi() {
        String lozinka = new String(tfLozinka.getPassword());
        if (lozinka.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Unesite lozinku.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int odgovor = JOptionPane.showConfirmDialog(this,
                "Da li ste sigurni da želite trajno da obrišete svoj nalog?",
                "Potvrda", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (odgovor != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (skladiste.obrisi(trenutnoIme, lozinka)) {
                obrisan = true;
                JOptionPane.showMessageDialog(this,
                        "Vaš nalog je obrisan.",
                        "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Lozinka nije ispravna. Nalog nije obrisan.",
                        "Greška", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri pisanju u fajl:\n" + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}

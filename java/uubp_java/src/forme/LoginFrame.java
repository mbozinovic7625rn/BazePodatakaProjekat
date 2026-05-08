package forme;

import korisnici.KorisnikSkladiste;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

public class LoginFrame extends JFrame {

    private final JTextField tfKorisnickoIme = new JTextField(20);
    private final JPasswordField tfLozinka = new JPasswordField(20);
    private final KorisnikSkladiste skladiste = new KorisnikSkladiste();

    public LoginFrame() {
        super("Prijava — sistem laboratorija");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel naslov = new JLabel("Sistem laboratorija — eksterni korisnik", SwingConstants.CENTER);
        naslov.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        naslov.setFont(naslov.getFont().deriveFont(Font.BOLD, 16f));
        add(naslov, BorderLayout.NORTH);

        JPanel forma = new JPanel(new GridBagLayout());
        forma.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; forma.add(new JLabel("Korisničko ime:"), g);
        g.gridx = 1;              forma.add(tfKorisnickoIme, g);
        g.gridx = 0; g.gridy = 1; forma.add(new JLabel("Lozinka:"), g);
        g.gridx = 1;              forma.add(tfLozinka, g);
        add(forma, BorderLayout.CENTER);

        JButton bUloguj = new JButton("Prijavi se");
        JButton bRegistracija = new JButton("Registruj se");
        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dugmad.add(bUloguj);
        dugmad.add(bRegistracija);
        add(dugmad, BorderLayout.SOUTH);

        bUloguj.addActionListener(e -> uloguj());
        bRegistracija.addActionListener(e -> {
            RegistracijaDialog d = new RegistracijaDialog(this);
            d.setVisible(true);
        });

        getRootPane().setDefaultButton(bUloguj);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private void uloguj() {
        String u = tfKorisnickoIme.getText().trim();
        String p = new String(tfLozinka.getPassword());
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Unesite korisničko ime i lozinku.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (skladiste.uloguj(u, p)) {
                tfLozinka.setText("");
                new GlavnaFrame(u).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pogrešno korisničko ime ili lozinka.",
                        "Greška", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri čitanju iz fajla:\n" + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}

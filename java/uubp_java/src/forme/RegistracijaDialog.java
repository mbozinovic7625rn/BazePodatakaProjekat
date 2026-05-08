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
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

public class RegistracijaDialog extends JDialog {

    private final JTextField tfKorisnickoIme = new JTextField(20);
    private final JPasswordField tfLozinka = new JPasswordField(20);
    private final JPasswordField tfPotvrda = new JPasswordField(20);
    private final KorisnikSkladiste skladiste = new KorisnikSkladiste();

    public RegistracijaDialog(JFrame vlasnik) {
        super(vlasnik, "Registracija novog korisnika", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel forma = new JPanel(new GridBagLayout());
        forma.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; forma.add(new JLabel("Korisničko ime:"), g);
        g.gridx = 1;              forma.add(tfKorisnickoIme, g);
        g.gridx = 0; g.gridy = 1; forma.add(new JLabel("Lozinka:"), g);
        g.gridx = 1;              forma.add(tfLozinka, g);
        g.gridx = 0; g.gridy = 2; forma.add(new JLabel("Potvrda lozinke:"), g);
        g.gridx = 1;              forma.add(tfPotvrda, g);
        add(forma, BorderLayout.CENTER);

        JButton bRegistruj = new JButton("Registruj");
        JButton bOtkazi = new JButton("Otkaži");
        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dugmad.add(bRegistruj);
        dugmad.add(bOtkazi);
        add(dugmad, BorderLayout.SOUTH);

        bRegistruj.addActionListener(e -> registruj());
        bOtkazi.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(bRegistruj);
        pack();
        setLocationRelativeTo(vlasnik);
    }

    private void registruj() {
        String u = tfKorisnickoIme.getText().trim();
        String p1 = new String(tfLozinka.getPassword());
        String p2 = new String(tfPotvrda.getPassword());

        if (u.isEmpty() || p1.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Sva polja su obavezna.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!p1.equals(p2)) {
            JOptionPane.showMessageDialog(this,
                    "Lozinke se ne podudaraju.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            skladiste.registruj(u, p1);
            JOptionPane.showMessageDialog(this,
                    "Uspešna registracija. Sada se možete prijaviti.",
                    "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri pisanju u fajl:\n" + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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

public class AzurirajNalogDialog extends JDialog {

    private final String trenutnoIme;
    private final JTextField tfNovoIme;
    private final JPasswordField tfTrenutnaLozinka = new JPasswordField(20);
    private final JPasswordField tfNovaLozinka = new JPasswordField(20);
    private final JPasswordField tfPotvrda = new JPasswordField(20);
    private final KorisnikSkladiste skladiste = new KorisnikSkladiste();

    private String novoIme = null;

    public AzurirajNalogDialog(JFrame vlasnik, String trenutnoIme) {
        super(vlasnik, "Ažuriranje korisničkog naloga", true);
        this.trenutnoIme = trenutnoIme;
        this.tfNovoIme = new JTextField(trenutnoIme, 20);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel forma = new JPanel(new GridBagLayout());
        forma.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; forma.add(new JLabel("Novo korisničko ime:"), g);
        g.gridx = 1;              forma.add(tfNovoIme, g);
        g.gridx = 0; g.gridy = 1; forma.add(new JLabel("Trenutna lozinka:"), g);
        g.gridx = 1;              forma.add(tfTrenutnaLozinka, g);
        g.gridx = 0; g.gridy = 2; forma.add(new JLabel("Nova lozinka:"), g);
        g.gridx = 1;              forma.add(tfNovaLozinka, g);
        g.gridx = 0; g.gridy = 3; forma.add(new JLabel("Potvrda nove lozinke:"), g);
        g.gridx = 1;              forma.add(tfPotvrda, g);
        add(forma, BorderLayout.CENTER);

        JButton bSacuvaj = new JButton("Sačuvaj");
        JButton bOtkazi = new JButton("Otkaži");
        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dugmad.add(bSacuvaj);
        dugmad.add(bOtkazi);
        add(dugmad, BorderLayout.SOUTH);

        bSacuvaj.addActionListener(e -> sacuvaj());
        bOtkazi.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(bSacuvaj);
        pack();
        setLocationRelativeTo(vlasnik);
    }

    public String getNovoIme() {
        return novoIme;
    }

    private void sacuvaj() {
        String unetoIme = tfNovoIme.getText().trim();
        String trenutna = new String(tfTrenutnaLozinka.getPassword());
        String nova1 = new String(tfNovaLozinka.getPassword());
        String nova2 = new String(tfPotvrda.getPassword());

        if (unetoIme.isEmpty() || trenutna.isEmpty() || nova1.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Sva polja su obavezna.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!nova1.equals(nova2)) {
            JOptionPane.showMessageDialog(this,
                    "Nova lozinka i potvrda se ne podudaraju.",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (!skladiste.uloguj(trenutnoIme, trenutna)) {
                JOptionPane.showMessageDialog(this,
                        "Trenutna lozinka nije ispravna.",
                        "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            skladiste.azuriraj(trenutnoIme, unetoIme, nova1);
            novoIme = unetoIme;
            JOptionPane.showMessageDialog(this,
                    "Nalog uspešno ažuriran.",
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

package view;

import controller.NalogController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AzurirajNalogDialog extends JDialog {

    private final JTextField novoKorisnickoImePolje;
    private final JPasswordField staraLozinkaPolje;
    private final JPasswordField novaLozinkaPolje;
    private final JPasswordField potvrdaLozinkePolje;

    private final String trenutnoKorisnickoIme;
    private String novoKorisnickoIme;

    public AzurirajNalogDialog(Frame vlasnik, String trenutnoKorisnickoIme) {
        super(vlasnik, "Azuriranje naloga", true);
        this.trenutnoKorisnickoIme = trenutnoKorisnickoIme;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Trenutno korisnicko ime:"), c);
        c.gridx = 1; c.gridy = 0;
        panel.add(new JLabel(trenutnoKorisnickoIme), c);

        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Novo korisnicko ime:"), c);
        c.gridx = 1; c.gridy = 1;
        novoKorisnickoImePolje = new JTextField(15);
        panel.add(novoKorisnickoImePolje, c);

        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("Stara lozinka:"), c);
        c.gridx = 1; c.gridy = 2;
        staraLozinkaPolje = new JPasswordField(15);
        panel.add(staraLozinkaPolje, c);

        c.gridx = 0; c.gridy = 3;
        panel.add(new JLabel("Nova lozinka:"), c);
        c.gridx = 1; c.gridy = 3;
        novaLozinkaPolje = new JPasswordField(15);
        panel.add(novaLozinkaPolje, c);

        c.gridx = 0; c.gridy = 4;
        panel.add(new JLabel("Potvrda nove lozinke:"), c);
        c.gridx = 1; c.gridy = 4;
        potvrdaLozinkePolje = new JPasswordField(15);
        panel.add(potvrdaLozinkePolje, c);

        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton sacuvajDugme = new JButton("Sacuvaj");
        JButton otkaziDugme = new JButton("Otkazi");
        dugmad.add(sacuvajDugme);
        dugmad.add(otkaziDugme);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        panel.add(dugmad, c);

        add(panel);
        pack();
        setLocationRelativeTo(vlasnik);

        sacuvajDugme.addActionListener(e -> onSacuvaj());
        otkaziDugme.addActionListener(e -> dispose());
    }

    private void onSacuvaj() {
        String novoIme = novoKorisnickoImePolje.getText().trim();
        String staraLozinka = new String(staraLozinkaPolje.getPassword());
        String novaLozinka = new String(novaLozinkaPolje.getPassword());
        String potvrda = new String(potvrdaLozinkePolje.getPassword());

        if (NalogController.azuriraj(trenutnoKorisnickoIme, staraLozinka, novoIme, novaLozinka, potvrda)) {
            postaviUspeh(novoIme);
        } else {
            JOptionPane.showMessageDialog(this, "Azuriranje nije uspelo.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postaviUspeh(String novoIme) {
        this.novoKorisnickoIme = novoIme.isEmpty() ? trenutnoKorisnickoIme : novoIme;
        dispose();
    }

    public String getNovoKorisnickoIme() {
        return novoKorisnickoIme;
    }
}

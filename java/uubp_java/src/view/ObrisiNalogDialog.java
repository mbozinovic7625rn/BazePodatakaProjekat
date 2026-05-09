package view;

import controller.NalogController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ObrisiNalogDialog extends JDialog {

    private final JPasswordField lozinkaPolje;
    private final String korisnickoIme;
    private boolean obrisan = false;

    public ObrisiNalogDialog(Frame vlasnik, String korisnickoIme) {
        super(vlasnik, "Brisanje naloga", true);
        this.korisnickoIme = korisnickoIme;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(new JLabel("Da bi obrisali nalog \"" + korisnickoIme + "\", unesite lozinku:"), c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Lozinka:"), c);
        c.gridx = 1; c.gridy = 1;
        lozinkaPolje = new JPasswordField(15);
        panel.add(lozinkaPolje, c);

        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton obrisiDugme = new JButton("Obrisi");
        JButton otkaziDugme = new JButton("Otkazi");
        dugmad.add(obrisiDugme);
        dugmad.add(otkaziDugme);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        panel.add(dugmad, c);

        add(panel);
        pack();
        setLocationRelativeTo(vlasnik);

        obrisiDugme.addActionListener(e -> onObrisi());
        otkaziDugme.addActionListener(e -> dispose());
    }

    private void onObrisi() {
        String lozinka = new String(lozinkaPolje.getPassword());

        if (NalogController.obrisi(korisnickoIme, lozinka)) {
            postaviObrisan();
        } else {
            JOptionPane.showMessageDialog(this, "Brisanje nije uspelo. Proverite lozinku.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postaviObrisan() {
        this.obrisan = true;
        dispose();
    }

    public boolean jeObrisan() {
        return obrisan;
    }
}

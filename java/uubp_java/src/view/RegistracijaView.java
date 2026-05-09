package view;

import controller.AuthController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RegistracijaView extends JFrame {

    private final JTextField korisnickoImePolje;
    private final JPasswordField lozinkaPolje;
    private final JPasswordField potvrdaLozinkePolje;

    public RegistracijaView() {
        super("Registracija");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Korisnicko ime:"), c);
        c.gridx = 1; c.gridy = 0;
        korisnickoImePolje = new JTextField(15);
        panel.add(korisnickoImePolje, c);

        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Lozinka:"), c);
        c.gridx = 1; c.gridy = 1;
        lozinkaPolje = new JPasswordField(15);
        panel.add(lozinkaPolje, c);

        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("Potvrda lozinke:"), c);
        c.gridx = 1; c.gridy = 2;
        potvrdaLozinkePolje = new JPasswordField(15);
        panel.add(potvrdaLozinkePolje, c);

        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton registracijaDugme = new JButton("Registruj se");
        JButton otkaziDugme = new JButton("Otkazi");
        dugmad.add(registracijaDugme);
        dugmad.add(otkaziDugme);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        panel.add(dugmad, c);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        registracijaDugme.addActionListener(e -> onRegistracija());
        otkaziDugme.addActionListener(e -> dispose());
    }

    private void onRegistracija() {
        String korisnickoIme = korisnickoImePolje.getText().trim();
        String lozinka = new String(lozinkaPolje.getPassword());
        String potvrda = new String(potvrdaLozinkePolje.getPassword());

        if (AuthController.registracija(korisnickoIme, lozinka, potvrda)) {
            JOptionPane.showMessageDialog(this, "Registracija uspesna.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registracija nije uspela.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame {

    private final JTextField korisnickoImePolje;
    private final JPasswordField lozinkaPolje;

    public LoginView() {
        super("Prijava");
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

        JPanel dugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton prijavaDugme = new JButton("Prijavi se");
        JButton registracijaDugme = new JButton("Registruj se");
        dugmad.add(prijavaDugme);
        dugmad.add(registracijaDugme);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        panel.add(dugmad, c);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        prijavaDugme.addActionListener(e -> onPrijava());
        registracijaDugme.addActionListener(e -> otvoriRegistraciju());
    }

    private void onPrijava() {
        String korisnickoIme = korisnickoImePolje.getText().trim();
        String lozinka = new String(lozinkaPolje.getPassword());

        if (AuthController.prijava(korisnickoIme, lozinka)) {
            prijavaUspesna(korisnickoIme);
        } else {
            JOptionPane.showMessageDialog(this, "Pogresni kredencijali.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prijavaUspesna(String korisnickoIme) {
        setVisible(false);
        MainView main = new MainView(korisnickoIme);
        main.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
        main.setVisible(true);
    }

    private void otvoriRegistraciju() {
        setVisible(false);
        RegistracijaView reg = new RegistracijaView();
        reg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
        reg.setVisible(true);
    }
}

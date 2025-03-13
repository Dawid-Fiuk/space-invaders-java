import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoUstawien extends JFrame {
    private JComboBox<String> poziomTrudnosci;
    private JCheckBox trybSpecjalny;

    public OknoUstawien() {
        setTitle("Ustawienia");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        poziomTrudnosci = new JComboBox<>(new String[]{"Latwy", "Sredni", "Trudny"});
        trybSpecjalny = new JCheckBox("Tryb Specjalny");

        JButton zapiszButton = new JButton("Zapisz");
        zapiszButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Zapisz ustawienia
                Ustawienia.poziomTrudnosci = poziomTrudnosci.getSelectedIndex();
                Ustawienia.trybSpecjalny = trybSpecjalny.isSelected();
                new EkranStartowy();
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Poziom Trudnosci:"), gbc);
        gbc.gridx = 1;
        panel.add(poziomTrudnosci, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(trybSpecjalny, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(zapiszButton, gbc);

        add(panel);
        setVisible(true);
    }
}

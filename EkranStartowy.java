import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EkranStartowy extends JFrame {
    static String loginGracza;
    public EkranStartowy() {
        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Space Invaders");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 20, 0); // Wstawka na górze
        panel.add(titleLabel, gbc);

        // Dodajemy etykietę "Login:"
        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 10); // Wstawka na dole
        panel.add(loginLabel, gbc);

        // Dodajemy pole tekstowe dla wprowadzenia loginu gracza
        JTextField loginField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0); // Wstawka na dole
        panel.add(loginField, gbc);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLoginGracza(loginField.getText());
                OknoGry oknoGry = new OknoGry(); // Tworzenie okna gry
                oknoGry.setVisible(true); // Wyświetlenie okna gry
                oknoGry.rozpocznijGre();
                dispose(); // Zamknięcie ekranu startowego
            }
        });

        JButton ustawieniaButton = new JButton("Ustawienia");
        ustawieniaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OknoUstawien();
                dispose();
            }
        });

        JButton highscoreButton = new JButton("Highscores");
        highscoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OknoHighscore();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(startButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0); // Standardowa wstawka
        panel.add(ustawieniaButton, gbc);

        gbc.gridy = 4;
        panel.add(highscoreButton, gbc);

        add(panel);
        setVisible(true);
    }
    private static void setLoginGracza(String login){

        loginGracza = login;
    }
    public static String getLoginGracza(){
        return loginGracza;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EkranStartowy ekranStartowy = new EkranStartowy(); // Tworzenie ekranu startowego
                ekranStartowy.setVisible(true); // Wyświetlenie ekranu startowego
            }
        });
    }
}

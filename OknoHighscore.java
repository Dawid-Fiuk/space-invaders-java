import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OknoHighscore extends JFrame {
    private JList<String> highscoreLista;

    public OknoHighscore() {
        setTitle("Highscores");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        highscoreLista = new JList<>();
        odczytajHighscore();

        add(new JScrollPane(highscoreLista));
        setVisible(true);
    }

    private void odczytajHighscore() {
        File file = new File("highscore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DefaultListModel<String> model = new DefaultListModel<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                model.addElement(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        highscoreLista.setModel(model);
    }
}

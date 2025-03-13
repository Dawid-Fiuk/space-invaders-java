import javax.swing.*;

public class Main {
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

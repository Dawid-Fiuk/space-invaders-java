import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PanelGry extends JPanel implements ActionListener {
    private javax.swing.Timer timer;
    private boolean graPauzowana = false;
    private boolean graSkonczona = false;
    private Gracz gracz;
    private java.util.List<Wrog> wrogowie;
    private int punkty = 0;
    private final String highscorePlik = "highscore.txt";
    private final int szerokoscOkna = 800;
    private String loginGracza;


    public PanelGry(String loginGracza) {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        this.loginGracza = loginGracza;
        gracz = new Gracz();
        wrogowie = new ArrayList<>();
        inicjalizujWrogow();

        timer = new javax.swing.Timer(10, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                gracz.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                gracz.keyPressed(e);
            }
        });

        if (Ustawienia.trybSpecjalny) {
            Random rand = new Random();
            javax.swing.Timer specjalnyTimer = new javax.swing.Timer(5000 + rand.nextInt(10000), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (Wrog wrog : wrogowie) {
                        wrog.zmienPredkosc(rand.nextInt(3) - 1);
                    }
                }
            });
            specjalnyTimer.start();
        }
    }

    private void inicjalizujWrogow() {
        // Zaktualizuj metodę inicjalizujWrogow, aby używała poziomu trudności z ustawień
        int rzadWrogow = 1 + Ustawienia.poziomTrudnosci * 2;
        for (int i = 0; i < rzadWrogow; i++) {
            for (int j = 0; j < 8; j++) {
                Wrog wrog = new Wrog(70 + j * 70, 50 + i * 50);
                wrog.zmienPredkosc(Ustawienia.poziomTrudnosci);
                wrogowie.add(wrog);
            }
        }
    }

    public void pauzujGre() {
        graPauzowana = !graPauzowana;
    }
    public void graczLewo() {
        gracz.ruchLewo();
    }

    // Metoda obsługująca ruch gracza w prawo
    public void graczPrawo() {
        gracz.ruchPrawo();
    }

    // Metoda obsługująca strzał gracza
    public void strzel() {
        gracz.strzel();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (graSkonczona) {
            wyswietlKoniecGry(g);
        } else if (!graPauzowana) {
            Graphics2D g2d = (Graphics2D) g;

            gracz.rysuj(g2d);

            for (Pocisk pocisk : gracz.getPociski()) {
                pocisk.rysuj(g2d);
            }

            for (Wrog wrog : wrogowie) {
                wrog.rysuj(g2d);
            }

            g2d.setColor(Color.WHITE);
            g2d.drawString("Punkty: " + punkty, 10, 20);
            g2d.drawString("Poziom: " + Ustawienia.poziomTrudnosci, 10, 40);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (!graPauzowana && !graSkonczona) {
                gracz.aktualizuj();

                java.util.List<Pocisk> pociski = gracz.getPociski();
                for (int i = 0; i < pociski.size(); i++) {
                    Pocisk pocisk = pociski.get(i);
                    if (pocisk.isVisible()) {
                        pocisk.aktualizuj();
                    } else {
                        pociski.remove(i);
                    }
                }

                for (int i = 0; i < wrogowie.size(); i++) {
                    Wrog wrog = wrogowie.get(i);
                    if (wrog.isVisible()) {
                        wrog.aktualizuj();
                        if (wrog.getX() <= 0 || wrog.getX() >= szerokoscOkna - wrog.getSzerokosc()) {
                            zmienKierunekWrogow();
                            break;
                        }
                        if (wrog.getY() >= 550) {
                            koniecGry();
                        }
                    } else {
                        wrogowie.remove(i);
                    }
                }

                if (wrogowie.isEmpty()) {
                    int szybkosc = Ustawienia.poziomTrudnosci;
                    szybkosc++;
                    inicjalizujWrogow();
                    for (Wrog wrog : wrogowie) {
                        wrog.zmienPredkosc(szybkosc);
                    }

                }

                sprawdzKolizje();

                repaint();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Błąd w actionPerformed: " + ex.getMessage());
        }
    }

    private void zmienKierunekWrogow() {
        for (Wrog wrog : wrogowie) {
            wrog.setKierunekRuchuWrogow(-wrog.getKierunekRuchuWrogow());
            wrog.aktualizujPoziom();
        }
    }

    private void sprawdzKolizje() {
        java.util.List<Pocisk> pociski = gracz.getPociski();

        for (int i = 0; i < pociski.size(); i++) {
            Pocisk pocisk = pociski.get(i);
            Rectangle r1 = pocisk.getBounds();

            for (int j = 0; j < wrogowie.size(); j++) {
                Wrog wrog = wrogowie.get(j);
                Rectangle r2 = wrog.getBounds();

                if (r1.intersects(r2)) {
                    pocisk.setVisible(false);
                    wrog.setVisible(false);
                    punkty++;
                }
            }
        }
    }

    private void koniecGry() {
        graSkonczona = true;
        zapiszWynik(loginGracza, punkty);
    }

    private void wyswietlKoniecGry(Graphics g) {
        String message = "Koniec gry!! Wynik: " + punkty;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.drawString(message, getWidth() / 2 - g2d.getFontMetrics().stringWidth(message) / 2, getHeight() / 2);

        java.util.List<String> highscoreList = odczytajHighscore();
        int pozycja = highscoreList.indexOf(String.valueOf(punkty)) + 1;
        if (pozycja > 0 && pozycja <= 10) {
            String gratulacje = "Gratulacje! Jesteś na " + pozycja + " pozycji w highscore!";
            g2d.drawString(gratulacje, getWidth() / 2 - g2d.getFontMetrics().stringWidth(gratulacje) / 2, getHeight() / 2 + 20);
        }
    }

    private void zapiszWynik(String loginGracza, int wynik) {
        try {
            List<String> highscoreList = odczytajHighscore();
            highscoreList.add(loginGracza + ": " + wynik); // Dodajemy login gracza i wynik
            Collections.sort(highscoreList, Collections.reverseOrder());
            if (highscoreList.size() > 10) {
                highscoreList = highscoreList.subList(0, 10);
            }
            PrintWriter writer = new PrintWriter(new FileWriter(highscorePlik));
            for (String s : highscoreList) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private java.util.List<String> odczytajHighscore() {
        java.util.List<String> highscoreList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highscorePlik));
            String line;
            while ((line = reader.readLine()) != null) {
                highscoreList.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Plik highscore nie istnieje, zostanie utworzony nowy.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }
}

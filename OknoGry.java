import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OknoGry extends JFrame {
    private PanelGry panelGry; // Dodajemy referencję do PanelGry

    public OknoGry() {
        initUI();
    }

    private void initUI() {
        setTitle("Space Invaders");
        setSize(800, 650);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuGry = new JMenu("Menu");
        menuBar.add(menuGry);
        JMenu pause = new JMenu("Pauza");
        menuBar.add(pause);

        JMenuItem startMenuItem = new JMenuItem("Restart");
        JMenuItem opcjeMenuItem = new JMenuItem("Rekordy");
        JMenuItem pauzaMenuItem = new JMenuItem("Pauza");

        menuGry.add(startMenuItem);
        menuGry.add(opcjeMenuItem);
        pause.add(pauzaMenuItem);

        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start menu item clicked");
                rozpocznijGre();
            }
        });

        opcjeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcje menu item clicked");
                otworzOpcje();
            }
        });

        pauzaMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pauza menu item clicked");
                pauzujGre(); // Wywołujemy metodę pauzowania
            }
        });


        setVisible(true);
    }

    public void rozpocznijGre() {
        System.out.println("rozpocznijGre method called");
        remove(getContentPane());
        panelGry = new PanelGry(EkranStartowy.getLoginGracza());
        add(panelGry);
        revalidate();
        repaint();
        panelGry.requestFocusInWindow();
    }

    private void otworzOpcje() {
        System.out.println("otworzOpcje method called");
        // Tworzymy i wyświetlamy okno opcji
        //OknoUstawien oknoUstawien = new OknoUstawien(panelGry); // Przekazujemy referencję do PanelGry
        //oknoUstawien.setVisible(true);
    }

    private void pauzujGre() {
        System.out.println("pauzujGre method called");
        if (panelGry != null) { // Sprawdzamy, czy panelGry został już utworzony
            panelGry.pauzujGre(); // Wywołujemy metodę pauzowania
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OknoGry ex = new OknoGry();
                ex.setVisible(true);
            }
        });
    }
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Gracz {
    private int x, y;
    private final int szerokosc = 50, wysokosc = 10;
    private final int predkosc = 5;
    private List<Pocisk> pociski;
    private boolean ruchLewo, ruchPrawo;
    private Image obrazGracza;

    public Gracz() {
        x = 375;
        y = 500;
        pociski = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getResourceAsStream("gracz.png");
            obrazGracza = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aktualizuj() {
        if (ruchLewo && x > 0) {
            x -= predkosc;
        }
        if (ruchPrawo && x < 750) {
            x += predkosc;
        }
    }

    public void rysuj(Graphics g) {
        if (obrazGracza != null) {
            g.drawImage(obrazGracza, x, y, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, szerokosc, wysokosc);
        }
    }

    public void strzel() {
        pociski.add(new Pocisk(x + szerokosc / 2, y));
    }

    public List<Pocisk> getPociski() {
        return pociski;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            ruchLewo = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            ruchPrawo = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            strzel();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            ruchLewo = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            ruchPrawo = false;
        }
    }
    public void ruchPrawo() {
        ruchPrawo = true;
    }

    public void zatrzymajRuchPrawo() {
        ruchPrawo = false;
    }
    public void ruchLewo() {
        ruchLewo = true;
    }

    public void zatrzymajRuchLewo() {
        ruchLewo = false;
    }

    public Rectangle getBounds() {
        if (obrazGracza != null) {
            return new Rectangle(x, y, obrazGracza.getWidth(null), obrazGracza.getHeight(null));
        } else {
            return new Rectangle(x, y, szerokosc, wysokosc);
        }
    }
}

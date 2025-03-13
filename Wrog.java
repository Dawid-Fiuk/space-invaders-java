import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Wrog {
    private int x, y;
    private final int szerokosc = 40, wysokosc = 20;
    private int predkosc = 1;
    private boolean widoczny;
    private Image obrazWroga;
    private int kierunekRuchuWrogow = 1; // 1 - w prawo, -1 - w lewo

    public Wrog(int x, int y) {
        this.x = x;
        this.y = y;
        widoczny = true;
        try {
            InputStream inputStream = getClass().getResourceAsStream("wrog1.png");
            obrazWroga = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aktualizuj() {
        x += predkosc * kierunekRuchuWrogow;
    }

    public void aktualizujPoziom() {
        y += wysokosc;
    }

    public void rysuj(Graphics g) {
        if (obrazWroga != null && widoczny) {
            g.drawImage(obrazWroga, x, y, null);
        }
    }

    public boolean isVisible() {
        return widoczny;
    }

    public void setVisible(boolean widoczny) {
        this.widoczny = widoczny;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, szerokosc, wysokosc);
    }

    public void zmienPredkosc(int zmiana) {
        predkosc += zmiana;
        if (predkosc < 1) {
            predkosc = 1;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getKierunekRuchuWrogow() {
        return kierunekRuchuWrogow;
    }

    public void setKierunekRuchuWrogow(int kierunekRuchuWrogow) {
        this.kierunekRuchuWrogow = kierunekRuchuWrogow;
    }
}

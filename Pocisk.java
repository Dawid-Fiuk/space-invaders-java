import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Pocisk {
    private int x, y;
    private final int szerokosc = 2, wysokosc = 10;
    private final int predkosc = 5;
    private boolean widoczny;
    private Image obrazPocisku;

    public Pocisk(int x, int y) {
        this.x = x;
        this.y = y;
        widoczny = true;
        try {
            InputStream inputStream = getClass().getResourceAsStream("pocisk.png");
            obrazPocisku = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aktualizuj() {
        y -= predkosc;
        if (y < 0) {
            widoczny = false;
        }
    }

    public void rysuj(Graphics g) {
        if (obrazPocisku != null && widoczny) {
            g.drawImage(obrazPocisku, x, y, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, szerokosc, wysokosc);
        }
    }

    public boolean isVisible() {
        return widoczny;
    }

    public void setVisible(boolean widoczny) {
        this.widoczny = widoczny;
    }

    public Rectangle getBounds() {
        if (obrazPocisku != null) {
            return new Rectangle(x, y, obrazPocisku.getWidth(null), obrazPocisku.getHeight(null));
        } else {
            return new Rectangle(x, y, szerokosc, wysokosc);
        }
    }
}

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Deck {

    Image deckImage;

    public Deck(String s) throws IOException {
        this.deckImage = ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin\\Original\\"+ s +"_State\\deck.jpg"));
        w = 70;
        h = 120;
    }
    public Deck() throws IOException {
        this.deckImage = ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\Original\\All_another\\Bin.bmp"));
        w = 70;
        h = 120;
    }
    int x = 600;
    int y = 200;
    int w;
    int h;

    public void drawDeck(Graphics2D g2d){
        Image test = deckImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        g2d.drawImage(test, x, y, null);
    }

    public boolean checkClick(MouseEvent e){
        if ((e.getX() > x) && (e.getX() < x + w) && (e.getY() > y) && (e.getY() < y + h)) {
            return true;
        }else{
            return false;
        }
    }
    public void setXY(int x0, int y0){
        this.x = x0;
        this.y = y0;
    }

}

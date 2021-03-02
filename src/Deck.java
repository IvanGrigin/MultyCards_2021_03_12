import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Deck {

    BufferedImage deckImage;

    public Deck(String s) throws IOException {
        this.deckImage = ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\Original\\"+ s +"\\deck.bmp"));
        w = deckImage.getWidth();
        h = deckImage.getHeight();
    }
    public Deck() throws IOException {
        this.deckImage = ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\Original\\All_another\\Bin.bmp"));
        w = deckImage.getWidth();
        h = deckImage.getHeight();
    }
    int x = 600;
    int y = 200;
    int w;
    int h;

    public void drawDeck(Graphics2D g2d){
        g2d.drawImage(deckImage, x, y, null);
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {

    public Card(){
        x = 20;
        y = 30;
        h = 120;
        w = 70;
    }

    public Card(String gameset0, String deck0,String numberOfCard0){
        x = 20;
        prex = 0;
        prey =0;
        y = 30;
        h = 120;
        w = 70;
        this.gameset = gameset0;
        this.deck = deck0;
        this.numberOfCard = numberOfCard0;
    }

    int x;
    int y;
    int prex;
    int prey;
    int h;
    int w;
    int higth;

    int rx = 0;
    int ry = 0;

    String gameset = "Original";
    String deck = "Doors";
    String numberOfCard = "01";
    int faseUpIsTrue = -1;

    public boolean checkClick(MouseEvent e) {
        if ((e.getX() > this.x) && (e.getX() < this.x + this.w) && (e.getY() > this.y) && (e.getY() < this.y + this.h)) {
            return true;
        }else{
            return false;
        }
    }

    public String sentState(){
        String s = x + " " + y + " " + w + " " + h + " " + gameset + " " + deck + " " + numberOfCard + " " + faseUpIsTrue + " " + higth + " " + rx + " " + ry;
        return s;
    }

    public boolean isInDarkGrayZone(int y0){
        // у0 -- положение верхней границы
        if (this.y + this.h > y0){
            return true;
        }else{
            return false;
        }
    }
    public boolean isInLightGrayZone(int y0, int y1){
        // y0 -- граница верхней зоны
        // y1 -- это положение нижней границы зоны

        if ((this.y + this.h <= y1)&&(this.y + this.h >= y0)){
            return true;
        }else{
            return false;
        }
    }
    public int checkDistance(){
        return (int) Math.sqrt((x-prex)*(x-prex) + (y-prey)*(y-prey));
    }

    public boolean checkTheSameCards(Card t){
        // Этот метод сравнивает две карты
        if(t.gameset.equals(this.gameset)&&t.deck.equals(this.deck)&&t.numberOfCard.equals(this.numberOfCard)){
            return true;
        }else{
            return false;
        }
    }


}


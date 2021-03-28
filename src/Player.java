import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player {
    StreamWorker postman;
    public int numberOfPlayer;
    public ArrayList<String> buns = new ArrayList<>();
    public ArrayList<String> doors = new ArrayList<>();
    public int force;
    public int level = 1;
    public int chisOfCardsOnHand;
    public String name;
    public String pol;
    public String clas;
    public int h;
    public int x;
    public int y;

    public Player(StreamWorker s, int n){
        postman = s;
        numberOfPlayer = n;
    }
    public Player(){

    }
    public void sentStateOfPlayer(){
        String all = "";
        String text = "";
        for (int i = 0; i < buns.size(); i = i + 1){
            text = text + " " + buns.get(i);
        }
        all = all + text;
        text = "";
        for (int i = 0; i < doors.size(); i = i + 1){
            text = text + " " + doors.get(i);
        }
        all = "State " + numberOfPlayer + " Buns " + buns.size() + " " + all + " Doors " + doors.size() + " " + text;
        all = all + " " + level + " " + chisOfCardsOnHand + " " + pol;
        postman.sendMessage(all);
    }
    public void drawPlayer(Graphics2D g2d, int x0, int y0, Painter p){
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x0- 180, y0, 160,120 );

        g2d.drawOval(x0-170, y0 + 10, 20, 20);
        g2d.drawString(pol, x0-165, y0 + 25);//Рисует пол персонажа

        g2d.drawString("Cards: " + chisOfCardsOnHand, x0-140, y0 + 15);//Рисует количество карт персонажа
        g2d.drawString("Level: " + level, x0-140, y0 + 30);//Рисует уровень персонажа


        int dx = 0;
        for (int j = 0; j < buns.size(); j = j + 1){
            Card t = new Card("Original", "Buns", buns.get(j));
            t.x = x0 + j * 70;
            t.y = y0;
            t.faseUpIsTrue = 1;
            p.drawSmall(g2d, t.x, t.y, t.gameset, t.deck, t.numberOfCard);
            dx = j + 1;
        }
        for (int j = 0; j < doors.size(); j = j + 1){
            Card t = new Card("Original", "Doors", doors.get(j));
            t.x = x0 + j * 70 + dx * 70;
            t.y = y0;
            t.faseUpIsTrue = 1;
            p.drawSmall(g2d, t.x, t.y, t.gameset, t.deck, t.numberOfCard);
        }
    }

    public Card checkPressed(MouseEvent e,int x0,int y0){
        this.x = x0;
        this.y = y0;
        Card returnCard = null;
        int dx = 0;
        for (int j = 0; j < buns.size(); j = j + 1){
            Card t = new Card("Original", "Buns", buns.get(j));
            t.x = x + j * 70;
            t.y = y;
            t.h = 120;
            t.w = 80;
            if(t.checkClick(e)){
                returnCard = t;
            }
            dx = j + 1;
        }
        for (int j = 0; j < doors.size(); j = j + 1){
            Card t = new Card("Original", "Doors", doors.get(j));
            t.x = x + j * 70 + dx * 70;
            t.y = y;
            t.h = 120;
            t.w = 80;
            if(t.checkClick(e)){
                returnCard = t;
            }
        }
        return returnCard;
    }
}

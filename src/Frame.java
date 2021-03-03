import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Frame extends JFrame implements MouseListener, MouseMotionListener {
    long previousWorldUpdateTime; // Храним здесь момент времени когда физика мира обновлялась в последний раз
    long dt; // Сколько прошло времени с предыдущей отправки
    int minDistance = 30;
    long minTime = 60;

    StreamWorker postman;
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<ArrayList<Card>> states = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    Player player;
    int maxHigth = 0;
    int activeCard = -1;
    int maxHeigthOfThisCards;
    int numberOfClient;
    int lightGrayZone = 150;
    int darkGrayZone = 150;

    boolean isCardNew = false;

    Deck deckOfDoors = new Deck("Doors");
    Deck deckOfBuns = new Deck("Buns");
    Deck deckOfBin = new Deck();


    public Frame() throws IOException {
        setSize(1970, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        deckOfBin.setXY(100, 50);
        deckOfDoors.setXY(200, 50);
        deckOfBuns.setXY(300, 50);

        repaint();
        this.previousWorldUpdateTime = System.currentTimeMillis();
        dt = 1000;

    }

    @Override
    public void paint(Graphics g) {

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();


        Graphics2D g2d = (Graphics2D) g;


        // Отрисовка карт руки
        // Темно-серая зона
        g.setColor(Color.darkGray);
        g.fillRect(0, getHeight() - darkGrayZone, getWidth(), darkGrayZone);
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (cards.get(j).isInDarkGrayZone(getHeight() - darkGrayZone))) {
                    cards.get(j).drawCard(g2d);
                }
            }
        }


        g.setColor(Color.lightGray);
        g.fillRect(0, getHeight() - darkGrayZone - lightGrayZone, getWidth(), lightGrayZone);
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (cards.get(j).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    cards.get(j).drawCard(g2d);
                }
            }
        }


        Color c = new Color(245, 245, 220);
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight() - darkGrayZone - lightGrayZone);


        //Отрисовка статичных элементов
        deckOfDoors.drawDeck(g2d);
        deckOfBuns.drawDeck(g2d);
        deckOfBin.drawDeck(g2d);

        //Отрисовка карт
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (!cards.get(j).isInDarkGrayZone(getHeight() - darkGrayZone)) && (!cards.get(j).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    cards.get(j).drawCard(g2d);
                }
            }
        }



        ///////////////////////////////////////////////////////////////////
        for (int i = 0; i < players.size(); i = i + 1){
            int dx = 0;
            for (int j = 0; j < players.get(i).buns.size(); j = j + 1){
                Card t = new Card("Original", "Buns", players.get(i).buns.get(j));
                t.x = 600 + j * 30;
                t.y = 100 + i * 100;
                t.faseUpIsTrue = 1;
                 System.out.println("hi "+t.numberOfCard+ " "+ t.deck+ " "+ t.gameset+" "+t.x + " "+ t.y );
                t.drawCard(g2d);
                dx = j;
            }
            for (int j = 0; j < players.get(i).doors.size(); j = j + 1){
                Card t = new Card("Original", "Doors", players.get(i).doors.get(j));
                t.x = 600 + j * 30 + dx * 30;
                t.y = 100 + i * 100;
                t.faseUpIsTrue = 1;
                t.drawCard(g2d);
            }
        }


        g.dispose();
        bufferStrategy.show();
    }


    @Override
    public void mouseClicked(MouseEvent e) {


        // Здесь идет проверка на попадание клика на карту и соответсвенно она переворачивается
        maxHeigthOfThisCards = -1;
        int active = -1;
        for (int i = 0; i < cards.size(); i = i + 1) {
            Card test = cards.get(i);
            if ((maxHeigthOfThisCards < cards.get(i).higth) && test.checkClick(e)) {
                maxHeigthOfThisCards = cards.get(i).higth;
                active = i;
            }
        }
        if (active != -1) {
            cards.get(active).faseUpIsTrue = (-1) * cards.get(active).faseUpIsTrue;
            if ((!cards.get(active).isInDarkGrayZone(getHeight() - darkGrayZone)) && (!cards.get(active).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                postman.sendMessage("All " + numberOfClient + " Card " + active + " " + cards.get(active).sentState());
                upTime();
            }
            new Thread(() -> {
                new MakeSound().playSound("C://Users//forStudy//IdeaProjects//data//Munchkin_Sounds//flip.wav");
            }).start();
        }
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Аналогичная проверка на попадание клика
        maxHeigthOfThisCards = -1;
        for (int i = 0; i < cards.size(); i = i + 1) {
            Card test = cards.get(i);
            if ((maxHeigthOfThisCards < cards.get(i).higth) && test.checkClick(e)) {
                maxHeigthOfThisCards = cards.get(i).higth;
            }
        }
        if (maxHeigthOfThisCards != -1) {
            for (int i = 0; i < cards.size(); i = i + 1) {
                if ((activeCard == -1) && (cards.get(i).higth == maxHeigthOfThisCards)) {
                    Card test = cards.get(i);
                    if (test.checkClick(e)) {
                        test.rx = e.getX() - test.x;
                        test.ry = e.getY() - test.y;
                        activeCard = i;
                        maxHigth = maxHigth + 1;
                        cards.get(activeCard).higth = maxHigth;
                        Card t = test;
                        sentStateOfCard(t);
                        upTime();


                    }
                }
            }
        } else {
            if (deckOfDoors.checkClick(e)) {
                isCardNew = true;

                //Здесь мы проверяем клик по колоде и соответственоо создаем новую карту двери

                activeCard = cards.size();
                maxHigth = maxHigth + 1;

                Card test = new Card();
                test.x = deckOfDoors.x;
                test.y = deckOfDoors.y;

                test.gameset = "Original";
                test.deck = "Doors";
                test.numberOfCard = "??";

                test.faseUpIsTrue = -1;
                test.higth = maxHigth;

                test.rx = e.getX() - test.x;
                test.ry = e.getY() - test.y;

                cards.add(test);

                sentStateOfNewCard(test);
                upTime();

                new Thread(() -> {
                    new MakeSound().playSound("C://Users//forStudy//IdeaProjects//data//Munchkin_Sounds//new.wav");
                }).start();
            }
            if (deckOfBuns.checkClick(e)) {
                isCardNew = true;

                //Здесь мы проверяем клик по колоде и соответственоо создаем новую карту сокровищ

                activeCard = cards.size();
                maxHigth = maxHigth + 1;

                Card test = new Card();
                test.x = deckOfBuns.x;
                test.y = deckOfBuns.y;

                test.gameset = "Original";
                test.deck = "Buns";
                test.numberOfCard = "??";

                test.faseUpIsTrue = -1;
                test.higth = maxHigth;

                test.rx = e.getX() - test.x;
                test.ry = e.getY() - test.y;

                cards.add(test);

                sentStateOfNewCard(test);
                upTime();

                new Thread(() -> {
                    new MakeSound().playSound("C://Users//forStudy//IdeaProjects//data//Munchkin_Sounds//new.wav");
                }).start();

            }

        }

        repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if (activeCard != -1) {
            Card test = cards.get(activeCard);
            test.prex = test.x;
            test.prey = test.y;
            test.x = e.getX() - test.rx;
            test.y = e.getY() - test.ry;
            Card t = test;

            dt = System.currentTimeMillis() - previousWorldUpdateTime;
            if ((dt > minTime) || (test.checkDistance() > minDistance)) {
                if ((!t.isInDarkGrayZone(getHeight() - darkGrayZone)) && (!t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    sentStateOfCard(t);
                    upTime();
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (activeCard != -1) {
            if (deckOfBin.checkClick(e)) {
                cards.get(activeCard).x = -1000;
            }
            Card t = cards.get(activeCard);
            sentStateOfCard(t);
            upTime();
            activeCard = -1;
            if(t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone)){
                if(t.deck.equals("Doors")){
                    player.doors.add(t.numberOfCard);
                }
                if(t.deck.equals("Buns")){
                    player.buns.add(t.numberOfCard);
                }
            }
        }
        repaint();
        sentStates();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setPostman(StreamWorker p) {
        // Добавляет почтальона
        postman = p;
    }

    public void setPlayer(StreamWorker p){
        // Создает истиного, реального игрока сидящего за компом
         player = new Player(p , numberOfClient);
    }
    public void newPlayer(StreamWorker p){
        // Добавляет нового "игрока" чтобы мы с него считывали карты
        players.add(new Player(p , numberOfClient));
    }

    public void sentStateOfCard(Card t) {
        if (activeCard != -1) {
            String text = null;
            if ((!t.isInDarkGrayZone(getHeight() - darkGrayZone)) && (!t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                postman.sendMessage("All " + numberOfClient + " Card " + activeCard + " " + t.sentState());
                text = "All " + numberOfClient + " Card " + activeCard + " " + t.sentState();
            }
            System.out.println(text);
        }
    }

    public void sentStateOfNewCard(Card t) {
        // В этом методе клиент осылает неполноценную карту, неизвестнуюю, а сервер ее определит
        if (activeCard != -1) {
            String text = "";
            if ((!t.isInDarkGrayZone(getHeight() - darkGrayZone)) && (!t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                text = "All " + numberOfClient + " newCard " + activeCard + " " + t.sentState();
                postman.sendMessage(text);
            }
            System.out.println(text);
        }
    }
    public void sentStates(){
        // Отсылает карты игрока в светло-серой зоне
       player.sentStateOfPlayer();
    }

    public void upTime() {
        dt = 0;
        previousWorldUpdateTime = System.currentTimeMillis();
    }


}
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
    int minDistance = 10;
    long minTime = 60;

    Painter p = new Painter();
    StreamWorker postman;
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    Player player;
    Player preplayer = new Player();
    Cube cubic;

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

    ClickableObject levelUp;
    ClickableObject levelDown;

    Card stateCard;


    public Frame() throws IOException {
        setSize(1967, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        deckOfBin.setXY(100, 50);
        deckOfDoors.setXY(200, 50);
        deckOfBuns.setXY(300, 50);

        levelUp = new ClickableObject(10, 80, 30, 30, "lv +1");
        levelDown = new ClickableObject(10, 120, 30,30,"lv -1");


        repaint();
        this.previousWorldUpdateTime = System.currentTimeMillis();
        dt = 1000;
        stateCard = null;

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
        g2d.setColor(Color.darkGray);
        g2d.fillRect(0, getHeight() - darkGrayZone, getWidth(), darkGrayZone);
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (cards.get(j).isInDarkGrayZone(getHeight() - darkGrayZone))) {
                    drawCard(g2d, cards.get(j));
                }
            }
        }

        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, getHeight() - darkGrayZone - lightGrayZone, getWidth(), lightGrayZone);
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (cards.get(j).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    drawCard(g2d, cards.get(j));
                }
            }
        }

        Color c = new Color(245, 245, 220);
        g2d.setColor(c);
        g2d.fillRect(0, 0, getWidth(), getHeight() - darkGrayZone - lightGrayZone);

        //Отрисовка статичных элементов
        deckOfDoors.drawDeck(g2d);
        deckOfBuns.drawDeck(g2d);
        deckOfBin.drawDeck(g2d);
        levelUp.drawObject(g2d);
        levelDown.drawObject(g2d);
        if (cubic != null) {
            cubic.drawCube(g2d);
        }
        ///////////////////////////////////////////////////////////////////
        // Происходит отрисовка "рук" всех игроков, шмотки плюшки и т.п.
        for (int i = 0; i < players.size(); i = i + 1) {
            players.get(i).drawPlayer(g2d, 800, 50 + i * 130, p);
        }
        //Отрисовка карт всех остальных
        for (int i = 0; i <= maxHigth; i = i + 1) {
            for (int j = 0; j < cards.size(); j = j + 1) {
                if ((cards.get(j).higth == i) && (!cards.get(j).isInDarkGrayZone(getHeight() - darkGrayZone)) && (!cards.get(j).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    drawCard(g2d, cards.get(j));
                }
            }
        }

        if (stateCard != null) {
            drawMaxCard(g2d, stateCard);
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

        if (e.getButton() == 1) {
            if (active != -1) {
                cards.get(active).faseUpIsTrue = (-1) * cards.get(active).faseUpIsTrue;
                if ((!cards.get(active).isInDarkGrayZone(getHeight() - darkGrayZone)) && (!cards.get(active).isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                    postman.sendMessage("All " + numberOfClient + " Card " + active + " " + cards.get(active).sentState());
                    upTime();
                }
                new Thread(() -> {
                    new MakeSound().playSound("C://Users//forStudy//IdeaProjects//data//Munchkin_Sounds//flip.wav");
                }).start();
            } else {
                if (cubic != null) {
                    cubic.checkClick(e);
                }
            }
        }
        if (levelUp != null){
            if(levelUp.checkClick(e)){
                player.level = player.level + 1;
            }
        }
        if (levelDown != null){
            if(levelDown.checkClick(e)){
                player.level = player.level - 1;
            }
        }
        player.level = Math.max(player.level, 1);

        sentStates();
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
        if (e.getButton() == 3) {
            if (activeCard != -1) {
                Card t = cards.get(activeCard);
                stateCard = new Card("Original", t.deck, t.numberOfCard);
                stateCard.x = e.getX() - 100;
                stateCard.y = e.getY() - 350;
            } else {
                for (int i = 0; i < players.size(); i = i + 1) {
                    if (players.get(i).checkPressed(e, 800, 50 + i * 120) != null) {
                        Card t = players.get(i).checkPressed(e, 800, 20 + i * 130);
                        stateCard = new Card("Original", t.deck, t.numberOfCard);
                        stateCard.x = e.getX() - 100;
                        stateCard.y = e.getY() - 50;
                    }

                }
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
            player.buns.clear();
            player.doors.clear();
            player.chisOfCardsOnHand = 0;

            for (int i = 0; i < cards.size(); i = i + 1) {
                t = cards.get(i);
                if (t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone)) {
                    if (t.deck.equals("Doors")) {
                        player.doors.add(t.numberOfCard);
                    }
                    if (t.deck.equals("Buns")) {
                        player.buns.add(t.numberOfCard);
                    }
                }
                if(cards.get(i).isInDarkGrayZone(getHeight() - darkGrayZone)) {
                    player.chisOfCardsOnHand = player.chisOfCardsOnHand + 1;
                }

            }
        }
        stateCard = null;
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

    public void setCube(StreamWorker p) {
        // Добавляет кубик
        cubic = new Cube(10, 40, p, numberOfClient);
    }

    public void setPlayer(StreamWorker p) {
        // Создает истиного, реального игрока сидящего за компом
        player = new Player(p, numberOfClient);
        player.pol = preplayer.pol;
        player.name = preplayer.name;
    }

    public void newPlayer(StreamWorker p) {
        // Добавляет нового "игрока" чтобы мы с него считывали карты
        players.add(new Player(p, numberOfClient));
    }

    public void sentStateOfCard(Card t) {
        if (activeCard != -1) {
            String text = null;
            if ((!t.isInDarkGrayZone(getHeight() - darkGrayZone)) && (!t.isInLightGrayZone(getHeight() - darkGrayZone - lightGrayZone, getHeight() - darkGrayZone))) {
                postman.sendMessage("All " + numberOfClient + " Card " + activeCard + " " + t.sentState());
                text = "All " + numberOfClient + " Card " + activeCard + " " + t.sentState();
            } else {
                text = "All " + numberOfClient + " Card " + activeCard + " -1000 -1000 " + t.w + " " + t.h + " " + t.gameset + " " + t.deck + " " + t.numberOfCard + " " + t.faseUpIsTrue + " " + t.higth + " " + t.rx + " " + t.ry;
                postman.sendMessage(text);
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

    public void sentStates() {
        // Отсылает карты игрока в светло-серой зоне
        player.sentStateOfPlayer();
    }

    public void upTime() {
        dt = 0;
        previousWorldUpdateTime = System.currentTimeMillis();
    }

    public void drawCard(Graphics2D g2d, Card t) {
        if (t.faseUpIsTrue == -1) {
            p.drawSmall(g2d, t.x, t.y, t.gameset, t.deck, "00");
        } else {
            p.drawSmall(g2d, t.x, t.y, t.gameset, t.deck, t.numberOfCard);
        }
    }

    public void drawMaxCard(Graphics2D g2d, Card t) {
        p.draw(g2d, t.x, t.y, t.gameset, t.deck, t.numberOfCard);
    }


}
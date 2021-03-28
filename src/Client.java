import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Client extends MessageListener {

    Frame frame = new Frame();
    int numberOfClient;
    boolean isItNewCard = false;

    public Client(PreGame p) throws IOException {
        frame.preplayer.name = p.creator.name;
        frame.preplayer.pol = p.creator.pol;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        PreGame preGame = new PreGame();
        while (preGame.isReady == false){
            preGame.repaint();
            Thread.sleep(20);
        }
        Client client = new Client(preGame);
        client.start();
    }

    public void start() throws IOException {

        String host = "127.0.0.1";
        int port = 2491;
        Socket socket = new Socket(host, port);

        StreamWorker postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream());
        postman.addListener(this);
        postman.start();


        frame.setPostman(postman);
        frame.setPlayer(postman);
        frame.setCube(postman);
    }

    @Override
    public void onMessage(String text) throws IOException {
        System.out.println(text);
        StringTokenizer tokenizer = new StringTokenizer(text);


        String token = tokenizer.nextToken();

        if (token.equals("Your_Number")) {
            String number = tokenizer.nextToken();
            numberOfClient = Integer.parseInt(number);
            frame.numberOfClient = numberOfClient;
            System.out.println("Your number in this Server: " + numberOfClient);
            frame.player.numberOfPlayer = numberOfClient;
        }

        if (token.equals("All")) {
            String numberOfClient = tokenizer.nextToken();
            String token0 = tokenizer.nextToken();

            if (token0.equals("Card")) {

                int num;
                String number = tokenizer.nextToken();
                num = Integer.parseInt(number);


                //Положение карты
                String token1 = tokenizer.nextToken();
                String token2 = tokenizer.nextToken();
                String token3 = tokenizer.nextToken();
                String token4 = tokenizer.nextToken();
                //Картинка карты
                String token5 = tokenizer.nextToken();
                String token6 = tokenizer.nextToken();
                String token7 = tokenizer.nextToken();
                String token8 = tokenizer.nextToken();
                //Высота карты
                String token9 = tokenizer.nextToken();
                //Положенеие от курсора
                String token10 = tokenizer.nextToken();
                String token11 = tokenizer.nextToken();


                Card test = new Card(token5, token6, token7);
                test.x = Integer.parseInt(token1);
                test.y = Integer.parseInt(token2);
                test.w = Integer.parseInt(token3);
                test.h = Integer.parseInt(token4);
                test.gameset = token5;
                test.deck = token6;
                test.numberOfCard = token7;
                test.faseUpIsTrue = Integer.parseInt(token8);
                test.higth = Integer.parseInt(token9);
                frame.maxHigth = Integer.parseInt(token9);
                test.rx = Integer.parseInt(token10);
                test.ry = Integer.parseInt(token11);

                if (num < frame.cards.size()) {
                    frame.cards.set(num, test);
                } else {
                    frame.cards.add(test);
                    new Thread(() -> {
                        new MakeSound().playSound("C://Users//forStudy//IdeaProjects//data//Munchkin_Sounds//new.wav");
                    }).start();

                }
            }
        }

        if (token.equals("State")) {
            String numberOfP = tokenizer.nextToken();
            int numberOfPlayer = Integer.parseInt(numberOfP);
            String token0 = tokenizer.nextToken();

            while (numberOfPlayer > frame.players.size()-1) {
                frame.newPlayer(frame.postman);
            }


            if (token0.equals("Buns")) {
                {
                    int kol;
                    String kol_vo = tokenizer.nextToken();
                    kol = Integer.parseInt(kol_vo);

                    frame.players.get(numberOfPlayer).buns.clear();
                    for (int i = 0; i < kol; i = i + 1) {
                        String n = tokenizer.nextToken();
                        frame.players.get(numberOfPlayer).buns.add(n);
                    }
                }


                String token1 = tokenizer.nextToken();
                if (token1.equals("Doors")) {
                    int kol;
                    String kol_vo = tokenizer.nextToken();
                    kol = Integer.parseInt(kol_vo);

                    frame.players.get(numberOfPlayer).doors.clear();
                    for (int i = 0; i < kol; i = i + 1) {
                        String n = tokenizer.nextToken();
                        frame.players.get(numberOfPlayer).doors.add(n);
                    }
                }
                String level = tokenizer.nextToken();
                frame.players.get(numberOfPlayer).level = Integer.parseInt(level);

                String chisOfCardsOnHand = tokenizer.nextToken();
                frame.players.get(numberOfPlayer).chisOfCardsOnHand = Integer.parseInt(chisOfCardsOnHand);

                String pol = tokenizer.nextToken();
                frame.players.get(numberOfPlayer).pol = pol;

            }
            if(token0.equals("Cube")){
                String numberOfFace = tokenizer.nextToken();
                frame.cubic.numberOfFace = numberOfFace;
            }
        }
        frame.repaint();
    }

    @Override
    public void sentCard(Card c) throws IOException {
    }

    @Override
    public void onDisconnect() {

    }
}
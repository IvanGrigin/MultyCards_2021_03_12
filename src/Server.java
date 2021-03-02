import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server extends MessageListener {
    ArrayList<StreamWorker> postmans = new ArrayList<>();
    ArrayList<String> textsOfServer = new ArrayList<>();


    public void run() throws IOException {
        ServerSocket server = new ServerSocket(2391);

        while (true) {
            Socket client = server.accept();
            StreamWorker postman = new StreamWorker(client.getInputStream(), client.getOutputStream());
            postman.addListener(this);
            postman.sendMessage("Welcome, this Server for you");
            postman.sendMessage("Your_Number " + (postmans.size()));
            for (int i = 0; i < textsOfServer.size(); i = i + 1) {
                postman.sendMessage(textsOfServer.get(i));
            }
            postman.start();
            postmans.add(postman);

        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }


    @Override
    public void onMessage(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text);
        int num;
        String forWho = tokenizer.nextToken();
        String tokenNumberOfClient = tokenizer.nextToken();
        int numberOfClient = Integer.parseInt(tokenNumberOfClient);

        String token0 = tokenizer.nextToken();

        if (token0.equals("Card")) {
            String number = tokenizer.nextToken();
            num = Integer.parseInt(number);
            if (num < textsOfServer.size()) {
                textsOfServer.set(num, text);
            } else {
                textsOfServer.add(text);
            }
        } else if (token0.equals("newCard")) {
            String number = tokenizer.nextToken();

            //Положение карты
            String token1 = tokenizer.nextToken();
            String token2 = tokenizer.nextToken();
            String token3 = tokenizer.nextToken();
            String token4 = tokenizer.nextToken();
            //Картинка карты
            String token5 = tokenizer.nextToken();
            String token6 = tokenizer.nextToken();
            String token7 = tokenizer.nextToken();
            int w = (int) (Math.random() * 10) + 1;
            token7 = "" + w;
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
            test.rx = Integer.parseInt(token10);
            test.ry = Integer.parseInt(token11);

            String s = "All -1 Card " + number + " " + test.x + " " + test.y + " " + test.w + " " + test.h + " " + "Original" + " " + test.deck + " " + test.numberOfCard + " -1 " + test.higth + " " + test.rx + " " + test.ry;
            text = s;
            textsOfServer.add(s);

            postmans.get(numberOfClient).sendMessage(text);
        }

        if (forWho.equals("All")) {
            for (int i = 0; i < postmans.size(); i++) {
                if (numberOfClient != (i)) {
                    StreamWorker postman1 = postmans.get(i);
                    postman1.sendMessage(text);
                }
            }
        }

        if (forWho.equals("State")) {
            for (int i = 0; i < postmans.size(); i++) {
                StreamWorker postman1 = postmans.get(i);
                postman1.sendMessage(text);
            }
        }


        System.out.println(text);
    }

    @Override
    public void sentCard(Card c) throws IOException {

    }

    @Override
    public void onDisconnect() {
        System.out.println("Клиент разорвал соединение");
    }


}


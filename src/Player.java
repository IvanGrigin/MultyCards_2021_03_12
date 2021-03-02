import java.util.ArrayList;

public class Player {
    StreamWorker postman;
    public int numberOfPlayer;
    public ArrayList<String> buns = new ArrayList<>();
    public ArrayList<String> doors = new ArrayList<>();
    public int force;
    public int level;
    public int chisOfCardsOnHand;
    public String name;
    public String clas;

    public Player(StreamWorker s, int n){
        postman = s;
        numberOfPlayer = n;
    }

    public void sentBuns(){
        String text = "";
        for (int i = 0; i < buns.size(); i = i + 1){
            text = text + " " + buns.get(i);
        }
        postman.sendMessage("State " + numberOfPlayer + " Buns "+ buns.size() + " " + text);
    }


    public void sentDoors(){
        String text = "";
        for (int i = 0; i < doors.size(); i = i + 1){
            text = text + " " + doors.get(i);
        }
        postman.sendMessage("State " + numberOfPlayer + " Doors "+ doors.size() + " " + text);
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
        postman.sendMessage(all);
    }
}

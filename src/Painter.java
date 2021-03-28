import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Painter {

    String gameset = "Original";
    ArrayList<Image> Buns = new ArrayList<>();
    ArrayList<Image> Doors = new ArrayList<>();
    ArrayList<BufferedImage> Cube = new ArrayList<>();

    Painter() {
        String filePathBuns = "C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin\\" + gameset + "\\" + "Buns_State";
        String filePathDoors = "C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin\\" + gameset + "\\" + "Doors_State";

        try {
            for(int i = 0; i < 10; i = i + 1){
                String s = "00" + i;
                Buns.add(ImageIO.read(new File(filePathBuns + "\\" + s +".jpg")));
            }
            for(int i = 10; i < 76; i = i + 1){
                String s = "0" + i;
                Buns.add(ImageIO.read(new File(filePathBuns + "\\" + s +".jpg")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for(int i = 0; i < 10; i = i + 1){
                String s = "00" + i;
                Doors.add(ImageIO.read(new File(filePathDoors + "\\" + s +".jpg")));
            }
            for(int i = 10; i < 96; i = i + 1){
                String s = "0" + i;
                Doors.add(ImageIO.read(new File(filePathDoors + "\\" + s +".jpg")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_1.bmp")));
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_2.bmp")));
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_3.bmp")));
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_4.bmp")));
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_5.bmp")));
            Cube.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "All_another" + "\\" + "Cube_6.bmp")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, int x0, int y0, String gameset0, String deck, String numberOfCard) {
        if (gameset0.equals("Original")) {
            if (deck.equals("Buns")) {
                // Отрисовывает карты сокровищ
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    Image test = Buns.get(i);
                    g2d.drawImage(test, x0, y0, null);
                }else{
                    Image test = Buns.get(0);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
            if (deck.equals("Doors")) {
                // Отрисовывает карты дверей
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    Image test = Doors.get(i);
                    g2d.drawImage(test, x0, y0, null);
                }else{
                    Image test = Doors.get(0);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
            if (deck.equals("Cube")) {
                // Отрисовывает грани кубика
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    BufferedImage test = Cube.get(i);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
        }

    }
    public void drawSmall(Graphics2D g2d, int x0, int y0, String gameset0, String deck, String numberOfCard) {
        int w0 = 70;
        int h0 = 120;
        if (gameset0.equals("Original")) {
            if (deck.equals("Buns")) {
                // Отрисовывает маленькие карты сокровищ
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    Image test = Buns.get(i).getScaledInstance(w0, h0, Image.SCALE_SMOOTH);
                    g2d.drawImage(test, x0, y0, null);
                }else{
                    Image test = Buns.get(0).getScaledInstance(w0, h0, Image.SCALE_SMOOTH);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
            if (deck.equals("Doors")) {
                // Отрисовывает маленькие карты дверей
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    Image test = Doors.get(i).getScaledInstance(w0, h0, Image.SCALE_SMOOTH);
                    g2d.drawImage(test, x0, y0, null);
                }else{
                    Image test = Doors.get(0).getScaledInstance(w0, h0, Image.SCALE_SMOOTH);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
        }

    }


}

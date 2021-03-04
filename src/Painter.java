import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Painter {

    String gameset = "Original";
    ArrayList<BufferedImage> Buns = new ArrayList<>();
    ArrayList<BufferedImage> Doors = new ArrayList<>();
    ArrayList<BufferedImage> Cube = new ArrayList<>();

    Painter() {
        try {
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "00.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "01.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "02.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "03.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "04.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "05.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "06.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "07.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "08.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "09.bmp")));
            Buns.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Buns" + "\\" + "10.bmp")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "00.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "01.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "02.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "03.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "04.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "05.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "06.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "07.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "08.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "09.bmp")));
            Doors.add(ImageIO.read(new File("C:\\Users\\forStudy\\IdeaProjects\\data\\Munchkin_Cards\\" + gameset + "\\" + "Doors" + "\\" + "10.bmp")));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void draw(Graphics2D g2d, int x0, int y0, String gameset0, String deck, String numberOfCard) {
        if (gameset0.equals("Original")) {
            if (deck.equals("Buns")) {
                if(!numberOfCard.equals("??")) {
                    int i = Integer.parseInt(numberOfCard);
                    BufferedImage test = Buns.get(i);
                    g2d.drawImage(test, x0, y0, null);
                }else{
                    BufferedImage test = Buns.get(0);
                    g2d.drawImage(test, x0, y0, null);
                }

            }
            if (deck.equals("Doors")) {
                if(!numberOfCard.equals("??")) {
                int i = Integer.parseInt(numberOfCard);
                BufferedImage test = Doors.get(i);
                g2d.drawImage(test, x0, y0, null);
                }else{
                    BufferedImage test = Doors.get(0);
                    g2d.drawImage(test, x0, y0, null);
                }
            }
        }

    }


}

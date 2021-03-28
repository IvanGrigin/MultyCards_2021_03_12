import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreatePlayer extends JPanel{
    public ClickableObject polM;
    public ClickableObject polW;
    public String pol = "M";
    public String name = "Ivan";

    public CreatePlayer(){
        polM = new ClickableObject(100, 70, 20, 20, "M");
        polM.c = Color.GREEN;
        polW = new ClickableObject(100, 100, 20, 20, "W");
    }
    public void draw(Graphics2D g2d){
        polM.drawObject(g2d);
        polW.drawObject(g2d);
    }
    public void checkClick(MouseEvent e){
        if(polM.checkClick(e)){
            pol = "M";
            polM.c = Color.GREEN;
            polW.c = Color.WHITE;
        }
        if(polW.checkClick(e)){
            pol = "W";
            polW.c = Color.GREEN;
            polM.c = Color.WHITE;
        }
    }
}

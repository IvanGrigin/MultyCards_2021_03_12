import java.awt.*;
import java.awt.event.MouseEvent;

public class ClickableObject {
    public int x;
    public int y;
    public int w;
    public int h;
    public Color c = Color.WHITE;
    public String s;
    public ClickableObject(int x0, int y0, int w0, int h0, String s0){
        this.x = x0;
        this.y = y0;
        this.w = w0;
        this.h = h0;
        this.s = s0;
    }
    public boolean checkClick(MouseEvent e){
        if ((e.getX() > this.x) && (e.getX() < this.x + this.w) && (e.getY() > this.y) && (e.getY() < this.y + this.h)) {
            return true;
        }else{
            return false;
        }
    }
    public void drawObject(Graphics2D g2d){
        g2d.setColor(c);
        g2d.fillRect(x,y,w,h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x,y,w,h);

        g2d.drawString(s, x + 2, y + ((int)(h/2) + 2));
    }
}

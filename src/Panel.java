import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Panel extends JFrame implements MouseListener, MouseMotionListener {
    ArrayList<Line> lines = new ArrayList<>();
    Point firstPoint;
    Point secondPoint;
    int kol = 0;
    StreamWorker postman;


    public Panel() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public static void drawLine(Line l, Graphics g) {
        g.drawLine(l.firstPoint.x, l.firstPoint.y, l.secondPoint.x, l.secondPoint.y);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) {                               // Если она еще не создана
            createBufferStrategy(2);                                // то создаем ее
            bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)
        g.setColor(getBackground());                                // Выставялем цвет в цвет фона
        g.fillRect(0, 0, getWidth(), getHeight());


        Graphics2D g2d = (Graphics2D) g;

        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i) != null) {
                g2d.setStroke(new BasicStroke(lines.get(i).stroke));
                g.setColor(Color.BLACK);
                drawLine(lines.get(i), g);
            }
        }


        g.dispose();
        bufferStrategy.show();
        kol = lines.size()-1;
        System.out.println("отрисовано "+ kol +" сегментов");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        firstPoint = new Point(e.getX(), e.getY());
        secondPoint = firstPoint;
        Line testline = new Line(firstPoint, secondPoint);


        postman.sendMessage("Segment "
                + testline.firstPoint.x
                + " "
                + testline.firstPoint.y
                + " "
                + testline.secondPoint.x
                + " "
                + testline.secondPoint.y);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        secondPoint = firstPoint;
        firstPoint = new Point(e.getX(), e.getY());
        Line testline = new Line(firstPoint, secondPoint);
        lines.add(testline);

        postman.sendMessage("Segment "
                + testline.firstPoint.x
                + " "
                + testline.firstPoint.y
                + " "
                + testline.secondPoint.x
                + " "
                + testline.secondPoint.y);

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public void setPostman(StreamWorker p){
        postman  = p;
    }

}

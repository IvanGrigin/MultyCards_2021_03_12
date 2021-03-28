import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class PreGame extends JFrame implements MouseListener {
    public CreatePlayer creator;
    public ClickableObject finish;
    public boolean isReady = false;
    public PreGame(){
        setSize(1967, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addMouseListener(this);
        creator = new CreatePlayer();
        finish = new ClickableObject(300, 100, 50, 30,"Finish");
    }
    public void paint(Graphics g){

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0,0,getWidth(), getHeight());
        creator.draw(g2d);
        finish.drawObject(g2d);

        g.dispose();
        bufferStrategy.show();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        creator.checkClick(e);
        isReady = (isReady || finish.checkClick(e));
        if (isReady) {
            setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}

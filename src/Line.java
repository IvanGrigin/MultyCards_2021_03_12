import java.awt.*;

public class Line {
    Point firstPoint;
    Point secondPoint;
    public Color color;
    public int stroke = 2;

    Line(Point firstPoint, Point secondPoint, Color color, int stroke){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.color = new Color((int) (Math.random()*5), (int) (Math.random()*150+50),(int) (Math.random()*250));
        this.stroke = stroke;

    }
    Line(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

    }

}

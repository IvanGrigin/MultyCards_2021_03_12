public class Point {
    int x;
    int y;
    int red;
    int blue;
    int green;


    Point(int x, int y){
        this.x = x;
        this.y = y;
        red = (int) (Math.random()*256);
        green = (int) (Math.random()*256);
        blue = (int) (Math.random()*256);
    }
    Point(){

    }
}


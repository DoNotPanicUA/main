package freshstart.domain;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Coordinates {
    private final int X;
    private final int Y;

    public Coordinates(int x, int y){
        this.X = x;
        this.Y = y;
    }

    public int getX(){
        return this.X;
    }

    public int getY(){
        return this.Y;
    }

}

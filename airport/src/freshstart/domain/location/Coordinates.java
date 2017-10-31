package freshstart.domain.location;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Coordinates {
    private final double X;
    private final double Y;

    public Coordinates(double x, double y){
        this.X = x;
        this.Y = y;
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.Y;
    }

}

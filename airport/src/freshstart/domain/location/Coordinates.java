package freshstart.domain.location;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Coordinates {
    private final double X;
    private final double Y;

    public Coordinates(double x, double y){
        this.X = roundDouble(x, 2);
        this.Y = roundDouble(y, 2);
    }

    private double roundDouble(Double inDouble, int precision){
        return Math.round(Math.pow(10, precision) * inDouble) / Math.pow(10, precision);
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.Y;
    }

}

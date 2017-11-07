package freshstart.domain.location;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Coordinates {
    private double X;
    private double Y;

    public Coordinates(double x, double y){
        this.X = roundDouble(x, 2);
        this.Y = roundDouble(y, 2);
    }

    public Coordinates(Coordinates coordinates){
        this.X = coordinates.getX();
        this.Y = coordinates.getY();
    }

    public Coordinates getCoordinates(){
        return new Coordinates(this);
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

    public void setCoordinates(double x, double y){
        this.X = x;
        this.Y = y;
    }

    public void setCoordinates(Coordinates coordinates){
        this.X = coordinates.getX();
        this.Y = coordinates.getY();
    }
}

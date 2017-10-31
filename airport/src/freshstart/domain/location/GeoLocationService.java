package freshstart.domain.location;


/**
 * Created by DoNotPanic-NB on 31.10.2017.
 */
public class GeoLocationService {

    public static Double calculateDistance(Coordinates coordinatesTo, Coordinates coordinatesFrom){
        return Math.sqrt(Math.pow(coordinatesTo.getX() - coordinatesFrom.getX(), 2)+
                Math.pow(coordinatesTo.getX() - coordinatesFrom.getX(), 2));
    }

    public static Direction getDirection(Coordinates coordinatesTo, Coordinates coordinatesFrom){
        int sideX = coordinatesTo.getX() - coordinatesFrom.getX();
        int sideY = coordinatesTo.getY() - coordinatesFrom.getY();
        double hypo = calculateDistance(coordinatesTo, coordinatesFrom);
        return new Direction().setCoefX(sideX/hypo).setCoefY(sideY/hypo);
    }
}

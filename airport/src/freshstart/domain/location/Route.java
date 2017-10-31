package freshstart.domain.location;

import java.util.Date;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Route {
    private Location destinationFrom;
    private Location destinationTo;
    private Double distance;
    private Date flyDate;
    private Direction direction;

    public Location getDestinationFrom() {
        return destinationFrom;
    }

    public Location getDestinationTo() {
        return destinationTo;
    }

    public Double getDistance() {
        return distance;
    }

    public Date getFlyDate() {
        return flyDate;
    }

    public Direction getDirection() {
        return direction;
    }

    public Route setDestination(Location destinationFrom, Location destinationTo, Date flyDate) {
        this.destinationTo = destinationTo;
        this.destinationFrom = destinationFrom;
        this.flyDate = flyDate;
        GeoLocationService.calculateDistance(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        GeoLocationService.getDirection(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        return this;
    }


}

package freshstart.domain.aircraft;

import freshstart.domain.location.Direction;
import freshstart.domain.location.GeoLocationService;
import freshstart.domain.location.Location;

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
        distance = GeoLocationService.calculateDistance(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        direction = GeoLocationService.getDirection(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        return this;
    }


}

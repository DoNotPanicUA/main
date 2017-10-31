package freshstart.domain;

import freshstart.domain.Location;

/**
 * Created by DoNotPanic-UA on 04.08.2017.
 */
public class Route {
    private Location destinationFrom;
    private Location destinationTo;
    private Double distance;

    public Location getDestinationFrom() {
        return destinationFrom;
    }

    public Location getDestinationTo() {
        return destinationTo;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDestination(Location destinationFrom, Location destinationTo) {
        this.destinationTo = destinationTo;
        this.destinationFrom = destinationFrom;
        calculateDistance();
    }

    private void calculateDistance(){
        distance = Math.sqrt(Math.pow(destinationTo.getCoordinates().getX() - destinationFrom.getCoordinates().getX(), 2)+
                             Math.pow(destinationTo.getCoordinates().getX() - destinationFrom.getCoordinates().getX(), 2));
    }
}

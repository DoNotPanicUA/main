package freshstart.domain.aircraft;

import freshstart.domain.common.PrintService;
import freshstart.domain.common.TimeInMilliSec;
import freshstart.domain.location.Direction;
import freshstart.domain.location.GeoLocationService;
import freshstart.domain.location.Location;

import java.util.Date;

public abstract class Route {
    private String routeName;
    private Location destinationFrom;
    private Location destinationTo;
    private Double distanceKM;
    private Date flyDate;
    private Date arrivingDate;
    private Plane assignedPlane;

    private Direction direction;

    Route(String name){
        this.routeName = name;
    }

    public Location getDestinationFrom() {
        return destinationFrom;
    }

    public Location getDestinationTo() {
        return destinationTo;
    }

    public Double getDistance() {
        return distanceKM;
    }

    public Date getFlyDate() {
        return flyDate;
    }

    public Plane getAssignedPlane() {
        return assignedPlane;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getRouteName() {
        return routeName;
    }

    public Double getDistanceKM() {
        return distanceKM;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    Route setDestination(Location destinationFrom, Location destinationTo) {
        this.destinationTo = destinationTo;
        this.destinationFrom = destinationFrom;
        distanceKM = GeoLocationService.calculateDistance(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        direction = GeoLocationService.getDirection(destinationTo.getCoordinates(), destinationFrom.getCoordinates());
        return this;
    }

    private void predictArriving(Plane plane){
        this.arrivingDate = new Date();
        arrivingDate.setTime(Long.sum(arrivingDate.getTime(), Math.abs(Math.round(Double.sum(1000 * (distanceKM / plane.getPlaneSpeed()), TimeInMilliSec.FIVEMINUTES.getTimeInMilliSecs())))));
    }

    private void setFlyDate(){
        Date timeShift = new Date();
        timeShift.setTime(Long.sum(timeShift.getTime(), TimeInMilliSec.MINUTE.getTimeInMilliSecs()));
        this.flyDate = timeShift;
    }

    public void registerRoute(Plane plane){
        //global registration ++
        this.assignedPlane = plane;
        plane.setRoute(this);
        setFlyDate();
        predictArriving(plane);
        PrintService.printRoute(this);
    }
}

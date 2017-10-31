package freshstart.domain;

import java.util.Date;

/**
 * Created by aleonets on 23.08.2017.
 */
public class Plane extends Thread implements Location{
    private Location currentLocation;
    private Coordinates coordinates;
    private Route route;
    private final String boardName;

    Plane(String boardName, Coordinates coordinates){
        this.boardName = boardName;
        this.coordinates = coordinates;
        PrintService.printMessageObj("The plane has been created.", this);
    }

    void setRoute(Route route){
        this.route = route;
        PrintService.printMessageObj("A new rout from " + route.getDestinationFrom().getObjectName() + " to " +
                route.getDestinationTo().getObjectName() + " has been assigned", this);
    }

    void setCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        PrintService.printMessageObj("The plane is located at the " + currentLocation, this);
    }

    Location getCurrentLocation() {
        return currentLocation;
    }

    void takeOff(){
        setCurrentLocation(null);
        Actions.PLANE_TAKEOFF.doAction();
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getObjectName() {
        return "Plane("+boardName+")";
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void crashPlane(){
        PrintService.printMessageObj("The plane has been crashed!!!", this);
        this.interrupt();
    }

    @Override
    public void run() {
        super.run();
        while (true){
        }
    }
}

package freshstart.domain.aircraft;

import freshstart.domain.airport.Airport;
import freshstart.domain.airport.Airstrip;
import freshstart.domain.airport.RadioTower;
import freshstart.domain.common.Actions;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.*;

import java.util.Date;

/**
 * Created by aleonets on 23.08.2017.
 */
public class Plane extends Thread implements Location {
    private Location currentLocation;
    private Coordinates coordinates;
    private Route route;
    private RadioTower linkedRadioTower;
    private final String boardName;
    private final Double planeSpeed = 0.2 /*Realistic 0.2 km/sec*/;
    private final Actions flyAction = Actions.PLANE_FLYMIN;

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
        Actions.PLANE_TAKEOFF.doAction();
        if (currentLocation instanceof Airstrip){
            ((Airstrip)currentLocation).freeAirstrip();
        }
        setCurrentLocation(null);
    }

    void requestRadioTower(){
        Actions.PLANE_REQUESTRADIOTOWER.doAction();
        if (currentLocation != null & currentLocation instanceof Airstrip){
            linkedRadioTower = ((Airstrip)currentLocation).getAirport().getRadioTower();
            PrintService.printMessageObj("Linked with local Radio Tower" + linkedRadioTower.getObjectName(), this);
        }else if (currentLocation == null & route != null){
            if (route.getDestinationTo() instanceof Airport){
                linkedRadioTower = ((Airport)route.getDestinationTo()).getRadioTower();
                PrintService.printMessageObj("Linked with foreign Radio Tower" + linkedRadioTower.getObjectName(), this);
            }
        }
    }

    void landPlane(){
        requestRadioTower();
        if (linkedRadioTower != null){
            Airstrip requestedAirstrip = linkedRadioTower.requestAirstrip(this);
            if (requestedAirstrip != null){
                Actions.PLANE_LAND.doAction();
                currentLocation = requestedAirstrip;
                setCoordinates(requestedAirstrip.getCoordinates());
                PrintService.printMessageObj("Landed on the "+ currentLocation.getObjectName(), this);
            }
        }
    }


    private void fly(Direction direction){
        flyAction.doAction();
        setCoordinates(new Coordinates(coordinates.getX() + direction.getCoefX() * planeSpeed * flyAction.getDurationSec(),
                                       coordinates.getY() + direction.getCoefY() * planeSpeed * flyAction.getDurationSec()));
    }

    void flyByRoute(){
        if (route != null & route.getFlyDate().before(new Date())){
            while (!currentLocation.getGlobalLocation().equals(route.getDestinationTo().getGlobalLocation())){
                if (currentLocation != null){
                    takeOff();
                }else if (GeoLocationService.calculateDistance(route.getDestinationTo().getCoordinates(), coordinates) > planeSpeed * flyAction.getDurationSec()){
                    fly(route.getDirection());
                }else{
                    landPlane();
                }
            }
        }else{
            Actions.STANDBY.doAction();
        }
    }

    @Override
    public Location getGlobalLocation() {
        return currentLocation.getGlobalLocation();
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        PrintService.printMessageObj("Update coordinates", this);
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
            flyByRoute();
        }
    }
}

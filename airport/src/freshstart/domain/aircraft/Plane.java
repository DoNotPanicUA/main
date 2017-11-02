package freshstart.domain.aircraft;

import freshstart.domain.airport.*;
import freshstart.domain.common.Actions;
import freshstart.domain.common.Named;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.*;

import java.util.Date;

/**
 * Created by aleonets on 23.08.2017.
 */
public class Plane extends Thread implements Named, CoordinateObject {
    private Location currentLocation;
    private Coordinates coordinates;
    private Route route;
    private RadioTower linkedRadioTower;
    private final String boardName;
    private final Double planeSpeed = 0.2 /*Realistic 0.2 km/sec*/;
    private final Actions flyAction = Actions.PLANE_FLYMIN;

    public Plane(String boardName, Location location){
        this.boardName = boardName;
        PrintService.printMessageObj("The plane has been initialized.", this);
        setCurrentLocation(location);
    }

    public void setRoute(Route route){
        this.route = route;
        PrintService.printMessageObj("A new rout from " + route.getDestinationFrom().getObjectName() + " to " +
                route.getDestinationTo().getObjectName() + " has been assigned", this);
    }

    private void setCurrentLocation(Location currentLocation){
        if (this.currentLocation != null & this.currentLocation instanceof PlaneLocation){
            ((PlaneLocation) this.currentLocation).freeLocation();
        }

        if (currentLocation != null & currentLocation instanceof PlaneLocation){
            ((PlaneLocation) currentLocation).reserveLocation(this);
        }

        this.currentLocation = currentLocation;

        if (currentLocation != null){
            setCoordinates(currentLocation.getCoordinates());
            PrintService.printMessageObj("The plane is located at the " + currentLocation.getObjectName(), this);
        }else{
            PrintService.printMessageObj("The plane is in the sky", this);
        }

    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public Coordinates getCoordinates() {
        return (this.coordinates != null ? new Coordinates(this.coordinates.getX(), this.coordinates.getY()) : null);
    }

    private void groundMoveTo(AirportObjects destinationObject){
        PlaneLocation resultObject = null;

        if (requestRadioTower()){
            while (resultObject == null){
                resultObject = linkedRadioTower.requestPlaneLocation(this, destinationObject);
                if (resultObject != null){
                    Actions.PLANE_GROUNDMOVE.doAction();
                    PrintService.printMessageObj("Move to the "+ resultObject.getObjectName(), this);
                    setCurrentLocation(resultObject);
                }else{
                    Actions.STANDBY.doAction();
                }
            }
        }
    }

    private void moveToAirstrip(){
        if (!(this.currentLocation instanceof Airstrip)){
            groundMoveTo(AirportObjects.AIRSTRIP);
        }
    }

    private void moveToParking(){
        if (!(this.currentLocation instanceof PlaneParkingPlace )){
            groundMoveTo(AirportObjects.PLANEPARKINGPLACE);
        }
    }


    private void takeOff(){
        moveToAirstrip();

        if (currentLocation instanceof Airstrip){
            Actions.PLANE_TAKEOFF.doAction();
            setCurrentLocation(null);
        }
    }

    private boolean requestRadioTower(){
        Actions.PLANE_REQUESTRADIOTOWER.doAction();
        if (currentLocation != null & currentLocation instanceof ChildLocation){
            if (((ChildLocation) currentLocation).getParentLocation() instanceof Airport) {
                linkedRadioTower = ((ChildLocation<Airport>) currentLocation).getParentLocation().getRadioTower();
                PrintService.printMessageObj("Linked with local Radio Tower" + linkedRadioTower.getObjectName(), this);
            }
        }else if(currentLocation != null & currentLocation instanceof Airport){
            linkedRadioTower = ((Airport) currentLocation).getRadioTower();
            PrintService.printMessageObj("Linked with local Radio Tower" + linkedRadioTower.getObjectName(), this);
        }else if (currentLocation == null & route != null){
            if (route.getDestinationTo() instanceof Airport){
                linkedRadioTower = ((Airport)route.getDestinationTo()).getRadioTower();
                PrintService.printMessageObj("Linked with foreign Radio Tower" + linkedRadioTower.getObjectName(), this);
            }
        }
        if (linkedRadioTower == null){
            PrintService.printMessageObj("An radio tower doesn't reply.", this);
        }
        return (linkedRadioTower != null);
    }

    private void landPlane(){
        if (requestRadioTower()){
            Airstrip requestedAirstrip = null;
            while (requestedAirstrip == null){
                requestedAirstrip = linkedRadioTower.requestPlaneLocation(this, AirportObjects.AIRSTRIP);
                if (requestedAirstrip != null){
                    Actions.PLANE_LAND.doAction();
                    PrintService.printMessageObj("Landed on the "+ requestedAirstrip.getObjectName(), this);
                    setCurrentLocation(requestedAirstrip);

                    moveToParking();
                }else{
                    Actions.STANDBY.doAction();
                }
            }
        }
    }

    private void fly(Direction direction){
        flyAction.doAction();
        setCoordinates(new Coordinates(coordinates.getX() + direction.getCoefX() * planeSpeed * flyAction.getDurationSec(),
                                       coordinates.getY() + direction.getCoefY() * planeSpeed * flyAction.getDurationSec()));
    }

    private boolean compareGlobalLocations(Location firstLocation, Location secondLocation){
        Location firstGlobalLocation;
        Location secondGlobalLocation;
        if (firstLocation instanceof ChildLocation){
            firstGlobalLocation = ((ChildLocation)firstLocation).getParentLocation();
        }else{
            firstGlobalLocation = firstLocation;
        }

        if (secondLocation instanceof ChildLocation){
            secondGlobalLocation = ((ChildLocation)secondLocation).getParentLocation();
        }else{
            secondGlobalLocation = secondLocation;
        }

        return (firstGlobalLocation.equals(secondGlobalLocation));
    }

    private void flyByRoute(){
        if (route != null & route.getFlyDate().before(new Date())){
            while (currentLocation == null || !compareGlobalLocations(currentLocation, route.getDestinationTo())){
                if (currentLocation != null){
                    takeOff();
                }else if (Math.abs(GeoLocationService.calculateDistance(route.getDestinationTo().getCoordinates(), coordinates)) > Math.abs(planeSpeed * flyAction.getDurationSec())){
                    fly(route.getDirection());
                }else{
                    landPlane();
                }
            }
        }else{
            Actions.STANDBY.doAction();
        }
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
    public void run() {
        super.run();
        while (true){
            flyByRoute();
        }
    }
}

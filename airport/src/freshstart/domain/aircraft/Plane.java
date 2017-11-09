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
public class Plane implements Named, CoordinateObject, Runnable {
    private Location currentLocation;
    private Coordinates coordinates;
    private Route route;
    private RadioTower linkedRadioTower;
    private final String boardName;
    private final Double planeSpeed = 0.2 /*Realistic 0.2 km/sec*/;
    private final Actions flyAction = Actions.PLANE_FLYMIN;

    Plane(String boardName){
        this.boardName = boardName;
        PrintService.printMessageObj("The plane has been initialized.", this);
        //setCurrentLocation(location);
    }

    public void setRoute(Route route){
        this.route = route;
        PrintService.printMessageObj("A new rout from " + route.getDestinationFrom().getObjectName() + " to " +
                route.getDestinationTo().getObjectName() + " has been assigned", this);
    }

    void setCurrentLocation(Location currentLocation){
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
        return (this.coordinates != null ? this.coordinates.getCoordinates() : null);
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
                    setCurrentLocation(requestedAirstrip);
                    PrintService.printMessageObj("Landed on the "+ requestedAirstrip.getObjectName(), this);

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

    private Location getGlobalLocation(Location location){
        if (location != null){
            return (location instanceof ChildLocation ? ((ChildLocation)location).getParentLocation() : location);
        }else{
            return null;
        }
    }

    private boolean compareGlobalLocations(Location firstLocation, Location secondLocation){
        return (getGlobalLocation(firstLocation).equals(getGlobalLocation(secondLocation)));
    }

    private void flyByRoute(){
        if (route.getFlyDate().before(new Date())){
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

    void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        PrintService.printMessageObj("Update coordinates", this);
    }

    public double getPlaneSpeed(){
        return this.planeSpeed;
    }

    public Airport getCurrentAirport(){
        Location loc;
        Airport air = null;
        if (currentLocation != null){
            loc = getGlobalLocation(currentLocation);
            if (loc instanceof Airport){
                air = (Airport)loc;
            }
        }
        return air;
    }

    private void requestRoute(){
        if (this.route == null & currentLocation != null){
            getCurrentAirport().getPlaneService().requestRoute(this);
        }
    }

    @Override
    public String getObjectName() {
        return "Plane("+boardName+")";
    }

    @Override
    public void run() {
        while (true){
            if (this.route != null){
                flyByRoute();
            }else{
                requestRoute();
                Actions.STANDBY.doAction();
            }
        }
    }
}

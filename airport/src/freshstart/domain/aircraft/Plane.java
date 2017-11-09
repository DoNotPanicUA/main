package freshstart.domain.aircraft;

import freshstart.domain.airport.*;
import freshstart.domain.common.Actions;
import freshstart.domain.common.Named;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.*;

import java.util.Date;

public class Plane implements Named, CoordinateObject, Runnable {
    private AirportLocation currentAirportLocation;
    private Coordinates coordinates;
    private Route route;
    private RadioTower linkedRadioTower;
    private final String boardName;
    private final Double planeSpeed = 0.2 /*Realistic 0.2 km/sec*/;
    private final Actions flyAction = Actions.PLANE_FLYMIN;

    Plane(String boardName){
        this.boardName = boardName;
        PrintService.printMessageObj("The plane has been initialized.", this);
        //setCurrentAirportLocation(location);
    }

    public void setRoute(Route route){
        this.route = route;
        PrintService.printMessageObj("A new rout from " + route.getDestinationFrom().getObjectName() + " to " +
                route.getDestinationTo().getObjectName() + " has been assigned", this);
    }

    public Route getCurrentRoute(){
        return this.route;
    }

    void setCurrentAirportLocation(AirportLocation currentAirportLocation){
        if (this.currentAirportLocation != null & this.currentAirportLocation instanceof PlaneAirportLocation){
            ((PlaneAirportLocation) this.currentAirportLocation).freeLocation();
        }

        if (currentAirportLocation != null & currentAirportLocation instanceof PlaneAirportLocation){
            ((PlaneAirportLocation) currentAirportLocation).takeLocation(this);
        }

        this.currentAirportLocation = currentAirportLocation;

        if (currentAirportLocation != null){
            setCoordinates(currentAirportLocation.getCoordinates());
            PrintService.printMessageObj("The plane is located at the " + currentAirportLocation.getObjectName(), this);
        }else{
            PrintService.printMessageObj("The plane is in the sky", this);
        }

    }

    public AirportLocation getCurrentAirportLocation() {
        return currentAirportLocation;
    }

    @Override
    public Coordinates getCoordinates() {
        return (this.coordinates != null ? this.coordinates.getCoordinates() : null);
    }

    private void groundMoveTo(AirportObjects destinationObject){
        PlaneAirportLocation resultObject = null;

        if (requestRadioTower()){
            while (resultObject == null){
                resultObject = linkedRadioTower.requestPlaneLocation(this, destinationObject);
                if (resultObject != null){
                    Actions.PLANE_GROUNDMOVE.doAction();
                    PrintService.printMessageObj("Move to the "+ resultObject.getObjectName(), this);
                    setCurrentAirportLocation(resultObject);
                }else{
                    Actions.STANDBY.doAction();
                }
            }
        }
    }

    private void moveToAirstrip(){
        if (!(this.currentAirportLocation instanceof Airstrip)){
            groundMoveTo(AirportObjects.AIRSTRIP);
        }
    }

    private void moveToParking(){
        if (!(this.currentAirportLocation instanceof PlaneParkingPlace )){
            groundMoveTo(AirportObjects.PLANEPARKINGPLACE);
        }
    }


    private void takeOff(){
        moveToAirstrip();

        if (currentAirportLocation instanceof Airstrip){
            Actions.PLANE_TAKEOFF.doAction();
            setCurrentAirportLocation(null);
        }
    }

    private boolean requestRadioTower(){
        Actions.PLANE_REQUESTRADIOTOWER.doAction();
        if (currentAirportLocation != null){
            linkedRadioTower = currentAirportLocation.getAirport().getRadioTower();
            PrintService.printMessageObj("Linked with local Radio Tower" + linkedRadioTower.getObjectName(), this);
        }else if (route != null){
            linkedRadioTower = route.getDestinationTo().getAirport().getRadioTower();
            PrintService.printMessageObj("Linked with foreign Radio Tower" + linkedRadioTower.getObjectName(), this);
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
                    setCurrentAirportLocation(requestedAirstrip);
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

    private boolean compareAirportLocations(AirportLocation firstAirportLocation, AirportLocation secondAirportLocation){
        return (firstAirportLocation.getAirport().equals(secondAirportLocation.getAirport()));
    }

    private void finishRoute(){
        PrintService.printMessageObj(this.getObjectName() + " finish the route(" + route.getRouteName() + ")", this);
        route = null;
    }

    private void flyByRoute(){
        if (route.getFlyDate().before(new Date())){
            while (currentAirportLocation == null || !compareAirportLocations(currentAirportLocation, route.getDestinationTo())){
                if (currentAirportLocation != null){
                    takeOff();
                }else if (Math.abs(GeoLocationService.calculateDistance(route.getDestinationTo().getCoordinates(), coordinates)) > Math.abs(planeSpeed * flyAction.getDurationSec())){
                    fly(route.getDirection());
                }else{
                    landPlane();
                    finishRoute();
                    return;
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

    private void requestRoute(){
        if (this.route == null & currentAirportLocation != null){
            currentAirportLocation.getAirport().getRadioTower().requestPlaneRoute(this);
        }
    }

    @Override
    public String getObjectName() {
        return "Plane("+boardName+")";
    }

    public void crashPlane(){
        Thread.currentThread().interrupt();
        PrintService.printMessageObj(this.getObjectName() + " is crashed!!!", this);
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

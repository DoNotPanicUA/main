package freshstart.domain.airport;

import freshstart.domain.aircraft.Plane;
import freshstart.domain.common.Actions;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.*;
import freshstart.domain.location.AirportLocation;
import freshstart.domain.location.PlaneAirportLocation;

public class RadioTower implements AirportLocation {
    private Airport linkedAirport;
    private Coordinates coordinates;

    RadioTower setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public Airport getAirport() {
        return this.linkedAirport;
    }

    @Override
    public String getObjectName() {
        return "Radio Tower(" + linkedAirport.getAirportName()+ ")";
    }

    @Override
    public Coordinates getCoordinates() {
        return (this.coordinates != null ? this.coordinates.getCoordinates() : null);
    }

    RadioTower(Airport airport){
        this.linkedAirport = airport;
    }

    void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    public <T extends PlaneAirportLocation> T requestPlaneLocation(Plane plane, AirportObjects requestedObject){
        T resultObject = null;

        if (plane.getCurrentRoute() != null && !linkedAirport.checkRouteRegistration(plane.getCurrentRoute())){
            PrintService.printMessageObj("Route("+plane.getCurrentRoute().getRouteName()+") is not registered!", this);
            return null;
        }

        if (requestedObject == AirportObjects.AIRSTRIP){
            PrintService.printMessageObj(plane.getObjectName() +" has requested an airstrip!", this);
            Actions.RADIOTOWER_REQUEST.doAction();
            resultObject = (T)linkedAirport.getPlaneService().requestAirstrip(plane);
        }else if (requestedObject == AirportObjects.PLANEPARKINGPLACE){
            PrintService.printMessageObj(plane.getObjectName() +" has requested a parking place!", this);
            Actions.RADIOTOWER_REQUEST.doAction();
            resultObject = (T)linkedAirport.getPlaneService().requestParking(plane);
        }

        if (resultObject == null){
            PrintService.printMessageObj("There is not a free requested location for the " + plane.getObjectName() +"!", this);
        }

        return resultObject;
    }

    public void requestPlaneRoute(Plane plane){
        linkedAirport.getPlaneService().requestRoute(plane);
    }

}

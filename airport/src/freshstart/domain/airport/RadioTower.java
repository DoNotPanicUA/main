package freshstart.domain.airport;

import freshstart.domain.aircraft.Plane;
import freshstart.domain.common.Actions;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.ChildLocation;
import freshstart.domain.location.Coordinates;
import freshstart.domain.location.Location;
import freshstart.domain.location.PlaneLocation;

import java.util.Iterator;

/**
 * Created by aleonets on 21.08.2017.
 */
public class RadioTower implements Location, ChildLocation<Airport> {
    private Airport linkedAirport;
    private Coordinates coordinates;

    RadioTower setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public Airport getParentLocation() {
        return linkedAirport;
    }

    @Override
    public Coordinates getParentCoordinates() {
        return linkedAirport.getCoordinates();
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

    public void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    public <T extends PlaneLocation> T requestPlaneLocation(Plane plane, AirportObjects requestedObject){
        T resultObject = null;

        if (requestedObject == AirportObjects.AIRSTRIP){
            PrintService.printMessageObj(plane.getObjectName() +" has requested an airstrip!", this);
            Actions.RADIOTOWER_REQUEST.doAction();
            resultObject = (T)linkedAirport.getPlaneService().requestAirstrip();
        }else if (requestedObject == AirportObjects.PLANEPARKINGPLACE){
            PrintService.printMessageObj(plane.getObjectName() +" has requested a parking place!", this);
            Actions.RADIOTOWER_REQUEST.doAction();
            resultObject = (T)linkedAirport.getPlaneService().requestParking();
        }

        if (resultObject == null){
            PrintService.printMessageObj("There is not a free requested location for the " + plane.getObjectName() +"!", this);
        }else{
            PrintService.printMessageObj(resultObject.getObjectName()+" is reserved for the "+ plane.getObjectName() +"!", this);
        }

        return resultObject;
    }

}

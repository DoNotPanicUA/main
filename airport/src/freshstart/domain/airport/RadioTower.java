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

    @Override
    public void setCoordinates(Coordinates coordinates){
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
        return linkedAirport.getCoordinates();
    }

    public RadioTower(Airport airport){
        this.linkedAirport = airport;
        PrintService.printMessageObj("The radio tower is online!", this);
    }

    public void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    public synchronized <T extends PlaneLocation> T requestPlaneLocation(Plane plane, AirportObjects requestedObject){
        T resultObject = null;
        Iterator iterator = null;

        if (requestedObject == AirportObjects.AIRSTRIP){
            iterator = linkedAirport.getAirstrips().iterator();
            PrintService.printMessageObj(plane.getObjectName() +" has requested an airstrip!", this);
        }else if (requestedObject == AirportObjects.PLANEPARKINGPLACE){
            iterator = linkedAirport.getParkingPlaces().iterator();
            PrintService.printMessageObj(plane.getObjectName() +" has requested a parking place!", this);
        }

        Actions.RADIOTOWER_REQUEST.doAction();

        if (iterator != null){
            while (iterator.hasNext() & resultObject == null){
                T planeLocation = (T)iterator.next();

                if (planeLocation.checkIsLocationFree()){
                    //planeLocation.reserveLocation(plane);
                    resultObject = planeLocation;
                }
            }
        }

        if (resultObject == null){
            PrintService.printMessageObj("There is not a free requested location for the " + plane.getObjectName() +"!", this);
        }else{
            PrintService.printMessageObj(resultObject.getObjectName()+" is reserved for the "+ plane.getObjectName() +"!", this);
        }

        return resultObject;
    }

}

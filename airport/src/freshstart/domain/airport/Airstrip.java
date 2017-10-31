package freshstart.domain.airport;

import freshstart.domain.location.Coordinates;
import freshstart.domain.location.Location;
import freshstart.domain.aircraft.Plane;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Airstrip implements Location {
    private Plane currentPlane;
    private final Airport airport;

    @Override
    public Location getGlobalLocation() {
        return airport;
    }

    @Override
    public void setCoordinates(Coordinates coordinates){
    }

    @Override
    public String getObjectName() {
        return "Airstrip(" + airport.getAirportName()+ ")";
    }

    @Override
    public Coordinates getCoordinates() {
        return airport.getCoordinates();
    }


    Airstrip(Airport airport){
        this.airport = airport;
    }

    public Airport getAirport() {
        return airport;
    }

    public void freeAirstrip(){
        this.currentPlane = null;
    }

    synchronized void reserveAirship(Plane plane){
        if (currentPlane == null){
            currentPlane = plane;
        }
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    boolean checkIsAirstripFree(){
        return (currentPlane == null);
    }
}

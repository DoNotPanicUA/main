package freshstart.domain.location;

import freshstart.domain.aircraft.Plane;
import freshstart.domain.airport.Airport;
import freshstart.domain.common.PrintService;

/**
 * Created by DoNotPanic-NB on 01.11.2017.
 */
public abstract class PlaneAirportLocation implements AirportLocation{

    private Plane reservedByPlane;
    private Plane currentPlane;
    private final Airport airport;
    private Coordinates coordinates;
    private final String objectName;

    public PlaneAirportLocation(Airport airport, String name){
        this.airport = airport;
        this.objectName = name;
        setCoordinates(airport.getCoordinates());
    }

    PlaneAirportLocation setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public Airport getAirport() {
        return this.airport;
    }

    @Override
    public String getObjectName() {
        return this.objectName;
    }

    @Override
    public Coordinates getCoordinates() {
        return (this.coordinates != null ? this.coordinates.getCoordinates() : null);
    }

    public boolean reserveLocation(Plane plane){
        if (this.reservedByPlane == null || this.reservedByPlane.equals(plane)){
            this.reservedByPlane = plane;
            PrintService.printMessageObj(this.getObjectName()+" is reserved for the "+ plane.getObjectName() +"!", this);
            return true;
        }else{
            return false;
        }
    }

    private void freeReserveLocation(){
        this.reservedByPlane = null;
    }

    public synchronized void takeLocation(Plane plane){
        if (this.currentPlane == null){
            this.currentPlane = plane;
        }else if (!this.currentPlane.equals(plane)){
            plane.crashPlane();
            this.currentPlane.crashPlane();
        }
    }

    public void freeLocation(){
        this.currentPlane = null;
        freeReserveLocation();
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    public boolean checkIsLocationFree(){
        return (this.reservedByPlane == null);
    }

}

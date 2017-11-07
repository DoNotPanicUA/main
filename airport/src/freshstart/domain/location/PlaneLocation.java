package freshstart.domain.location;

import freshstart.domain.aircraft.Plane;
import freshstart.domain.airport.Airport;

/**
 * Created by DoNotPanic-NB on 01.11.2017.
 */
public abstract class PlaneLocation<T extends Location> implements Location, ChildLocation<T> {

    private Plane currentPlane;
    private final T globalLocation;
    private Coordinates coordinates;
    private final String objectName;

    public PlaneLocation(T globalLocation, String name){
        this.globalLocation = globalLocation;
        this.objectName = name;
        setCoordinates(globalLocation.getCoordinates());
    }

    PlaneLocation setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
        return this;
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
        if (this.currentPlane == null || currentPlane.equals(plane)){
            currentPlane = plane;
            return true;
        }else{
            return false;
        }
    }

    public void freeLocation(){
        this.currentPlane = null;
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    public boolean checkIsLocationFree(){
        return (currentPlane == null);
    }

    @Override
    public T getParentLocation() {
        return globalLocation;
    }

    @Override
    public Coordinates getParentCoordinates() {
        return globalLocation.getCoordinates();
    }
}

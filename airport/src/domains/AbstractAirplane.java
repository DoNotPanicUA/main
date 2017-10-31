package domains;

import freshstart.domain.Location;
import freshstart.domain.Route;

/**
 * Created by Red8 on 04/08/2017.
 */
public abstract class AbstractAirplane {
    private Location currentLocation;
    private boolean isFueled;
    private Route currentRoute;
    private Double maxSpeed;

    // Public
    public void refuelPlane(){
        isFueled = true;
    }

    public abstract void takeOff();

    public abstract <T extends PlaneLandableLocation> void toLand(T location);

    // Private
    private void setLocation(Location location){
        this.currentLocation = location;
    }

    private Location getCurrentLocation(){
        return this.currentLocation;
    }
}
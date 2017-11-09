package domains;

import freshstart.domain.location.AirportLocation;
import freshstart.domain.aircraft.Route;

/**
 * Created by Red8 on 04/08/2017.
 */
public abstract class AbstractAirplane {
    private AirportLocation currentAirportLocation;
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
    private void setLocation(AirportLocation airportLocation){
        this.currentAirportLocation = airportLocation;
    }

    private AirportLocation getCurrentAirportLocation(){
        return this.currentAirportLocation;
    }
}
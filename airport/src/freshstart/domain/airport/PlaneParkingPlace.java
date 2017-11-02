package freshstart.domain.airport;

import freshstart.domain.location.PlaneLocation;

/**
 * Created by aleonets on 21.08.2017.
 */
public class PlaneParkingPlace extends PlaneLocation {

    public PlaneParkingPlace(Airport airport, int number){
        super(airport, "PlaneParking #" + number + " (" + airport.getAirportName() + ")");
    }
}

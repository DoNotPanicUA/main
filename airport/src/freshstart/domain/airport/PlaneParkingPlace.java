package freshstart.domain.airport;

import freshstart.domain.location.PlaneAirportLocation;

/**
 * Created by aleonets on 21.08.2017.
 */
public class PlaneParkingPlace extends PlaneAirportLocation {

    PlaneParkingPlace(Airport airport, int number){
        super(airport, "PlaneParking #" + (number+1) + " (" + airport.getAirportName() + ")");
    }
}

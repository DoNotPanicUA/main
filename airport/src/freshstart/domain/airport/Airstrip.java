package freshstart.domain.airport;

import freshstart.domain.location.PlaneAirportLocation;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Airstrip extends PlaneAirportLocation {

    public Airstrip(Airport airport, int number){
        super(airport, "Airstrip #" + (number+1) + " (" + airport.getAirportName() + ")");
    }
}

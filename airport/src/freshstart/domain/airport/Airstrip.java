package freshstart.domain.airport;

import freshstart.domain.location.PlaneLocation;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Airstrip extends PlaneLocation {

    public Airstrip(Airport airport, int number){
        super(airport, "Airstrip #" + number + " (" + airport.getAirportName() + ")");
    }
}

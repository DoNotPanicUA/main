package freshstart.domain.location;

import freshstart.domain.airport.Airport;
import freshstart.domain.common.Named;

public interface AirportLocation extends Named, CoordinateObject {
    Airport getAirport();

}

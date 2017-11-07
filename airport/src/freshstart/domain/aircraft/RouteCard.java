package freshstart.domain.aircraft;

import freshstart.domain.location.Location;

import java.util.Date;

/**
 * Created by DoNotPanic-NB on 06.11.2017.
 */
public class RouteCard extends Route {
    @Override
    public RouteCard setDestination(Location destinationFrom, Location destinationTo, Date flyDate) {
        super.setDestination(destinationFrom, destinationTo, flyDate);
        return this;
    }

}

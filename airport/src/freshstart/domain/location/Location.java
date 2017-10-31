package freshstart.domain.location;

import freshstart.domain.common.Named;

/**
 * Created by Red8 on 04/08/2017.
 */
public interface Location extends Named {

    Location getGlobalLocation();
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
}

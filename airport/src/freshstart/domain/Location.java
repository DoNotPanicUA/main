package freshstart.domain;

import freshstart.domain.Coordinates;

/**
 * Created by Red8 on 04/08/2017.
 */
public interface Location extends Named {

    Coordinates getCoordinates();
}

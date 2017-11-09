package domains;

import freshstart.domain.location.AirportLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoNotPanic-UA on 07.08.2017.
 */
public abstract class AbstractAirport implements AirportLocation {
    private RadioTower radioTower;
    private List<Runway> runways = new ArrayList<>();

    public void setRadioTower(RadioTower radioTower){
        this.radioTower = radioTower;
    }

    public RadioTower getRadioTower() {
        return radioTower;
    }
}

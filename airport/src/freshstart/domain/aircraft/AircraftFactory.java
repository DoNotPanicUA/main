package freshstart.domain.aircraft;

import freshstart.domain.airport.Airport;
import freshstart.domain.airport.AirportObjects;

public class AircraftFactory {
    private AircraftFactory(){}

    public static Plane getInstancePlane(String name, Airport airport){
        Plane newPlane = new Plane(name);
        newPlane.setCurrentAirportLocation(airport.getRadioTower().requestPlaneLocation(newPlane, AirportObjects.PLANEPARKINGPLACE));

        new Thread(newPlane).start();
        return newPlane;
    }
}

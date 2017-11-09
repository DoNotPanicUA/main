package freshstart;

import freshstart.domain.aircraft.AircraftFactory;
import freshstart.domain.aircraft.Plane;
import freshstart.domain.aircraft.RouteFactory;
import freshstart.domain.airport.Airport;
import freshstart.domain.airport.AirportFactory;
import freshstart.domain.common.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    private static List<Airport> knownAirports;
    private static List<Plane> knownPlanes;
    public static void main(String[] args){
        knownAirports = createAirports();

        /*Wait for airports initialization*/
        Actions.STANDBY_5SEC.doAction();

        knownPlanes = createPlanes();
    }

    private static List<Airport> createAirports(){
        List<Airport> airports = new ArrayList<>();

        airports.add(AirportFactory.getInstanceAirport("Borispol"));
        airports.add(AirportFactory.getInstanceAirport("John F. Kennedy"));
        airports.add(AirportFactory.getInstanceAirport("Heathrow"));
        airports.add(AirportFactory.getInstanceAirport("Seattle-Tacoma"));

        for (Airport airport : airports){
            airport.setLinkedAirports(airports);
        }

        return airports;
    }

    public static Airport getRandomKnownAirport(){
        Airport result = null;

        int randInt = new Random().nextInt(knownAirports.size());

        if (randInt >= 0){
            result = knownAirports.get(randInt);
        }

        return  result;
    }

    private static List<Plane> createPlanes(){
        List<Plane> planes = new ArrayList<>();
        Plane newPlane = AircraftFactory.getInstancePlane("APEK-005", knownAirports.get(0));
        //RouteFactory.requestRouteFrom(knownAirports.get(0), newPlane);
        planes.add(newPlane);

        newPlane = AircraftFactory.getInstancePlane("FANS-741", knownAirports.get(0));
        //RouteFactory.requestRouteFrom(knownAirports.get(0), newPlane);
        planes.add(newPlane);

        newPlane = AircraftFactory.getInstancePlane("KARD-926", knownAirports.get(0));
        //RouteFactory.requestRouteFrom(knownAirports.get(0), newPlane);
        planes.add(newPlane);

        return planes;
    }
}

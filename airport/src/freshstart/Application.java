package freshstart;

import freshstart.domain.airport.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Application {
    private static List<Airport> knownAirports;
    public static void main(String[] args){
        knownAirports = createAirports();
    }

    private static List<Airport> createAirports(){
        List<Airport> airports = new ArrayList<>();

        airports.add(new Airport("Borispol"));
        airports.add(new Airport("Kyiv"));
        airports.add(new Airport("Hitrow"));

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
}

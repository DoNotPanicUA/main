package freshstart.domain.airport;

import freshstart.domain.common.PrintService;
import freshstart.domain.location.Coordinates;

import java.util.Random;

/**
 * Created by DoNotPanic-NB on 03.11.2017.
 */
public class AirportFactory {
    /*private constructor*/
    private AirportFactory(){}

    public static Airport getInstanceAirport(String name){
        Airport newAirport = new Airport(name);
        iniAirport(newAirport, -1, -1);

        return newAirport;
    }

    public static Airport getInstanceAirportDetailed(String name, int numAirstrips, int numParkingPlaces){
        Airport newAirport = new Airport(name);
        iniAirport(newAirport, (numAirstrips >= 1 ? numAirstrips : 1), (numParkingPlaces >= 1 ? numParkingPlaces : 1));

        return newAirport;
    }

    private static void startAirport(Airport airport){
        new Thread(airport).start();
        PrintService.printMessageObj("The airport is online.", airport);
    }

    private static void iniAirport(Airport airport, int numAirstrips, int numParkingPlaces){
        if (airport.getCoordinates() == null){
            airport.setCoordinates(new Coordinates(new Random().nextInt(100),new Random().nextInt(100)));
        }

        int AirstripsCount = (numAirstrips == -1 ? new Random().nextInt(2)+1 : numAirstrips);
        int ParkingCount = (numParkingPlaces == -1 ? new Random().nextInt(AirstripsCount*10)+AirstripsCount : numParkingPlaces);

        int i = 0;
        while (i < AirstripsCount || i < ParkingCount) {
            if (i < AirstripsCount) {
                airport.addAirstrip(new Airstrip(airport, i));
            }

            if (i < ParkingCount) {
                airport.addParkingPlace(new PlaneParkingPlace(airport, i));
            }
            i++;
        }

        PrintService.printMessageObj("The airport is initialised.", airport);
        airport.setRadioTower(new RadioTower(airport).setCoordinates(airport.getCoordinates()));
        airport.setPassengerService(new PassengerService(airport));
        airport.setPlaneService(new PlaneService(airport));

        startAirport(airport);
    }
}

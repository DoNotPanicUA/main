package com.donotpanic.airport;

import com.donotpanic.airport.domain.aircraft.AircraftFactory;
import com.donotpanic.airport.domain.aircraft.Plane;
import com.donotpanic.airport.domain.airport.Airport;
import com.donotpanic.airport.domain.airport.AirportFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private static List<Airport> knownAirports;
    private static List<Plane> knownPlanes;
    public static void main(String[] args){
        ApplicationContext mainContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        AirportFactory airportFactory = mainContext.getBean("AirportFactory", AirportFactory.class);
        try{
            knownAirports = createAirports(airportFactory);

            /*Wait for airports initialization*/
            //Actions.STANDBY_5SEC.doAction();

            knownPlanes = createPlanes();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    private static List<Airport> createAirports(AirportFactory airportFactory) throws Throwable{
        List<Airport> airports = new ArrayList<>();

        airports.add(airportFactory.getInstanceAirport("Borispol"));
        airports.add(airportFactory.getInstanceAirport("John F. Kennedy"));
        airports.add(airportFactory.getInstanceAirport("Heathrow"));
        airports.add(airportFactory.getInstanceAirport("Seattle-Tacoma"));

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

    private static List<Plane> createPlanes() throws Throwable{
        List<Plane> planes = new ArrayList<>();
        Plane newPlane = AircraftFactory.getPassengerPlane("APEK-005", knownAirports.get(0), "Boeing 737-100");
        planes.add(newPlane);

        newPlane = AircraftFactory.getPassengerPlane("FNAS-005", knownAirports.get(1), "Boeing 747-8");
        planes.add(newPlane);

        newPlane = AircraftFactory.getPassengerPlane("FD-221", knownAirports.get(2), "AN-2");
        planes.add(newPlane);

        newPlane = AircraftFactory.getPassengerPlane("RR-521", knownAirports.get(3), "AN-2");
        planes.add(newPlane);

        newPlane = AircraftFactory.getPassengerPlane("KV-552", knownAirports.get(0), "AN-2");
        planes.add(newPlane);

        return planes;
    }
}

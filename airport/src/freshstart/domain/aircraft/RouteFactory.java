package freshstart.domain.aircraft;

import freshstart.Application;
import freshstart.domain.airport.Airport;
import freshstart.domain.location.Location;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DoNotPanic-NB on 06.11.2017.
 */
public class RouteFactory {
    private static Map<Location, RouteCard> unAssignedRoutes = null;
    private static Map<Location, RouteCard> registeredRoutes = null;

    private static final String routeNamePrefix = "FRT";
    private static int routeIndex = 100;
    private static char routePostfix = 'A';

    private RouteFactory(){}

    private static synchronized String getNextRouteName(){
        if (routeIndex < 999){
            routeIndex++;
        }else{
            routeIndex = 100;
            routePostfix++;
        }

        return routeNamePrefix + "-" + routeIndex + routePostfix;
    }

    private static void genNewRoutes(int numOfRoutes){
        for (int i = 1; i <= numOfRoutes; i++){
            Airport fromAirport = Application.getRandomKnownAirport();
            Airport toAirport;
            while ((toAirport = Application.getRandomKnownAirport()).equals(fromAirport)){}

            unAssignedRoutes.put(fromAirport, new RouteCard().setDestination(fromAirport, toAirport, null));

        }
    }

    public static synchronized Route getRouteFrom(Airport airport)
    {
        if (unAssignedRoutes.size() == 0){
            genNewRoutes(15);
        }

        while (true){
            RouteCard resultObject = unAssignedRoutes.get(airport);
            if (resultObject != null){
                return resultObject;
            }else{
                genNewRoutes(10);
            }
        }
    }
}

package freshstart.domain.aircraft;

import freshstart.Application;
import freshstart.domain.airport.Airport;

public class RouteFactory {

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

    private static class PlaneRoute extends Route{
        PlaneRoute(String name){
            super(name);
        }
    }

    private static Route genRandNewRoute(Airport fromAirport){
        Airport toAirport = Application.getRandomKnownAirport();

        while (fromAirport.equals(toAirport)){
            toAirport = Application.getRandomKnownAirport();
        }

        //Route resultObject =

        return new PlaneRoute(getNextRouteName()).setDestination(fromAirport, toAirport);
    }

    public static synchronized void requestRouteFrom(Airport airport, Plane plane)
    {
        Route newRoute = genRandNewRoute(airport);
        newRoute.registerRoute(plane);
    }
}

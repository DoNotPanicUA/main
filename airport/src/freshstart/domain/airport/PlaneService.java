package freshstart.domain.airport;

import freshstart.domain.aircraft.Plane;
import freshstart.domain.aircraft.RouteFactory;

import java.util.Iterator;

/**
 * Created by aleonets on 21.08.2017.
 */
public class PlaneService {
    private Airport linkedAirport;

    PlaneService(Airport airport){
        this.linkedAirport = airport;
    }

    void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    synchronized Airstrip requestAirstrip(Plane plane){
        Iterator<Airstrip> iterator = linkedAirport.getAirstrips().iterator();
        Airstrip resultObject = null;

        while (iterator.hasNext() & resultObject == null){
            Airstrip airstrip = iterator.next();

            if (airstrip.checkIsLocationFree()){
                airstrip.reserveLocation(plane);
                resultObject = airstrip;
            }
        }
        return resultObject;
    }

    synchronized PlaneParkingPlace requestParking(Plane plane){
        Iterator<PlaneParkingPlace> iterator = linkedAirport.getParkingPlaces().iterator();
        PlaneParkingPlace resultObject = null;

        while (iterator.hasNext() & resultObject == null){
            PlaneParkingPlace parkingPlace = iterator.next();

            if (parkingPlace.checkIsLocationFree()){
                parkingPlace.reserveLocation(plane);
                resultObject = parkingPlace;
            }
        }
        return resultObject;
    }

    public synchronized void requestRoute(Plane plane){
        RouteFactory.requestRouteFrom(this.linkedAirport, plane);
    }

}

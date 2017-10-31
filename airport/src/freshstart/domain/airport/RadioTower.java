package freshstart.domain.airport;

import freshstart.domain.*;

import java.util.Date;

/**
 * Created by aleonets on 21.08.2017.
 */
public class RadioTower implements Location {
    private Airport linkedAirport;

    @Override
    public String getObjectName() {
        return "Radio Tower(" + linkedAirport.getAirportName()+ ")";
    }

    @Override
    public Coordinates getCoordinates() {
        return linkedAirport.getCoordinates();
    }

    public RadioTower(Airport airport){
        this.linkedAirport = airport;
        PrintService.printMessageObj("The radio tower is online!", this);
    }

    public void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    public synchronized Airstrip requestAirstrip(Plane plane){
        PrintService.printMessageObj("Plane("+ plane.getName() +") has requested an airstrip!", this);
        TimeInMilliSec.MINUTE.sleep();
        Airstrip freeAirstrip = null;

        while (linkedAirport.getAirstrips().iterator().hasNext() || freeAirstrip == null){
            Airstrip airstrip = linkedAirport.getAirstrips().iterator().next();

            if (airstrip.checkIsAirstripFree()){
                airstrip.reserveAirship(plane);
                freeAirstrip = airstrip;
            }
        }

        if (freeAirstrip == null){
            PrintService.printMessageObj("There is not free airstrip for the Plane("+ plane.getName() +")!", this);
        }else{
            PrintService.printMessageObj("Airstrip("+freeAirstrip.toString()+" is free for landing the Plane("+ plane.getName() +")!", this);
        }

        return freeAirstrip;
    }
}

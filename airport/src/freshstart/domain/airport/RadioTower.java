package freshstart.domain.airport;

import freshstart.domain.Plane;
import freshstart.domain.TimeInMilliSec;

import java.util.Date;

/**
 * Created by aleonets on 21.08.2017.
 */
public class RadioTower {
    private Airport linkedAirport;

    public RadioTower(Airport airport){
        this.linkedAirport = airport;
        System.out.println("["+new Date()+"] Radio tower of the " + airport.getAirportName() + " airport is online!");
    }

    public void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

    public synchronized Airstrip requestAirstrip(Plane plane){
        System.out.println("["+new Date()+"] RadioTower("+ this.linkedAirport.getAirportName()+"): Plane("+ plane.getName() +") has requested an airstrip!");
        TimeInMilliSec.MINUTE.sleep();
        Airstrip freeAirstrip = null;

        while (linkedAirport.getAirstrips().iterator().hasNext() || freeAirstrip == null){
            Airstrip airstrip = linkedAirport.getAirstrips().iterator().next();

            if (airstrip.checkIsAirstripFree()){
                airstrip.reservAirstip(plane);
                freeAirstrip = airstrip;
            }
        }

        if (freeAirstrip == null){
            System.out.println("["+new Date()+"] RadioTower("+ this.linkedAirport.getAirportName()+"): There is not free airstrip for the Plane("+ plane.getName() +")!");
        }else{
            System.out.println("["+new Date()+"] RadioTower("+ this.linkedAirport.getAirportName()+"): Airstrip("+freeAirstrip.toString()+" is free for landing the Plane("+ plane.getName() +")!");
        }

        return freeAirstrip;
    }
}

package freshstart.domain.airport;

import freshstart.domain.Plane;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Airstrip {
    private Plane currentPlane;

    public void freeAirstrip(){
        this.currentPlane = null;
    }

    synchronized void reserveAirship(Plane plane){
        if (currentPlane == null){
            currentPlane = plane;
        }
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    boolean checkIsAirstripFree(){
        return (currentPlane == null);
    }
}

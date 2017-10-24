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

    public synchronized void reservAirstip(Plane plane){
        if (currentPlane == null){
            currentPlane = plane;
        }
    }

    public Plane getCurrentPlane() {
        return currentPlane;
    }

    public boolean checkIsAirstripFree(){
        return (currentPlane == null ? true : false);
    }
}

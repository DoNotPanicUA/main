package freshstart.domain.airport;

/**
 * Created by aleonets on 21.08.2017.
 */
public class PlaneService {
    private Airport linkedAirport;

    public PlaneService(Airport airport){
        this.linkedAirport = airport;
    }

    public void setLinkedAirport(Airport airport){
        this.linkedAirport = airport;
    }

}

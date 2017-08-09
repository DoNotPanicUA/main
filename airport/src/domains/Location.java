package domains;

/**
 * Created by Red8 on 04/08/2017.
 */
public interface Location {
    void setLocationName(String name);
    void setCoordinates(Coordinates coordinates);

    String getLocationName();
    Coordinates getCoordinates();
}

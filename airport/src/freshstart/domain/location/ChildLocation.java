package freshstart.domain.location;

/**
 * Created by DoNotPanic-NB on 02.11.2017.
 */
public interface ChildLocation</*Parent location*/T extends Location> extends Location {
    T getParentLocation();
    Coordinates getParentCoordinates();
}

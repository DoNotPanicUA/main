package freshstart.domain.airport;

import freshstart.domain.aircraft.Route;
import freshstart.domain.common.Actions;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.Coordinates;
import freshstart.domain.location.AirportLocation;

import java.util.ArrayList;
import java.util.List;

public class Airport implements AirportLocation, Runnable {
    private String airportName;
    private List<Airstrip> airstrips = new ArrayList<>();
    private List<PlaneParkingPlace> parkingPlaces = new ArrayList<>();
    private RadioTower radioTower;
    private PassengerService passengerService;
    private PlaneService planeService;
    //private List<Airport> linkedAirports = new ArrayList<>();
    private Coordinates coordinates;
    private ArrayList<Route> registeredRoutes = new ArrayList<>();

    Airport(String airportName){
        this.airportName = airportName;
    }

    @Override
    public Airport getAirport() {
        return this;
    }

    public String getObjectName() {
        return "Airport("+airportName+")";
    }

    public void registerRoute(Route route){
        if (!this.registeredRoutes.contains(route)){
            this.registeredRoutes.add(route);
        }
    }

    boolean checkRouteRegistration(Route route){
        return this.registeredRoutes.contains(route);
    }

    @Override
    public Coordinates getCoordinates() {
        return (this.coordinates != null ? this.coordinates.getCoordinates() : null);
    }

    @Override
    public void run() {
        while(true){
            Actions.STANDBY.doAction();
        }
    }

    void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getAirportName() {
        return airportName;
    }

    void addAirstrip(Airstrip airstrip){
        if (airstrips.indexOf(airstrip) == -1){
            airstrips.add(airstrip);
        }
    }

    List<Airstrip> getAirstrips(){
        return this.airstrips;
    }

    void addParkingPlace(PlaneParkingPlace parkingPlace){
        if (parkingPlaces.indexOf(parkingPlace) == -1){
            parkingPlaces.add(parkingPlace);
        }
    }

    List<PlaneParkingPlace> getParkingPlaces(){
        return this.parkingPlaces;
    }

    public RadioTower getRadioTower() {
        return radioTower;
    }

    PassengerService getPassengerService() {
        return passengerService;
    }

    PlaneService getPlaneService() {
        return planeService;
    }

    void setRadioTower(RadioTower radioTower) {
        this.radioTower = radioTower;
        PrintService.printMessageObj("The radio tower is online!", this.radioTower);
    }

    void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    void setPlaneService(PlaneService planeService) {
        this.planeService = planeService;
    }
}
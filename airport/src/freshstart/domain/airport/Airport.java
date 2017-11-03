package freshstart.domain.airport;

import freshstart.domain.common.Actions;
import freshstart.domain.common.PrintService;
import freshstart.domain.location.Coordinates;
import freshstart.domain.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleonets on 21.08.2017.
 */
public class Airport implements Location, Runnable {
    private String airportName;
    private List<Airstrip> airstrips = new ArrayList<>();
    private List<PlaneParkingPlace> parkingPlaces = new ArrayList<>();
    private RadioTower radioTower;
    private PassengerService passengerService;
    private PlaneService planeService;
    private List<Airport> linkedAirports = new ArrayList<>();
    private Coordinates coordinates;

    Airport(String airportName){
        this.airportName = airportName;
    }

    public String getObjectName() {
        return "Airport("+airportName+")";
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void run() {
        while(true){
            Actions.STANDBY.doAction();
        }
    }

    public void setCoordinates(Coordinates coordinates) {
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

    public PassengerService getPassengerService() {
        return passengerService;
    }

    public PlaneService getPlaneService() {
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

    public void setLinkedAirports(List<Airport> linkedAirports) {
        for (Airport airport : linkedAirports){
            if(!airport.equals(this) && this.linkedAirports.indexOf(airport) == -1){
                this.linkedAirports.add(airport);
            }
        }
    }
}
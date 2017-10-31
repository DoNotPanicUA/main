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
public class Airport extends Thread implements Location {
    private String airportName;
    private List<Airstrip> airstrips = new ArrayList<>();
    private List<PlaneParkingPlace> parkingPlaces = new ArrayList<>();
    private RadioTower radioTower;
    private PassengerService passengerService;
    private PlaneService planeService;
    private List<Airport> linkedAirports = new ArrayList<>();
    private Coordinates coordinates;

    @Override
    public Location getGlobalLocation() {
        return this;
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
        super.run();
        iniAirport();

        while(true){
            Actions.STANDBY.doAction();
            //PrintService.printMessageObj("Ping.", this);
        }
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getAirportName() {
        return airportName;
    }

    public Airport(String airportName){
        this.airportName = airportName;
        this.start();
    }

    public void addAirstrip(Airstrip airstrip){
        if (airstrips.indexOf(airstrip) == -1){
            airstrips.add(airstrip);
        }
    }

    public List<Airstrip> getAirstrips(){
        return this.airstrips;
    }

    public void addParkingPlace(PlaneParkingPlace parkingPlace){
        if (parkingPlaces.indexOf(parkingPlace) == -1){
            parkingPlaces.add(parkingPlace);
        }
    }

    public List<PlaneParkingPlace> getParkingPlaces(){
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

    public void setRadioTower(RadioTower radioTower) {
        this.radioTower = radioTower;
    }

    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public void setPlaneService(PlaneService planeService) {
        this.planeService = planeService;
    }

    public void setLinkedAirports(List<Airport> linkedAirports) {
        for (Airport airport : linkedAirports){
            if(!airport.equals(this) && this.linkedAirports.indexOf(airport) == -1){
                this.linkedAirports.add(airport);
            }
        }
    }

    private void iniAirport(){
        //this.setLinkedAirports(airports);
        if (coordinates == null){
            setCoordinates(new Coordinates(new Random().nextInt(100),new Random().nextInt(100)));
        }

        int numAirstrips = new Random().nextInt(2)+1;
        int numParks = new Random().nextInt(numAirstrips*10)+numAirstrips;

        for (int i = 0 ; i < numAirstrips ; i++){
            this.addAirstrip(new Airstrip(this));
        }

        for (int i = 0 ; i < numParks ; i++){
            this.addParkingPlace(new PlaneParkingPlace());
        }

        PrintService.printMessageObj("The airport is online.", this);
        radioTower = new RadioTower(this);
        passengerService = new PassengerService(this);
        planeService = new PlaneService(this);

    }
}
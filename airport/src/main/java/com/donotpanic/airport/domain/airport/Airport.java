package com.donotpanic.airport.domain.airport;

import com.donotpanic.airport.domain.Engine.GlobalEngine;
import com.donotpanic.airport.domain.Engine.GlobalObject;
import com.donotpanic.airport.domain.aircraft.Route;
import com.donotpanic.airport.domain.common.Actions;
import com.donotpanic.airport.domain.common.PrintService;
import com.donotpanic.airport.domain.Engine.ObjectType;
import com.donotpanic.airport.domain.location.Coordinates;
import com.donotpanic.airport.domain.location.AirportLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Airport implements AirportLocation, Runnable, GlobalObject {
    private String airportName;
    private List<Airstrip> airstrips = new ArrayList<>();
    private List<PlaneParkingPlace> parkingPlaces = new ArrayList<>();
    private RadioTower radioTower;
    private PassengerService passengerService;
    private PlaneService planeService;
    //private List<Airport> linkedAirports = new ArrayList<>();
    private ArrayList<Route> registeredRoutes = new ArrayList<>();
    private GlobalEngine globalEngine;

    private ObjectType objType = ObjectType.AIRPORT;

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public ObjectType getObjectType() {
        return objType;
    }

    Airport(String airportName, GlobalEngine globalEngine){
        this.airportName = airportName;
        this.globalEngine = globalEngine;
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
        return globalEngine.getObjectCoordinates(this);
    }

    @Override
    public void run() {
        while(true){
            Actions.STANDBY.doAction();
        }
    }

//    void setCoordinates(Coordinates coordinates) {
//        this.coordinates = coordinates;
//    }

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
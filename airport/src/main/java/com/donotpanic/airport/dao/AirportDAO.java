package com.donotpanic.airport.dao;

import com.donotpanic.airport.domain.aircraft.AircraftFactory;

import java.util.ArrayList;

public interface AirportDAO {
    void registerAircraftModel(AircraftFactory.AircraftModel model);
    ArrayList<AircraftFactory.AircraftModel> getAllModels();

}

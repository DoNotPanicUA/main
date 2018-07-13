package com.donotpanic.airport.dao;

import com.donotpanic.airport.domain.aircraft.AircraftFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
class AirportOracleDAO implements AirportDAO {

    private JdbcTemplate jdbcTemplate;

    private AirportOracleDAO(DriverManagerDataSource source){
        jdbcTemplate = new JdbcTemplate(source);
    }

    @Bean(name = "OracleDAO")
    @Autowired
    @DependsOn("oracleDBLocal")
    @Scope("singleton")
    public AirportDAO getAirportOracleDAO(@Qualifier("oracleDBLocal") DriverManagerDataSource source){
        return new AirportOracleDAO(source);
    }

    @Override
    public void registerAircraftModel(AircraftFactory.AircraftModel model) {

    }

    @Override
    public ArrayList<AircraftFactory.AircraftModel> getAllModels() {
        return null;
    }
}

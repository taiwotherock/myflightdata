package com.virginatlantic.myflightdata.service;

import com.virginatlantic.myflightdata.model.FlightDto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface FlightDataService {

    void loadCSVFlightData();

    List<FlightDto> findFlightDays(Date flightDate) throws ParseException;

}

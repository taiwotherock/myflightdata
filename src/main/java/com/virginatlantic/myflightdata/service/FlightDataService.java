package com.virginatlantic.myflightdata.service;

import com.virginatlantic.myflightdata.model.FlightDto;

import java.text.ParseException;
import java.util.List;

public interface FlightDataService {

    List<FlightDto> processInputFile(String fileName,String flightDate);

    List<FlightDto> findFlightDays(List<FlightDto> flights, String flightDate) throws ParseException;

}

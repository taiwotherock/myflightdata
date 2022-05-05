package com.virginatlantic.myflightdata;

import com.virginatlantic.myflightdata.model.FlightDto;
import com.virginatlantic.myflightdata.service.FlightDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MyflightdataApplicationTests {

    @Autowired
    FlightDataService flightDataService;

    @Test
    void contextLoads() {
    }


    @Test
    public void validateFlightDayWednesday() throws ParseException {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date flightDate = df2.parse("2022-05-04");
        List<FlightDto> listFound = flightDataService.findFlightDays(flightDate);
        assertEquals(7, listFound.size());
    }

    @Test
    public void validateLastFlightTimeIs1535() throws ParseException {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date flightDate = df2.parse("2022-05-07");
        List<FlightDto> listFound = flightDataService.findFlightDays(flightDate);
        assertEquals("15:35", listFound.get(listFound.size() - 1).getDepartureTime());
    }

    @Test
    public void validateFlightToTobagoOnSunday() throws ParseException {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date flightDate = df2.parse("2022-05-01"); //Sunday may 1st
        List<FlightDto> listFound = flightDataService.findFlightDays(flightDate);
        List<String> actual = listFound.stream().map(FlightDto::getDestinationAirport).collect(toList());
        Assertions.assertTrue(actual.contains("TAB"));
    }

}

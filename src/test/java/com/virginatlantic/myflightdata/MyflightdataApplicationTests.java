package com.virginatlantic.myflightdata;

import com.virginatlantic.myflightdata.model.FlightDto;
import com.virginatlantic.myflightdata.service.FlightDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	public void validateFlightDayWednesday()
	{
		List<FlightDto> listFound = flightDataService.processInputFile("flights.csv","2022-05-04");
		assertEquals(7, listFound.size());
	}

	@Test
	public void validateLastFlightTimeIs1535()
	{
		List<FlightDto> listFound = flightDataService.processInputFile("flights.csv","2022-05-07");
		assertEquals("15:35", listFound.get(listFound.size()-1).getDepartureTime());
	}

	@Test
	public void validateFlightToTobagoOnSunday()
	{
		List<FlightDto> listFound = flightDataService.processInputFile("flights.csv","2022-05-01"); //Sunday may 1st
		List<String> actual = listFound.stream().map(FlightDto::getDestinationAirport).collect(toList());
		Assertions.assertTrue( actual.contains("TAB"));
	}

}

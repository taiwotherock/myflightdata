package com.virginatlantic.myflightdata.controller;


import com.virginatlantic.myflightdata.model.FlightDto;
import com.virginatlantic.myflightdata.service.FlightDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "flightdata", description = "Flight Data Service")
@RequestMapping("/flightdata")
public class FlightdataController {

    @Autowired
    FlightDataService flightDataService;

    @Operation(summary = "Fetch flight display information by date")
    @GetMapping(value = "/flights")
    public ResponseEntity<List<FlightDto>> getFlightInformationDisplay(
            @RequestParam(name = "flightDate", required = true, defaultValue = "2022-05-05")
            @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date flightDate) {
        List<FlightDto> flightDtoList = new ArrayList<>();
        try {

            flightDtoList = flightDataService.findFlightDays(flightDate);
            return ResponseEntity.ok(flightDtoList);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }


    }

}

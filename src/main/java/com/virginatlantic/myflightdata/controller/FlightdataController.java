package com.virginatlantic.myflightdata.controller;


import com.virginatlantic.myflightdata.model.FlightDto;
import com.virginatlantic.myflightdata.service.FlightDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@Tag(name="flightdata",description="Flight Data Service")
@RequestMapping("/flightdata")
public class FlightdataController {

    @Autowired
    FlightDataService flightDataService;

    @Operation(summary = "Fetch flight display information by date")
    @GetMapping(value = "/flights")
    public ResponseEntity<List<FlightDto>> getFlightInformationDisplay(
            @RequestParam(name = "flightDate", required = true) String flightDate,
            @RequestParam(name = "flightFileName", required = true) String flightFileName) {
        List<FlightDto> flightDtoList = new ArrayList<>();
        try {

            flightDtoList = flightDataService.processInputFile(flightFileName,flightDate);
            return ResponseEntity.ok(flightDtoList);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }

       // return ResponseEntity.ok(flightDtoList);
    }

}

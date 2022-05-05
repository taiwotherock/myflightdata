package com.virginatlantic.myflightdata.service;

import com.virginatlantic.myflightdata.model.FlightDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FlightDataServiceImpl implements FlightDataService {

    /* Pick this code from dzone website.
    https://dzone.com/articles/how-to-read-a-big-csv-file-with-java-8-and-stream
     */

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    Environment environment;

    protected List<FlightDto> flights;

    @PostConstruct
    public void init() {
        System.out.println("Init. load file ");
        loadCSVFlightData();

    }

    @Override
    public void loadCSVFlightData() {

        try {

            String fileName = environment.getProperty("flight.filename");
            Resource resource = resourceLoader.getResource("classpath:" + fileName);
            InputStream inputStream = resource.getInputStream();
            InputStreamReader r = new InputStreamReader(inputStream);

            BufferedReader br = new BufferedReader(r);  //new InputStreamReader(inputFS));

            // skip the header of the csv
            flights = br.lines().skip(1).map(mapToFlightData).collect(Collectors.toList());
            br.close();


        } catch (IOException e) {
            e.getStackTrace();
        }


        return;
    }

    private Function<String, FlightDto> mapToFlightData = (line) -> {

        String[] flightData = line.split(",");// a CSV has comma separated lines
        String[] flightWeekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        StringBuffer flightDays = new StringBuffer();
        List<String> flightDayList = new ArrayList<>();
        FlightDto flightDto = new FlightDto();

        flightDto.setDepartureTime(flightData[0]);//<-- this is the first column in the csv file
        flightDto.setDestination(flightData[1]);
        flightDto.setDestinationAirport(flightData[2]);
        flightDto.setFlightNo(flightData[3]);

        int d = 4;
        for (int i = 0; i < 7; i++) {

            if ((d + i) < flightData.length && checkFlightDay(flightData[d + i], flightWeekDays[i])) {
                if (flightDays.toString().isEmpty())
                    flightDays.append(flightWeekDays[i]);
                else
                    flightDays.append("," + flightWeekDays[i]);
            }
        }
        flightDto.setFlightDays(flightDays.toString().trim());
        return flightDto;
    };

    private boolean checkFlightDay(String flight, String day) {

        if (flight != null && flight.equals("x")) {
            return true;
        }

        return false;
    }

    @Override
    public List<FlightDto> findFlightDays(Date flightDate) throws ParseException {


        DateFormat df2 = new SimpleDateFormat("EEEE");
        String flightDay = df2.format(flightDate);
        System.out.println("Day of Flight Input " + flightDay);

        List<FlightDto> list = flights.stream()
                .filter(d -> d.getFlightDays().contains(flightDay))
                .sorted(Comparator.comparing(FlightDto::getDepartureTime))
                .collect(Collectors.toList());

        return list;

    }

}

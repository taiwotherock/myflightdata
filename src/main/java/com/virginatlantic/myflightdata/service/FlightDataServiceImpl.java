package com.virginatlantic.myflightdata.service;

import com.virginatlantic.myflightdata.model.FlightDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

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

    @Override
    public List<FlightDto> processInputFile(String fileName, String flightDate) {

        List<FlightDto> flights = new ArrayList<FlightDto>();

        try{

            Resource resource = resourceLoader.getResource("classpath:" + fileName);
            InputStream inputStream= resource.getInputStream();
            InputStreamReader r = new InputStreamReader(inputStream);

            //File inputF = new File(inputFilePath);
            //InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(r);  //new InputStreamReader(inputFS));

            // skip the header of the csv
            flights = br.lines().skip(1).map(mapToFlightData).collect(Collectors.toList());
            br.close();

            return  findFlightDays(flights, flightDate);



        }  catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        catch (ParseException e) {
            e.getStackTrace();
        }catch (IOException e) {
           e.getStackTrace();
        }


        return flights ;
    }

    private Function<String, FlightDto> mapToFlightData = (line) -> {

        String[] flightData = line.split(",");// a CSV has comma separated lines
        String[] flightWeekDays = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        StringBuffer flightDays = new StringBuffer();
        //flightDays.append("");

        List<String> flightDayList = new ArrayList<>();


        FlightDto flightDto = new FlightDto();

        //System.out.println("Flight " + flightData[0]);

        flightDto.setDepartureTime(flightData[0]);//<-- this is the first column in the csv file
        flightDto.setDestination(flightData[1]);
        flightDto.setDestinationAirport(flightData[2]);
        flightDto.setFlightNo(flightData[3]);

        int d = 4;
        for(int i=0;i<7;i++)
        {
            //System.out.println("F data " + line + " " + flightData.length + " " + i  + " " + d);
           if( (d+i) <flightData.length && checkFlightDay(flightData[d + i],flightWeekDays[i] ))
           {
               //flightDayList.add(flightWeekDays[i]);
               if(flightDays.toString().isEmpty())
                   flightDays.append(flightWeekDays[i]);
               else
                   flightDays.append("," + flightWeekDays[i]);
           }
        }

        //System.out.println("flight day " + flightDays);

       // flightDto.setFlightDaysList(flightDayList);
        flightDto.setFlightDays(flightDays.toString().trim());

        //more initialization goes here

        return flightDto;
    };

    private boolean checkFlightDay(String flight,String day)
    {
        //System.out.println("Day: " + flight + " " + day);
        if (flight != null && flight.equals("x")) {
            return true;
        }

        return false;
    }

    @Override
    public List<FlightDto> findFlightDays(List<FlightDto> flights, String flightDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(flightDate);

        DateFormat df2 = new SimpleDateFormat("EEEE");
        String flightDay = df2.format(dt);
        System.out.println("Day of Flight Input " + flightDay);

       List<FlightDto> list = flights.stream()
                .filter(d -> d.getFlightDays().contains(flightDay))
                .sorted(Comparator.comparing(FlightDto::getDepartureTime))
                .collect(Collectors.toList());

        System.out.println("List Found " + list.size());


       return  list;

    }

}

package com.flightontime.flightapi.service;

import com.flightontime.flightapi.domain.voo.dto.FlightPredictionResponse;
import com.flightontime.flightapi.domain.voo.dto.FlightRequest;
import com.flightontime.flightapi.infra.client.datascience.DataScienceClientInterface;
import com.flightontime.flightapi.infra.client.datascience.FlightPredictionMapper;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequest;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightPredictionService {

    @Autowired
    private DataScienceClientInterface client;

    public FlightPredictionResponse predictDelay(FlightRequest flightRequest) {
        DataScienceApiRequest apiRequest = FlightPredictionMapper.toDsApiRequest(flightRequest);
        DataScienceApiResponse apiResponse = client.getFlightPrediction(apiRequest);
        return FlightPredictionMapper.toFlightPredictionResponse(apiResponse);
    }

}

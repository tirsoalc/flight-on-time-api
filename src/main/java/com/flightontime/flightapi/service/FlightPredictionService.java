package com.flightontime.flightapi.service;

import com.flightontime.flightapi.domain.DataScienceApiOfflineException;
import com.flightontime.flightapi.domain.voo.dto.FlightPredictionResponse;
import com.flightontime.flightapi.domain.voo.dto.FlightRequest;
import com.flightontime.flightapi.infra.client.datascience.DataScienceClientInterface;
import com.flightontime.flightapi.infra.client.datascience.FlightPredictionMapper;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequest;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightPredictionService {

    @Autowired
    private DataScienceClientInterface client;

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackPredictDelay")
    public FlightPredictionResponse predictDelay(FlightRequest flightRequest) {
        DataScienceApiRequest apiRequest = FlightPredictionMapper.toDsApiRequest(flightRequest);
        DataScienceApiResponse apiResponse = client.getFlightPrediction(apiRequest);
        return FlightPredictionMapper.toFlightPredictionResponse(apiResponse);
    }

    public FlightPredictionResponse fallbackPredictDelay(FlightRequest flightRequest, Throwable t) {
        if(t instanceof CallNotPermittedException) {
            throw new DataScienceApiOfflineException("O circuito está aberto. Chamada ao serviço de Data Science interrompida");
        }

        throw new DataScienceApiOfflineException("O serviço de Data Science está fora do ar");
    }

}

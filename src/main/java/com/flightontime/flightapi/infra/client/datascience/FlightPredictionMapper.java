package com.flightontime.flightapi.infra.client.datascience;

import com.flightontime.flightapi.domain.voo.dto.FlightPredictionDetails;
import com.flightontime.flightapi.domain.voo.dto.FlightPredictionResponse;
import com.flightontime.flightapi.domain.voo.dto.FlightRequest;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequest;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResponse;

public class FlightPredictionMapper {

    public static DataScienceApiRequest toDsApiRequest(FlightRequest flightRequest) {
        DataScienceApiRequest apiRequest = new DataScienceApiRequest(
                flightRequest.companhia(),
                flightRequest.origem(),
                flightRequest.destino(),
                flightRequest.dataPartida(),
                flightRequest.distanciaKm()
        );
        return apiRequest;
    }

    public static FlightPredictionResponse toFlightPredictionResponse(DataScienceApiResponse apiResponse) {
        FlightPredictionDetails flightPredictionDetails = null;

        if (apiResponse.detalhes() != null) {
            flightPredictionDetails = new FlightPredictionDetails(
                    apiResponse.detalhes().is_feriado(),
                    apiResponse.detalhes().distancia_km()
            );
        }

        FlightPredictionResponse flightPredictionResponse = new FlightPredictionResponse(
                apiResponse.previsao(),
                apiResponse.probabilidade(),
                apiResponse.nivel_risco(),
                apiResponse.mensagem(),
                flightPredictionDetails
        );
        return flightPredictionResponse;
    }

}

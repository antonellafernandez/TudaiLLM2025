package demo.elastic.rag;

import org.springframework.ai.tool.annotation.ToolParam;

public class TripEstimateRequest {
    @ToolParam(description = "Precio del vuelo")
    private double flightCost;

    @ToolParam(description = "Clima esperado en destino (ej: soleado, lluvia)")
    private String weather;

    public double getFlightCost() {
        return flightCost;
    }

    public String getWeather() {
        return weather;
    }
}
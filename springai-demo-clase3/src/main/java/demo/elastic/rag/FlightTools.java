package demo.elastic.rag;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class FlightTools {
    // Métodos que ejecuta si detecta alguna necesidad relacionada semánticamente con cada @Tool
    @Tool(
            name = "getWeather",
            description = "Obtiene el clima esperado en una ciudad en una fecha específica"
    )
    public String getWeather(@ToolParam WeatherQuery query) {
        if (query.getCity().equalsIgnoreCase("Bariloche") &&
                query.getDate().startsWith("2025-07")) {
            return "frío";
        } else {
            return "soleado";
        }
    }

    @Tool(
            name = "searchFlights",
            description = "Busca vuelos entre dos ciudades en una fecha"
    )
    public double searchFlights(@ToolParam FlightQuery query) {
        return 120.50; // Simulación
    }

    @Tool(
            name = "estimateTripCost",
            description = "Calcula el costo estimado del viaje completo"
    )
    public String estimateTripCost(@ToolParam TripEstimateRequest input) {
        double dailyExpenses = input.getWeather().equalsIgnoreCase("frío") ? 80 : 50;
        double total = input.getFlightCost() + (dailyExpenses * 3);
        return "Costo estimado total: USD " + total;
    }

}
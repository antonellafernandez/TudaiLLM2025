package demo.elastic.rag;

import org.springframework.ai.tool.annotation.ToolParam;

public class WeatherQuery {
    @ToolParam(description = "Nombre de la ciudad")
    private String city;

    @ToolParam(description = "Fecha del viaje en formato YYYY-MM-DD")
    private String date;

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }
}

package demo.elastic.rag;

import org.springframework.ai.tool.annotation.ToolParam;

public class FlightQuery {
    // @ToolParam --> Al recibir la pregunta, si algún dato se asemeja
    // semánticamente, extrae la información para llenar los campos
    @ToolParam(description = "Ciudad de origen")
    private String origin;

    @ToolParam(description = "Ciudad de destino")
    private String destination;

    @ToolParam(description = "Fecha en formato YYYY-MM-DD")
    private String date;

    public String getDate() {
        return date;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }
}

package demo.elastic.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    private final ChatClient agent;

    public TravelController(ChatClient.Builder chatClientBuilder) {
        this.agent = chatClientBuilder.build();
    }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String question) {
        SystemMessage systemMessage = new SystemMessage("""
                 Eres un asistente de viajes. 
                 Usa las herramientas provistas siempre que puedas para obtener información extra sobre el clima, costos de los vuelos, y opciones."""
        );
        UserMessage userMessage = new UserMessage(question);
        final Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        //ChatClient.ChatClientRequestSpec spec = this.agent.prompt(prompt).tools("getWeather", "estimateTripCost", "searchFlights");
        ChatClient.ChatClientRequestSpec spec = this.agent.prompt(prompt).tools(new FlightTools());
        // Let's do the same without tools
        // ChatClient.ChatClientRequestSpec spec = this.agent.prompt(prompt);
        return ResponseEntity.ok(spec.call().content());
    }
}
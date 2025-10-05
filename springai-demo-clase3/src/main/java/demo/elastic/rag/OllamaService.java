package demo.elastic.rag;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {
    @Value("${spring.ai.ollama.base-url}")
    String ollama_url;

    @Value("${spring.ai.ollama.model}")
    String model;

    @Value("${spring.ai.ollama.token}")
    String token;

    @PostConstruct
    public void init() {
        System.out.println("Ollama URL: " + ollama_url);
    }

    public String queryLLM(String prompt) {
        Gson gson = new Gson();
        String requestBody = gson.toJson(new MyOllamaOptions(false, model, prompt));

        WebClient webClient = WebClient.builder()
                .baseUrl(this.ollama_url)
                .defaultHeader("accept", "*/*")
                .defaultHeader("authorization", "Bearer " + token)
                .defaultHeader("content-type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        String response = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;

    }
}
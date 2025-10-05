package demo.elastic.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Embedding --> Representacion a partir del texto, el texto pasa a ser un vector y al comparar dos vectores
// obtengo similitud alta si los vectores se encuentran en un mismo espacio vectorial de respresentación

// @Component/@Bean --> Controla lo que va pasando en cada etapa del ciclo de vida
@Component
@Primary
public class MyCustomOllamaEmbeddingsFilteredChatModel implements ChatModel {

    @Autowired
    private OllamaService service;

    @Autowired
    private EmbeddingModel embeddingModel;

    // Representación vectorial de blacklist
    private List<float[]> bannedEmbeddings = null;

    // Cuando se crea el @Component llama a este método isntantáneamente
    @PostConstruct
    private void init() {
        // Define contenido inapropiado
        List<String> blacklist = List.of(
                "violencia extrema",
                "contenido sexual explícito",
                "odio hacia grupos",
                "uso de drogas ilegales"
        );
        // Obtiene los vectores asociados
        try {
            EmbeddingResponse response = embeddingModel.embedForResponse(blacklist);
            this.bannedEmbeddings = response.getResults().stream()
                    .map(r -> r.getOutput())
                    .toList();
        } catch (Exception ex) {
            this.bannedEmbeddings = new ArrayList<>();
        }
    }

    // Define si la respuesta es inapropiada
    public boolean isInappropriate(String text) {
        if (bannedEmbeddings.isEmpty())
            return false;
        float[] inputEmbedding = embeddingModel.embed(text);
        for (float[] bannedEmbedding : bannedEmbeddings) {
            if (cosineSimilarity(inputEmbedding, bannedEmbedding) > 0.85) {
                return true;
            }
        }
        return false;
    }

    // Calcula el ángulo entre los dos vectores
    // Algo así como cuánto se separa un vector del otro
    // Si superan un umbral dado, son similares
    private double cosineSimilarity(float[] a, float[] b) {
        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        String promptText = prompt.getInstructions().stream()
                .map(m -> m.getText())
                .reduce("", (a, b) -> a + "\n" + b);

        // AssistantMessage: representa un mensaje generado por el modelo (como un texto de respuesta).
        // Generation: representa una posible "salida" del modelo (puede haber varias respuestas alternativas).
        // ChatResponse: agrupa una o más Generation.

        String response = service.queryLLM(promptText);

        // Respuesta si el contenido es inapropiado
        if (isInappropriate(response)) {
            Generation generation = new Generation(new AssistantMessage("⚠️ Contenido inapropiado detectado."));
            return new ChatResponse(List.of(generation));
        }

        Generation generation = new Generation(new AssistantMessage(response));
        // Adaptar la respuesta a ChatResponse
        ChatResponse chatResponse = new ChatResponse(List.of(generation));
        return chatResponse;
    }

}
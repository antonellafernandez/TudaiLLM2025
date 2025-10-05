package demo.elastic.rag;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyCustomOllamaChatModel implements ChatModel {

    @Autowired
    private OllamaService service;

    @Override
    public ChatResponse call(Prompt prompt) {
        String promptText = prompt.getInstructions().stream()
                .map(m -> m.getText())
                .reduce("", (a, b) -> a + "\n" + b);

        //AssistantMessage: representa un mensaje generado por el modelo (como un texto de respuesta).
        //Generation: representa una posible "salida" del modelo (puede haber varias respuestas alternativas).
        //ChatResponse: agrupa una o más Generation.

        String response = service.queryLLM(promptText);
        Generation generation = new Generation(new AssistantMessage(response));
        // Adaptar la respuesta a ChatResponse
        ChatResponse chatResponse = new ChatResponse(List.of(generation));
        return chatResponse;
    }

}
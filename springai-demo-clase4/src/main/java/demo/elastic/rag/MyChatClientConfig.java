package demo.elastic.rag;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyChatClientConfig {
    // @Bean --> Componente que se inicializa, configura y está disponible para inyección
    // en otras partes de la aplicación
    @Bean
    public ChatClient.Builder chatClientBuilder(ChatModel model) {
        return ChatClient.builder(model);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public Tesseract tesseract() {
        Tesseract tess = new Tesseract();
        tess.setDatapath("src/main/resources"); // Ruta a los datos de idioma
        tess.setLanguage("eng");
        return tess;
    }

}

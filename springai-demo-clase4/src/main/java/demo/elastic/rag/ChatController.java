package demo.elastic.rag;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    // @Autowired --> Inyecta automáticamente un bean que coincida con este tipo
    @Autowired
    private ChatClient agent;

    @Autowired
    private Tesseract tesseract;

    // Endpoint para obtener respuesta
    @PostMapping("/text")
    public ResponseEntity<String> chatFromText(@RequestBody String question) { // question es la pregunta del usuario
        System.out.println(question);
        return ResponseEntity.ok(buildResponse(question));
    }

    private String buildResponse(String question) {
        ChatClient.ChatClientRequestSpec spec = this.agent.prompt(buildPrompt(question));
        return spec.call().content();
    }

    private Prompt buildPrompt(String question) {
        // Instrucciones básicas de comportamiento --> Puede ser uno
        SystemMessage systemMessage = new SystemMessage("""
                 Eres un chat empático y extremadamente amable a la hora de responder."""
        );
        // Mensaje del usuario --> Puede ser uno o más
        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return prompt;
    }

    // Endpoint que recibe imagen en base64, hace OCR y consulta al LLM
    // https:baeldung.com/java-ocr-tesseract
    @PostMapping("/image")
    public ResponseEntity<String> chatFromImage(@RequestBody ImageRequest base64Image) throws IOException, TesseractException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image.getImage());
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

        String extractedText = tesseract.doOCR(img);

        System.out.println(extractedText);

        String assignment = "Leé este texto extraído de una imagen y explicalo: \n" + extractedText;
        return ResponseEntity.ok(buildResponse(assignment));
    }

    public static class ImageRequest {
        private String image;
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }
    }

}
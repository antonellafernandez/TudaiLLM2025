package demo.elastic.rag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rag")
public class RagController {

    private final OllamaService ollamaService;
    private final RagService openAIService;

    @Autowired
    public RagController(OllamaService ollamaService, RagService openAIService) {
        this.ollamaService = ollamaService;
        this.openAIService = openAIService;
    }

    @PostMapping("/queryOllama")
    public ResponseEntity queryOllama(@RequestBody String question) {
        try {
            String response = ollamaService.queryLLM(question);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/queryOpenAI")
    public ResponseEntity queryOpenAI(@RequestBody String question) {
        try {
            String response = openAIService.queryLLM(question);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

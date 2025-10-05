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

    private final RagService ragService;
    private final OllamaService ollamaService;

    @Autowired
    public RagController(RagService ragService, OllamaService ollamaService) {
        this.ragService = ragService;
        this.ollamaService = ollamaService;
    }

    @PostMapping("/ingestPdf")
    public ResponseEntity ingestPDF(@RequestBody String path) {
        try {
            ragService.ingestPDF(path);
            return ResponseEntity.ok().body("Done!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/queryOpenAI")
    public ResponseEntity query(@RequestBody String question) {
        try {
            String response = ragService.queryLLM(question);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
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

}

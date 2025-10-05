package demo.elastic.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
//import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {

    private ChatClient chatClient;

    public RagService(ChatClient.Builder clientBuilder) {
        this.chatClient = clientBuilder.build();
    }

    public String queryLLM(String question) {

        // Setting the prompt with the context
        String prompt = """
            You're assisting with answering generic questions. You should provide accurate answers to the
            question in the QUESTION section. 
            If unsure, simply state that you don't know.
            QUESTION:
            """ + question;
        log.info(prompt);

        String response = chatClient.prompt()
            .user(prompt)
            .call()
            .content();

        return response;
    }

}

package demo.elastic.rag;

import java.util.HashMap;
import java.util.Map;

public class MyOllamaOptions {
    /**
    {
    "stream": false,
    "model": "deepseek-r1",
    "messages": [{ "role": "user", "content": "Question goes here" }]
    }
    */
    public boolean stream;
    public String model;
    public Map<String,String>[] messages;

    public MyOllamaOptions(boolean stream, String model, String msg) {
        this.stream = stream;
        this.model = model;
        Map<String,String> map = new HashMap<String, String>();
        map.put("role", "user");
        map.put("content", msg);
        this.messages = new Map[]{map};
    }

}

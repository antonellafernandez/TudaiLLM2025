#!/bin/bash

#java -jar target/rag.article-0.0.1-SNAPSHOT.jar

# Query al LLM
 MODEL=OpenAI
 curl -XPOST "http://localhost:8080/rag/query$MODEL" --header "Content-Type: text/plain" --data "How many types of horizontal dashes can I use?"

# MODEL=Ollama
 curl -XPOST "http://localhost:8080/rag/query$MODEL" --header "Content-Type: text/plain" --data "How many types of horizontal dashes can I use?"

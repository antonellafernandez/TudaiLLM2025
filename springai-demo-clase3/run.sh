#!/bin/bash

java -jar target/rag.article-0.0.1-SNAPSHOT.jar

# Query al LLM
# curl -XPOST "http://localhost:8080/api/travel/ask" --header "Content-Type: text/plain" --data "Quiero viajar de Mendoza a Bariloche el 10 de julio. ¿Cuánto me costaría?"

# Tutorial para incorporar ollama
# https://chatgpt.com/c/67b36684-d138-800b-9ba1-010807e0b333

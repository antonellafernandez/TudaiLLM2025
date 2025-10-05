#!/bin/bash

#Ingestar al menos un pdf para RAG
 CURRENT_DIR=$(pwd)
 curl -XPOST "http://localhost:8080/rag/ingestPdf" --header "Content-Type: text/plain" --data "file://$CURRENT_DIR/Sample-pdf.pdf"
 curl -XPOST "http://localhost:8080/rag/ingestPdf" --header "Content-Type: text/plain" --data "file://$CURRENT_DIR/Sample-pdf2.pdf"
 curl -XPOST "http://localhost:8080/rag/ingestPdf" --header "Content-Type: text/plain" --data "file://$CURRENT_DIR/Sample-pdf3.pdf"

# Query al LLM
 curl -XPOST "http://localhost:8080/rag/queryOpenAI" --header "Content-Type: text/plain" --data "How many types of horizontal dashes can I use?"

# Tutorial para incorporar ollama
# https://chatgpt.com/c/67b36684-d138-800b-9ba1-010807e0b333

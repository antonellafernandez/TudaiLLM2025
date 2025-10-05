#!/bin/bash

IMAGEN="src/main/resources/source-document.jpg"
# Generar archivo JSON con imagen en base64 sin saltos de línea
BASE64_TMP=$(mktemp)
JSON_TMP=$(mktemp)

base64 -w 0 "$IMAGEN" > "$BASE64_TMP"
echo -n '{"image": "' > "$JSON_TMP"
cat "$BASE64_TMP" >> "$JSON_TMP"
echo '"}' >> "$JSON_TMP"

curl -XPOST "http://localhost:8080/chat/image" -H "Content-Type: application/json" --data @"$JSON_TMP"
rm "$BASE64_TMP" "$JSON_TMP"

# Query al LLM con texto
curl -XPOST "http://localhost:8080/chat/text" --header "Content-Type: text/plain" --data "Puedes ampliar un poco mas?"

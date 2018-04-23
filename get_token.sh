#!/bin/bash
curl -v -H "Content-Type: application/json" -X POST http://localhost:8080/ipl/login -d "{\"username\":\"$1\",\"password\":\"$2\"}"

#!/bin/bash
curl -v -H "Content-Type: application/json" -X POST http://localhost:8080/ipl/login -d "{\"username\":\"superadmin\",\"password\":\"ipl@master\"}"

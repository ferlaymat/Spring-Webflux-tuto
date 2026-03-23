# Spring-Webflux-tuto

This repository providing an example of how to use Spring Webflux with r2dbc or mongodb.
They do not cover all use cases, but offer an easy way to get started with this tool.

## Requirements
- Java 21
- Maven

## Getting Started

Select webflux or webflux-mongo folder then start the app via the command line:
```bash
mvn spring-boot:run
```
The app will be available throught the url http://localhost:8080

## Projects and URLs

| Service | URL | Use case |
|---|---|---|
| Person | http://localhost:8080/api/v1/person | Standard CRUD sample |
| Back pressure | http://localhost:8080/api/v1/backpressure | Give 3 cases to explain back pressure and how to basically manage it |
| Producer | http://localhost:8080/api/v1/producer | Give sample of differents main Flux or Mono functions |
| Book | http://localhost:8080/api/v1/book | Give simple CRUD with different implementations in Reactive environment |
| MongoDb | http://localhost:8081 | Mongo Express UI|

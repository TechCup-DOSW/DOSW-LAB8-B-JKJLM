# TechCup Futbol - Image Service Testing Guide (Postman)

This guide explains how to validate the Part B image microservice end-to-end using Postman.

## 1. Prerequisites

1. Docker running.
2. Postman installed.

## 2. Start MongoDB

Run:

```powershell
docker run --name mongo-lab8 -p 27017:27017 -d mongo
```

If the container already exists:

```powershell
docker start mongo-lab8
```

## 3. Run the application

From project root:

```powershell
./mvnw spring-boot:run
```

The API should be available at:

- Base URL: http://localhost:8081

## 4. Postman setup

1. Create a new collection (example: LAB8-ImageService).
2. Add this variable in the collection:
- Name: baseUrl
- Value: http://localhost:8081
3. Use {{baseUrl}} in all requests.

## 5. Endpoint tests (minimum required)

## 5.1 Upload image

- Method: POST
- URL: {{baseUrl}}/imagenes
- Body: form-data
- Fields:
- archivo -> Type: File -> select image file
- referenciaExterna -> Type: Text -> example: torneo-001

Expected:

1. Status 200.
2. JSON with fields like id, nombre, tipoContenido, tamano, fechaCarga, referenciaExterna.

## 5.2 List images

- Method: GET
- URL: {{baseUrl}}/imagenes

Expected:

1. Status 200.
2. JSON array (can be empty if nothing uploaded).

## 5.3 Get image by id

- Method: GET
- URL: {{baseUrl}}/imagenes/{id}
- Example: {{baseUrl}}/imagenes/69c2ab0bd99f5a53b9027f08

Expected:

1. Status 200.
2. Binary response body.
3. Response headers include Content-Disposition and Content-Type.

## 5.4 Delete image by id

- Method: DELETE
- URL: {{baseUrl}}/imagenes/{id}

Expected:

1. Status 204 when id exists.
2. Status 404 when id does not exist.

## 5.5 List images by external reference

- Method: GET
- URL: {{baseUrl}}/imagenes/referencia/{referenciaExterna}
- Example: {{baseUrl}}/imagenes/referencia/torneo-001

Expected:

1. Status 200.
2. JSON array with matching records.

## 6. Suggested full validation flow

1. Upload two images using the same referenciaExterna (torneo-001).
2. Call GET /imagenes and verify both appear.
3. Copy one id and call GET /imagenes/{id}.
4. Call GET /imagenes/referencia/torneo-001 and verify both appear.
5. Delete one image with DELETE /imagenes/{id}.
6. Call GET /imagenes again and verify only one remains.





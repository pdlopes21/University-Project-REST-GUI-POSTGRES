package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ArbitroApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    private ArbitroApiClient() {
        // Private constructor to prevent instantiation
    }


    public static List<ArbitroDto> getAllArbitros() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<ArbitroDto>>() {
        });
    }

    public static List<ArbitroDto> getArbitrosByNome(String nome) throws Exception {
        nome = nome.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros/by-nome/" + nome))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<ArbitroDto>>() {
        });
    }


    public static ArbitroDto getArbitroById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros/by-id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), ArbitroDto.class);
    }

    public static ArbitroDto getArbitroByUsername(String username) throws Exception {
        username = username.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros/by-username/" + username))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), ArbitroDto.class);
    }

    public static ArbitroDto createArbitro(ArbitroDto arbitroDto) throws Exception {
        String json = mapper.writeValueAsString(arbitroDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), ArbitroDto.class);
    }

    public static ArbitroDto updateArbitro(ArbitroDto arbitroDto) throws Exception {
        String json = mapper.writeValueAsString(arbitroDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros/update/" + arbitroDto.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), ArbitroDto.class);
    }

    public static void deleteArbitro(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/arbitros/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete arbitro. HTTP code: " + response.statusCode());
        }
    }





}

package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.*;

public class EquipaApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    private EquipaApiClient() { 
    }

    public static List<EquipaDto> getAllEquipas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<EquipaDto>>() {});
    }

    public static EquipaDto getEquipaByNome(String nome) throws Exception {
        nome = nome.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/by-nome/" + nome))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

    public static EquipaDto getEquipaById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/by-id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

    public static EquipaDto createEquipa(EquipaDto equipa) throws Exception {
        String json = mapper.writeValueAsString(equipa);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

    public static EquipaDto updateEquipa(EquipaDto equipa) throws Exception {
        String json = mapper.writeValueAsString(equipa);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/update/" + equipa.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

    public static void deleteEquipaById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/by-id/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete equipa. HTTP code: " + response.statusCode());
        }
    }

    public static void deleteEquipaByNome(String nome) throws Exception {
        nome = nome.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/by-nome/" + nome))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete equipa by nome. HTTP code: " + response.statusCode());
        }
    }

    public static EquipaDto addJogador(long equipaId, long jogadorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/add-jogador/" + equipaId + "/" + jogadorId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

    public static EquipaDto removeJogador(long equipaId, long jogadorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/equipas/remove-jogador/" + equipaId + "/" + jogadorId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), EquipaDto.class);
    }

}

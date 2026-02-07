package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JogadorApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    private JogadorApiClient() {
        // Private constructor to prevent instantiation
    }

    public static List<JogadorDto> getAllJogadores() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<JogadorDto>>() {
        });
    }

    public static List<JogadorDto> getJogadoresByNome(String nome) throws Exception {
        nome = nome.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores/by-nome/" + nome))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<JogadorDto>>() {
        });
    }


    public static JogadorDto getJogadorById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores/by-id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogadorDto.class);
    }


    public static JogadorDto getJogadorByUsername(String username) throws Exception {
        username = username.replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores/by-username/" + username))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogadorDto.class);
    }



    public static JogadorDto createJogador(JogadorDto jogadorDto) throws Exception {
        String json = mapper.writeValueAsString(jogadorDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), JogadorDto.class);
    }


    public static JogadorDto updateJogador(JogadorDto jogadorDto) throws Exception {
        String json = mapper.writeValueAsString(jogadorDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores/update/" + jogadorDto.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogadorDto.class);
    }

    public static void deleteJogador(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogadores/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete jogador. HTTP code: " + response.statusCode());
        }
    }
}

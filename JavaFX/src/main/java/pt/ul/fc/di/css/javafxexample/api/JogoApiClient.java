package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.*;

public class JogoApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();



    private JogoApiClient() {
    }

    public static List<JogoDto> getAllJogos() throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<JogoDto>>() {});
    }

    public static JogoDto getJogoById(long id) throws Exception {
        mapper.findAndRegisterModules(); // Ensure modules are registered
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/by-id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }


    public static JogoDto createJogo(JogoDto jogoDto) throws Exception {
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(jogoDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }

    public static int deleteJogo(long id) throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204 && response.statusCode() != 200) {
            return 0; // No content, deleted successfully
        } else if (response.statusCode() == 404) {
            return -1; // Not found
        } else {
            return -2; // Other error
        }
    }

    public static JogoDto atualizarPlanteis(Long jogoId, Long plantelCasaId, Long plantelVisitanteId) throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/atualizar-planteis/" + jogoId + "/" + plantelCasaId + "/" + plantelVisitanteId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }

    public static JogoDto atualizarArbitroPrincipal(Long jogoId, Long arbitroId) throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/atualizar-arbitro-principal/" + jogoId + "/" + arbitroId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }

    public static JogoDto addArbitroToJogo(Long jogoId, Long arbitroId) throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/add-arbitro/" + jogoId + "/" + arbitroId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }

    public static JogoDto removeArbitroFromJogo(Long jogoId, Long arbitroId) throws Exception {
        mapper.findAndRegisterModules();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/remover-arbitros/" + jogoId + "/" + arbitroId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }

    public static JogoDto terminarJogo(Long jogoId, JogoDto jogoDto) throws Exception {
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(jogoDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/jogos/resultado/" + jogoId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), JogoDto.class);
    }


}
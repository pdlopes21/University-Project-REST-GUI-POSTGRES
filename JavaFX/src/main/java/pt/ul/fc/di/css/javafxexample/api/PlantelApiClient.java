package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class PlantelApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    private PlantelApiClient() {
        // Private constructor to prevent instantiation
    }

    public static List<PlantelDto> getAllPlanteis() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<PlantelDto>>() {});
    }

    public static PlantelDto getPlantelById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/by-id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null; // Not found
        } else if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), PlantelDto.class);
    }


    public static List<PlantelDto> getAllPlanteisByEquipaId(long equipaId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/by-equipa/" + equipaId))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), new TypeReference<List<PlantelDto>>() {});
    }

    public static PlantelDto createPlantel(PlantelDto plantelDto) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(plantelDto)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), PlantelDto.class);
    }



    public static int deletePlantelById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            return 0; // No content
        } else if (response.statusCode() == 404) {
            return -1; // Not found
        } else {
            return -2; // Error occurred
        }
    }


    public static PlantelDto addJogadorToPlantel(long plantelId, long jogadorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/add-jogador/" + plantelId + "/" + jogadorId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null; // Not found
        } else if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), PlantelDto.class);
    }

    public static PlantelDto removeJogadorFromPlantel(long plantelId, long jogadorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/remove-jogador/" + plantelId + "/" + jogadorId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null; // Not found
        } else if (response.statusCode() != 200 && response.statusCode() != 201) {
            return null;
        }

        return mapper.readValue(response.body(), PlantelDto.class);
    }

    public static PlantelDto updatePlantel(long id, PlantelDto plantelDto) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/planteis/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(plantelDto)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null; // Not found
        } else if (response.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(response.body(), PlantelDto.class);
    }




}

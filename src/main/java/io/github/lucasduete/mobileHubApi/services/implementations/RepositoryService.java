package io.github.lucasduete.mobileHubApi.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.entities.Repository;
import io.github.lucasduete.mobileHubApi.services.interfaces.RepositoryServiceInterface;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class RepositoryService implements RepositoryServiceInterface {

    private final WebTarget target;

    public RepositoryService() {
        Client client = ClientBuilder
                .newClient();

        this.target = client.target(MyApplication.URL_BASE);
    }

    @Override
    public Repository getSingleRepo(String owner, String repo) throws IOException {

        Response response = target
                .path("repos")
                .path(owner)
                .path(repo)
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .get();

        return recuperarRepositorio(response);
    }

    @Override
    public List<Repository> getMyRepos(String token) throws IOException {

        Response response = null;

        if (token.endsWith("=")) {
            response = target
                    .path("user")
                    .path("repos")
                    .request()
                    .header("Accept", "application/json, application/vnd.github.v3+json")
                    .header("Authorization", String.format("Basic %s", token))
                    .get();
        } else {
            response = target
                    .path("user")
                    .path("repos")
                    .request()
                    .header("Accept", "application/json, application/vnd.github.v3+json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .get();
        }

        return recuperarRepositorios(response);

    }

    @Override
    public List<Repository> getMyStars(String token) throws IOException {

        Response response = null;

        if (token.endsWith("=")) {
            response = target
                    .path("user")
                    .path("starred")
                    .request()
                    .header("Accept", "application/json, application/vnd.github.v3+json")
                    .header("Authorization", String.format("Basic %s", token))
                    .get();
        } else {
            response = target
                    .path("user")
                    .path("starred")
                    .request()
                    .header("Accept", "application/json, application/vnd.github.v3+json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .get();
        }

        return recuperarRepositorios(response);

    }

    @Override
    public List<Repository> search(String keyword) throws IOException {

        Response response = target
                .path("search")
                .path("repositories")
                .queryParam("q", keyword)
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .request()
                .header("Accept", "application/json, application/vnd.github.v3.text-match+json")
                .get();

        return recuperarRepositoriosBusca(response);

    }

    private Repository recuperarRepositorio(Response response) throws IOException {
        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonObject.toString(), Repository.class);
    }

    private List<Repository> recuperarRepositorios(Response response) throws IOException {
        JsonArray jsonArray = recuperarJsonArray(response);

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(jsonArray.toString(), Repository[].class));
    }

    private List<Repository> recuperarRepositoriosBusca(Response response) throws IOException {
        JsonArray jsonArray = recuperarJsonArrayBusca(response);

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(jsonArray.toString(), Repository[].class));
    }

    private JsonArray recuperarJsonArray(Response response) {
        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        return jsonArray
                .stream()
//                .limit(10)
                .collect(JsonCollectors.toJsonArray());
    }

    private JsonArray recuperarJsonArrayBusca(Response response) {
        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        System.out.printf("\n\n TAMANHO: " + jsonObject.getInt("total_count"));

        return jsonObject
                .getJsonArray("items")
                .stream()
                .limit(10)
                .collect(JsonCollectors.toJsonArray());
    }
}

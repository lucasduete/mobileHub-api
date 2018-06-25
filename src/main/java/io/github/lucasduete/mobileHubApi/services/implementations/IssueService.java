package io.github.lucasduete.mobileHubApi.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.entities.Issue;
import io.github.lucasduete.mobileHubApi.services.interfaces.IssueServiceInterface;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IssueService implements IssueServiceInterface {

    private final WebTarget target;

    public IssueService() {
        Client client = ClientBuilder.newClient();

        this.target = client.target(MyApplication.URL_BASE);
    }

    @Override
    public Issue getIssue(String repository, int issueNumber) throws IOException {

        return null;
    }

    @Override
    public List<Issue> getIssuesByRepo(String repository) throws IOException {
        Response response = target
                .path("repos")
                .path(repository)
                .path("issues")
                .queryParam("state", "open")
                .queryParam("sort", "created")
                .queryParam("direction", "desc")
                .request()
                .get();

        return recuperaIssues(response);
    }

    private List<Issue> recuperaIssues(Response response) throws IOException {
        JsonArray jsonArray = recuperarJsonArray(response);

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(jsonArray.toString(), Issue[].class)) ;
    }

    private JsonArray recuperarJsonArray(Response response) {
        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        return jsonArray
                .stream()
                .limit(10)
                .collect(JsonCollectors.toJsonArray());
    }
}

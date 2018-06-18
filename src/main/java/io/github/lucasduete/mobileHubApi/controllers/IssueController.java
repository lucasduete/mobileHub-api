package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.MyApplication;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("issues")
public class IssueController {

    private static final String URL_BASE = MyApplication.URL_BASE;

    @GET
    @Path("repository")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssuesByRepo(@QueryParam("owner") String owner, @QueryParam("repo") String repo) {

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget target = client
                .target(URL_BASE)
                .path("repos")
                .path(owner)
                .path(repo)
                .path("issues");

        Response response = target
                .queryParam("state", "open")
                .queryParam("sort", "comments")
                .queryParam("direction", "desc")
                .request()
                .get();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        jsonArray = jsonArray.stream().limit(10).collect(JsonCollectors.toJsonArray());
        return Response.ok(jsonArray).build();
    }

    @GET
    @Path("issue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleIssue(@QueryParam("owner") String owner,
                                   @QueryParam("repo") String repo,
                                   @QueryParam("number") String number) {

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget target = client
                .target(URL_BASE)
                .path("repos")
                .path(owner)
                .path(repo)
                .path("issues")
                .path(number);

        Response response = target
                .request()
                .get();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        jsonArray = jsonArray.stream().limit(10).collect(JsonCollectors.toJsonArray());
        return Response.ok(jsonArray).build();
    }
}

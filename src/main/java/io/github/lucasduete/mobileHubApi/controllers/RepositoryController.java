package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;

@Path("repositories")
public class RepositoryController {

    private static final String URL_BASE = MyApplication.URL_BASE;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Security
    public Response getMyRepos(@Context SecurityContext securityContext) {

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget webTarget = client.target(URL_BASE)
                .path("user")
                .path("repos");

        Response response = webTarget
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .header("Authorization", String.format("bearer %s", TokenManagement.getToken(securityContext)))
                .get();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        jsonArray = jsonArray.stream().limit(10).collect(JsonCollectors.toJsonArray());
        return Response.ok(jsonArray).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Security
    public Response getMyStars(@Context SecurityContext securityContext) {

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget webTarget = client.target(URL_BASE)
                .path("user")
                .path("starred");

        Response response = webTarget
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .header("Authorization", String.format("bearer %s", TokenManagement.getToken(securityContext)))
                .get();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonArray = jsonReader.readArray();

        System.out.printf("\n\n TAMANHO: " + jsonArray.size());

        jsonArray = jsonArray.stream().limit(10).collect(JsonCollectors.toJsonArray());
        return Response.ok(jsonArray).build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("keyword") String keyword) {

        if (keyword == null || keyword.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget webTarget = client.target(URL_BASE)
                .path("search")
                .path("repositories");

        Response response = webTarget
                .queryParam("q", keyword)
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .request()
                .header("Accept", "application/json, application/vnd.github.v3.text-match+json")
                .get();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        System.out.printf("\n\n TAMANHO: " + jsonObject.getInt("total_count"));

        JsonArray jsonArray = jsonObject.getJsonArray("items");
        jsonArray = jsonArray.stream().limit(10).collect(JsonCollectors.toJsonArray());

        return Response.ok(jsonArray).build();
    }
}

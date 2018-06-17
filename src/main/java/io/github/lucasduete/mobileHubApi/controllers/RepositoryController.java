package io.github.lucasduete.mobileHubApi.controllers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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

@Path("repositories")
public class RepositoryController {

    private static final String URL_BASE = "https://api.github.com/";

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

        return Response.ok(jsonObject).build();
    }
}

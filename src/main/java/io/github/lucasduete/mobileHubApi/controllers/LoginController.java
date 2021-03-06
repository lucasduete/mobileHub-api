package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

@Path("login")
public class LoginController {

    private static final String CLIENT_ID = "bd2aabdd400a435308b3";
    private static final String CLIENT_SECRET = "7f6ab3e6a29e3acf00be0cac6c8e8bf48ef8933d";

    @POST
    @Path("basic/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response basic(@FormParam("username") String username, @FormParam("password") String password) {

        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(username, password);


        Client client = ClientBuilder
                .newBuilder()
                .register(authenticationFeature)
                .build();

        WebTarget webTarget = client.target("https://api.github.com").path("authorizations");

        Response response = webTarget
                .request()
                .post(
                        Entity.json(
                                "{\"note\": \" " + Instant.now().toEpochMilli() + " \"}"
                        )
                );

        if (response.getStatus() != Response.Status.CREATED.getStatusCode())
            return Response.status(Response.Status.UNAUTHORIZED).build();

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        return Response.ok(
                jsonObject.getString("token")
        ).build();
    }

    @GET
    @Path("oauth2")
    @Produces(MediaType.TEXT_PLAIN)
    public Response oauth2() {
        return Response.ok(
                String.format("https://github.com/login/oauth/authorize?client_id=%s", CLIENT_ID)
        ).build();
    }

    @GET
    @Path("redirect")
    @Produces(MediaType.TEXT_HTML)
    public Response redirectOauth(@QueryParam("code") String code) {

        URI uri = null;

        try {
            uri = new URI(
                    String.format("https://mobilehub-api.herokuapp.com/redirectPage.html?code=%s", code)
            );
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        System.out.printf("\n\n%s", uri.toString());

        return Response.seeOther(uri).build();
    }

    @GET
    @Path("authorize")
    @Produces(MediaType.TEXT_PLAIN)
    public Response oauth2Authorize(@QueryParam("code") String code) {

        if (code == null || code.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget webTarget = client.target("https://github.com/")
                .path("login")
                .path("oauth")
                .path("access_token");

        Response response = webTarget
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("code", code)
                .request()
                .header("Accept", MediaType.APPLICATION_JSON)
                .post(null);

        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        String token = gererateToken(jsonObject.getString("access_token"));

        return Response.ok(token).build();
    }

    private String gererateToken(String githubToken) {
        return new TokenManagement().gerarToken(githubToken, 316);
    }

}

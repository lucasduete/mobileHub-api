package io.github.lucasduete.mobileHubApi.controllers;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("login")
public class LoginController {

    private static final String CLIENT_ID = "bd2aabdd400a435308b3";
    private static final String CLIENT_SECRET = "7f6ab3e6a29e3acf00be0cac6c8e8bf48ef8933d";

    @POST
    @Path("basic/")
    public Response basic(@FormParam("username") String username, @FormParam("password") String password) {

        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(username, password);


        Client client = ClientBuilder
                .newBuilder()
                .register(authenticationFeature)
                .build();

        WebTarget webTarget = client.target("https://api.github.com").path("user");

        Response response = webTarget.request().get();

        System.out.println(response.getStatus());
        System.out.println(response.getEntity());

        return response;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("oauth2")
    public Response oauth2() {
        return Response.ok(
                String.format("https://github.com/login/oauth/authorize?client_id=%s", CLIENT_ID)
        ).build();
    }

    @GET
    @Path("authorize")
    public Response oauth2Authorize(@QueryParam("code") String code) {

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

        return response;
    }
}

package io.github.lucasduete.mobileHubApi.controllers;

import com.sun.syndication.io.FeedException;
import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;
import io.github.lucasduete.mobileHubApi.services.implementations.UserService;
import io.github.lucasduete.mobileHubApi.services.interfaces.UserServiceInterface;
import io.github.lucasduete.mobileHubApi.utils.AtomConsumer;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Path("user")
public class UserController {

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@Context SecurityContext securityContext) {

        UserServiceInterface userService = new UserService();
        String token = TokenManagement.getToken(securityContext);

        try {
            return Response.ok(
                    userService.getProfile(token)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{username}")
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {

        UserServiceInterface userService = new UserService();

        try {
            return Response.ok(
                    userService.getUser(username)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("feed")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeed(@FormParam("username") String username, @FormParam("password") String password) {

        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(username, password);

        Response response = ClientBuilder
                .newBuilder()
                .register(authenticationFeature)
                .build()
                .target(MyApplication.URL_BASE)
                .path("feeds")
                .request()
                .get();


        if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode())
            return response;

        String jsonString = response.readEntity(String.class);
        JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();

        List<Feed> userFeed = Collections.emptyList();

        try {
            userFeed = new AtomConsumer().consume(jsonObject.getString("current_user_url"));
        } catch (Exception ex) {
            if (!ex.getMessage().contains("Entity input stream has already been closed."))
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(userFeed).build();
    }

}
package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;
import io.github.lucasduete.mobileHubApi.services.implementations.UserService;
import io.github.lucasduete.mobileHubApi.services.interfaces.UserServiceInterface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

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

}
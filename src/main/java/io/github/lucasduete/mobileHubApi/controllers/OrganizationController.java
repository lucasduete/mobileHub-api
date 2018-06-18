package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("organization")
public class OrganizationController {

    private static final String URL_BASE = MyApplication.URL_BASE;

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyOrganizations(@Context SecurityContext securityContext) {

        Client client = ClientBuilder
                .newBuilder()
                .build();

        WebTarget webTarget = client
                .target(URL_BASE)
                .path("user")
                .path("orgs");

        Response response = webTarget
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .header("Authorization", String.format("bearer %s", TokenManagement.getToken(securityContext)))
                .get();

        return response;
    }
}

package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.services.implementations.RepositoryService;
import io.github.lucasduete.mobileHubApi.services.interfaces.RepositoryServiceInterface;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("repositories")
public class RepositoryController {

    @GET
    @Path("{owner}/{repo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleRepo(@PathParam("owner") String owner, @PathParam("repo") String repo) {

        RepositoryServiceInterface repositoryService = new RepositoryService();

        try {
            return Response.ok(
                    repositoryService.getSingleRepo(owner, repo)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyRepos(@QueryParam("token") String token) {

        RepositoryServiceInterface repositoryService = new RepositoryService();

        try {
            return Response.ok(
                    repositoryService.getMyRepos(token)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("stars")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyStars(@QueryParam("token") String token) {

        RepositoryServiceInterface repositoryService = new RepositoryService();

        try {
            return Response.ok(
                    repositoryService.getMyStars(token)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("keyword") String keyword) {

        if (keyword == null || keyword.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        RepositoryServiceInterface repositoryService = new RepositoryService();

        try {
            return Response.ok(
                    repositoryService.search(keyword)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}

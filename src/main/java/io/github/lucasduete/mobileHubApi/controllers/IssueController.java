package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.services.implementations.IssueService;
import io.github.lucasduete.mobileHubApi.services.interfaces.IssueServiceInterface;

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
import java.io.IOException;
import java.io.StringReader;

@Path("issues")
public class IssueController {

    private static final String URL_BASE = MyApplication.URL_BASE;

    @GET
    @Path("repository")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssuesByRepo(@QueryParam("owner") String owner, @QueryParam("repo") String repo) {

        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        String repository = String.format("%s/%s", owner, repo);
        IssueServiceInterface issueService = new IssueService();

        try {
            return Response.ok(
                    issueService.getIssuesByRepo(repository)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("issue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleIssue(@QueryParam("owner") String owner,
                                   @QueryParam("repo") String repo,
                                   @QueryParam("number") int number) {

        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty() || number >= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        String repository = String.format("%s/%s", owner, repo);
        IssueServiceInterface issueService = new IssueService();

        try {
            return Response.ok(
                    issueService.getIssue(repository, number)
            ).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

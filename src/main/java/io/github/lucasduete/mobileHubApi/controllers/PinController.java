package io.github.lucasduete.mobileHubApi.controllers;

import io.github.lucasduete.mobileHubApi.entities.PinRepository;
import io.github.lucasduete.mobileHubApi.infraSecurity.Security;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("pin")
@Singleton
public class PinController {

    @Context
    private Sse sse;

    private SseBroadcaster broadcaster;
    private List<PinRepository> pinnedRepositories;

    @PostConstruct
    public void init() {
        this.broadcaster = sse.newBroadcaster();
        this.pinnedRepositories = new ArrayList<>();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pinnedRepositories.forEach(pinRepository -> {
                PinRepository updatableRepository = getRepositorie(pinRepository.getName());

                if (!pinRepository.equals(updatableRepository))
                    broadcast(updatableRepository);
            });

        }).start();
    }

    @POST
    @Path("repository")
    @Security
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void pinRepository(@FormParam("name") String repoName, @Context SseEventSink eventSink) {

        pinnedRepositories.add(getRepositorie(repoName));

        eventSink.send(sse.newEvent("Você está Inscrito!"));
        broadcaster.register(eventSink);
    }

    private void broadcast(PinRepository updatedRepository) {
        broadcaster.broadcast(sse.newEvent("Repositório " + updatedRepository.getName() + " Atualizado!"));
    }

    private PinRepository getRepositorie(String repoName) {
        String jsonString = null;
        JsonReader jsonReader = null;

        WebTarget webTarget = ClientBuilder
                .newClient()
                .target("https://api.github.com")
                .path("repos")
                .path(repoName);

        Response queryRepository = webTarget
                .request()
                .get();

        jsonString = queryRepository.readEntity(String.class);
        jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonRepository = jsonReader.readObject();

        Response queryCommits = webTarget
                .path("commits")
                .request()
                .get();

        jsonString = queryCommits.readEntity(String.class);
        jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonCommits = jsonReader.readArray();

        Response queryPulls = webTarget
                .path("pulls")
                .request()
                .get();

        jsonString = queryPulls.readEntity(String.class);
        jsonReader = Json.createReader(new StringReader(jsonString));
        JsonArray jsonPulls = jsonReader.readArray();

        return  new PinRepository(
            repoName,
            jsonPulls.size(),
            jsonCommits.size(),
            jsonRepository.getInt("open_issues_count")
        );
    }

}

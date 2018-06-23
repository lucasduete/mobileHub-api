package io.github.lucasduete.mobileHubApi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lucasduete.mobileHubApi.MyApplication;
import io.github.lucasduete.mobileHubApi.entities.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;

public class UserService implements UserServiceInterface {

    private final WebTarget target;

    public UserService() {
        Client client = ClientBuilder.newClient();

        this.target = client
                .target(MyApplication.URL_BASE);
    }

    @Override
    public User getProfile(String token) throws IOException {

        Response response = target
                .path("user")
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .header("Authorization", String.format("bearer %s", token))
                .get();

        return recuperarUsuario(response);
    }

    @Override
    public User getUser(String username) throws IOException {
        Response response = target
                .path("user")
                .path(username)
                .request()
                .header("Accept", "application/json, application/vnd.github.v3+json")
                .get();

        return recuperarUsuario(response);
    }

    private User recuperarUsuario(Response response) throws IOException {
        String jsonString = response.readEntity(String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonObject.toString(), User.class);
    }
}

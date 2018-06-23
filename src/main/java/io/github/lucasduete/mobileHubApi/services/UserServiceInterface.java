package io.github.lucasduete.mobileHubApi.services;

import io.github.lucasduete.mobileHubApi.entities.User;

import java.io.IOException;

public interface UserServiceInterface {

    public User getProfile(String token) throws IOException;
    public User getUser(String username) throws IOException;
}

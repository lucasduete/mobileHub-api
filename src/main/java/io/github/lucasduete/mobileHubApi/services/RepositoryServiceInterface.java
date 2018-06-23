package io.github.lucasduete.mobileHubApi.services;

import io.github.lucasduete.mobileHubApi.entities.Repository;

import java.io.IOException;
import java.util.List;

public interface RepositoryServiceInterface {

    public List<Repository> getMyRepos(String token) throws IOException;

    public List<Repository> getMyStars(String token) throws IOException;

    public List<Repository> search(String keyword) throws IOException;

}

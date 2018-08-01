package io.github.lucasduete.mobileHubApi.services.interfaces;

import io.github.lucasduete.mobileHubApi.entities.Repository;

import java.io.IOException;
import java.util.List;

public interface RepositoryServiceInterface {

    public Repository getSingleRepo(String owner, String repo) throws IOException;

    public List<Repository> getMyRepos(String token) throws IOException;

    public List<Repository> getMyStars(String token) throws IOException;

    public List<Repository> search(String keyword) throws IOException;
}

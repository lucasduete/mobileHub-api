package io.github.lucasduete.mobileHubApi.services.interfaces;

import io.github.lucasduete.mobileHubApi.entities.Issue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IssueServiceInterface {

    public Issue getIssue(String repository, int issueNumber) throws IOException;

    public List<Issue> getIssuesByRepo(String repository) throws IOException;
}

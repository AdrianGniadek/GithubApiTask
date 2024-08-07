package pl.adriangniadek.githubapitask.service;

import pl.adriangniadek.githubapitask.records.Branch;
import pl.adriangniadek.githubapitask.records.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    private static final String GITHUB_API_URL = "https://api.github.com";

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Repository> getUserRepositories(String username) {
        String repositoriesUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment("users", username, "repos")
                .toUriString();

        Repository[] repositories = restTemplate.getForObject(repositoriesUrl, Repository[].class);

        if (repositories == null) {
            throw new IllegalArgumentException("User not found or has no repositories");
        }

        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(repo -> new Repository(
                        repo.repositoryName(),
                        repo.owner(),
                        false,
                        getBranches(username, repo.repositoryName())
                ))
                .toList();
    }

    private List<Branch> getBranches(String username, String repositoryName) {
        String branchesUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment("repos", username, repositoryName, "branches")
                .toUriString();

        Branch[] branches = restTemplate.getForObject(branchesUrl, Branch[].class);
        return branches != null ? Arrays.asList(branches) : List.of();
    }
}

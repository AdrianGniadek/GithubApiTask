package pl.adriangniadek.githubapitask.service;

import pl.adriangniadek.githubapitask.dto.BranchDto;
import pl.adriangniadek.githubapitask.dto.RepositoryDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GITHUB_API_URL = "https://api.github.com";

    public List<RepositoryDto> getUserRepositories(String username) throws IllegalArgumentException {
        String repositoriesUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment("users", username, "repos")
                .toUriString();

        RepositoryDto[] repositories = restTemplate.getForObject(repositoriesUrl, RepositoryDto[].class);

        if (repositories == null) {
            throw new IllegalArgumentException("User not found or has no repositories");
        }

        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    repo.setBranches(getBranches(username, repo.getRepositoryName()));
                    return repo;
                })
                .toList();
    }

    private List<BranchDto> getBranches(String username, String repositoryName) {
        String branchesUrl = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment("repos", username, repositoryName, "branches")
                .toUriString();

        BranchDto[] branches = restTemplate.getForObject(branchesUrl, BranchDto[].class);
        return branches != null ? Arrays.asList(branches) : List.of();
    }
}

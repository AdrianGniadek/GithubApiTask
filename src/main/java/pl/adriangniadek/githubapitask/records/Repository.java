package pl.adriangniadek.githubapitask.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Repository(
        @JsonProperty("name") String repositoryName,
        @JsonProperty("owner") OwnerDto owner,
        @JsonProperty("fork") boolean isFork,
        List<Branch> branches
) {

    public record OwnerDto(String login) {
    }
}
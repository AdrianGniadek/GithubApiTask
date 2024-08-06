package pl.adriangniadek.githubapitask.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RepositoryDto {

    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private OwnerDto owner;

    @JsonProperty("fork")
    private boolean isFork;

    private List<BranchDto> branches;

    @Data
    public static class OwnerDto {
        private String login;
    }
}
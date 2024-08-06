package pl.adriangniadek.githubapitask.dto;

import lombok.Data;

@Data
public class BranchDto {

    private String name;
    private CommitDto commit;

    @Data
    public static class CommitDto {
        private String sha;
    }
}
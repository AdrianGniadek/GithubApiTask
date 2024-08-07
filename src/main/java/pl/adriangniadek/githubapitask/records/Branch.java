package pl.adriangniadek.githubapitask.records;

public record Branch(String name, CommitDto commit) {

    public record CommitDto(String sha) {
    }
}
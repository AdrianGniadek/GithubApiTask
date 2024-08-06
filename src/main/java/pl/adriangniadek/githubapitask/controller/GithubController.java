package pl.adriangniadek.githubapitask.controller;

import pl.adriangniadek.githubapitask.dto.ErrorResponse;
import pl.adriangniadek.githubapitask.dto.RepositoryDto;
import pl.adriangniadek.githubapitask.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username,
                                                 @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader) {
        if (!"application/json".equals(acceptHeader)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only application/json is supported");
        }

        try {
            List<RepositoryDto> repositories = githubService.getUserRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}

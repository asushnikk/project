package training.project.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import training.project.domain.Score;
import training.project.domain.User;
import training.project.repository.ScoreRepository;
import java.util.Optional;

@Component
@AllArgsConstructor
public class MySecurityService {

    private ScoreRepository scoreRepository;

    public boolean hasScoreOwnerPermission(Object principal, Long scoreId) {
        User user = (User) principal;
        Optional<Score> score = scoreRepository.findById(scoreId);

        return score
                .map(w -> w.getOwner().getId().equals(user.getId()))
                .orElse(false);
    }

    public boolean hasScoreUserPermission(Authentication authentication, Long scoreId) {
        User user = (User) authentication.getPrincipal();
        Optional<Score> score = scoreRepository.findById(scoreId);

        return score
                .map(w -> w.getUsers()
                        .stream()
                        .anyMatch(item -> item.getId().equals(user.getId()))
                )
                .orElse(false);
    }
}
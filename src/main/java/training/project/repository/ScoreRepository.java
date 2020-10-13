package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.domain.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
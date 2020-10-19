package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training.project.domain.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
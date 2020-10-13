package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.domain.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}

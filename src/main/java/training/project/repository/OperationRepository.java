package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training.project.domain.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}

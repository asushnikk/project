package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
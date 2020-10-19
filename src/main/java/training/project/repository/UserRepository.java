package training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training.project.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
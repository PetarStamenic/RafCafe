package rs.raf.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.raf.userservice.model.User;
import java.util.List;
import rs.raf.userservice.model.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByEmail(String email);
}

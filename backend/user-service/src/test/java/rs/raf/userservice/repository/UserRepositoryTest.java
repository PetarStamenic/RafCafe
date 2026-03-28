package rs.raf.userservice.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import rs.raf.userservice.model.User;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    void shouldFindUserByUsername() {
        Optional<User> user = repo.findByUsername("stiboly");
        assertTrue(user.isPresent());
    }

    @Test
    void shouldFindUsersByRole() {
        List<User> users = repo.findAllByRole(User.Role.ADMINISTRATOR);
        assertFalse(users.isEmpty());
    }

    @Test
    void shouldFindUsersByEmail() {
        List<User> users = repo.findAllByEmail("whatever@gmail.com");
        assertFalse(users.isEmpty());
    }
}

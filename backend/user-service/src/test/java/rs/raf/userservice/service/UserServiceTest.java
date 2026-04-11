package rs.raf.userservice.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataIntegrityViolationException;

import rs.raf.userservice.dto.user.RegisterUserRequestDTO;
import rs.raf.userservice.dto.user.UserResponseDTO;
import rs.raf.userservice.error.UserAlreadyExistsException;
import rs.raf.userservice.error.UserNotFoundException;
import rs.raf.userservice.mapper.UserMapper;
import rs.raf.userservice.model.User;
import rs.raf.userservice.model.UserRole;
import rs.raf.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repo;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    private User buildUser(Long id, String username) {
        return new User()
            .setId(id)
            .setUsername(username)
            .setPassword("secret")
            .setEmail(username + "@example.com")
            .setFirstName("John")
            .setLastName("Doe")
            .setIpAddress("127.0.0.1")
            .setRole(UserRole.CUSTOMER);
    }

    private UserResponseDTO buildDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole()
        );
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = buildUser(1L, "alice");
        User user2 = buildUser(2L, "bob");
        UserResponseDTO dto1 = buildDTO(user1);
        UserResponseDTO dto2 = buildDTO(user2);

        when(repo.findAll()).thenReturn(List.of(user1, user2));
        when(mapper.toDTO(user1)).thenReturn(dto1);
        when(mapper.toDTO(user2)).thenReturn(dto2);

        List<UserResponseDTO> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void shouldReturnEmptyListWhenNoUsers() {
        when(repo.findAll()).thenReturn(List.of());

        List<UserResponseDTO> result = service.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnUserById() {
        User user = buildUser(1L, "alice");
        UserResponseDTO dto = buildDTO(user);

        when(repo.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toDTO(user)).thenReturn(dto);

        UserResponseDTO result = service.getById(1L);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowWhenUserByIdNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(
            UserNotFoundException.class,
            () -> service.getById(99L)
        );

        assertEquals("User with id 99 not found", ex.getMessage());
    }

    @Test
    void shouldReturnUserByUsername() {
        User user = buildUser(1L, "alice");
        UserResponseDTO dto = buildDTO(user);

        when(repo.findByUsername("alice")).thenReturn(Optional.of(user));
        when(mapper.toDTO(user)).thenReturn(dto);

        UserResponseDTO result = service.getByUsername("alice");

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowWhenUserByUsernameNotFound() {
        when(repo.findByUsername("ghost")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(
            UserNotFoundException.class,
            () -> service.getByUsername("ghost")
        );

        assertEquals("User with username ghost not found", ex.getMessage());
    }

    @Test
    void shouldRegisterUserAndSetIpAddress() {
        RegisterUserRequestDTO dto = new RegisterUserRequestDTO(
            "alice", "secret", "alice@example.com", "Alice", "Smith"
        );
        User unsaved = buildUser(null, "alice").setIpAddress(null);
        User saved = buildUser(1L, "alice");
        UserResponseDTO responseDTO = buildDTO(saved);

        when(mapper.toEntity(dto)).thenReturn(unsaved);
        when(repo.save(unsaved)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(responseDTO);

        UserResponseDTO result = service.registerUser(dto, "192.168.1.1");

        assertEquals("192.168.1.1", unsaved.getIpAddress());
        assertEquals(responseDTO, result);
        verify(repo).save(unsaved);
    }

    @Test
    void shouldThrowWhenRegisteringDuplicateUser() {
        RegisterUserRequestDTO dto = new RegisterUserRequestDTO(
            "alice", "secret", "alice@example.com", "Alice", "Smith"
        );
        User unsaved = buildUser(null, "alice").setIpAddress(null);

        when(mapper.toEntity(dto)).thenReturn(unsaved);
        when(repo.save(unsaved)).thenThrow(new DataIntegrityViolationException("duplicate"));

        UserAlreadyExistsException ex = assertThrows(
            UserAlreadyExistsException.class,
            () -> service.registerUser(dto, "192.168.1.1")
        );

        assertEquals("User with username alice already exists", ex.getMessage());
    }
}

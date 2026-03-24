package rs.raf.cafe.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.raf.cafe.user.dto.*;
import rs.raf.cafe.user.exception.BadRequestException;
import rs.raf.cafe.user.exception.ResourceNotFoundException;
import rs.raf.cafe.user.model.Role;
import rs.raf.cafe.user.model.User;
import rs.raf.cafe.user.repository.UserRepository;
import rs.raf.cafe.user.security.JwtUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User buildUser() {
        return User.builder()
                .id(1L)
                .email("john@example.com")
                .password("encodedPassword")
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .loyaltyPoints(0)
                .active(true)
                .build();
    }

    @Test
    void register_success() {
        RegisterRequest request = RegisterRequest.builder()
                .email("john@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(buildUser());

        UserProfileResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("john@example.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("CUSTOMER", response.getRole());
        assertEquals(0, response.getLoyaltyPoints());

        verify(userRepository).existsByEmail("john@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicateEmail_throwsException() {
        RegisterRequest request = RegisterRequest.builder()
                .email("john@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.register(request));

        assertTrue(exception.getMessage().contains("Email already exists"));
        verify(userRepository).existsByEmail("john@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_success() {
        LoginRequest request = LoginRequest.builder()
                .email("john@example.com")
                .password("password123")
                .build();

        User user = buildUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        LoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());

        verify(userRepository).findByEmail("john@example.com");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(jwtUtil).generateToken(user);
    }

    @Test
    void login_wrongPassword_throwsException() {
        LoginRequest request = LoginRequest.builder()
                .email("john@example.com")
                .password("wrongPassword")
                .build();

        User user = buildUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.login(request));

        assertTrue(exception.getMessage().contains("Invalid email or password"));
        verify(jwtUtil, never()).generateToken(any(User.class));
    }

    @Test
    void getProfile_success() {
        User user = buildUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        UserProfileResponse response = userService.getProfile("john@example.com");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("CUSTOMER", response.getRole());
        assertEquals(0, response.getLoyaltyPoints());
    }

    @Test
    void getProfile_notFound_throwsException() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.getProfile("unknown@example.com"));

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void updateProfile_success() {
        UserProfileUpdateRequest request = UserProfileUpdateRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .build();

        User user = buildUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User updatedUser = buildUser();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserProfileResponse response = userService.updateProfile("john@example.com", request);

        assertNotNull(response);
        assertEquals("Jane", response.getFirstName());
        assertEquals("Smith", response.getLastName());
        assertEquals("john@example.com", response.getEmail());

        verify(userRepository).findByEmail("john@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addLoyaltyPoints_success() {
        User user = buildUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addLoyaltyPoints("john@example.com", 50);

        assertEquals(50, user.getLoyaltyPoints());
        verify(userRepository).findByEmail("john@example.com");
        verify(userRepository).save(user);
    }
}

package rs.raf.userservice.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import rs.raf.userservice.dto.user.*;
import rs.raf.userservice.error.PasswordMismatchException;
import rs.raf.userservice.error.UserAlreadyExistsException;
import rs.raf.userservice.error.UserNotFoundException;
import rs.raf.userservice.mapper.UserMapper;
import rs.raf.userservice.model.User;
import rs.raf.userservice.repository.UserRepository;
import rs.raf.userservice.util.JWTUtil;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final JWTUtil jwt;
    private final BCryptPasswordEncoder passwords;

    public UserService(
        UserRepository repo,
        UserMapper mapper,
        JWTUtil jwt,
        BCryptPasswordEncoder passwords
    ) {
        this.repo = repo;
        this.mapper = mapper;
        this.jwt = jwt;
        this.passwords = passwords;
    }

    public List<UserResponseDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    public UserResponseDTO getById(Long id) {
        return repo.findById(id)
            .map(mapper::toDTO)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public UserResponseDTO getByUsername(String username) {
        return repo.findByUsername(username)
            .map(mapper::toDTO)
            .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    public UserResponseDTO registerUser(RegisterUserRequestDTO dto, String ip) {
        User user = mapper.toEntity(dto);
        user.setIpAddress(ip);
        try {
            user = repo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with username " + dto.username() + " already exists");
        }
        return mapper.toDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UpdateUserRequestDTO dto, String ip) {
        User user = repo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if (
                dto.oldPassword() == null ||
                dto.newPassword() == null ||
                dto.oldPassword().equals(dto.newPassword()) ||
                !passwords.matches(dto.oldPassword(), user.getPassword())
        ) {
            throw new PasswordMismatchException("Old password does not match or new password is the same as old password");
        }

        user.setPassword(passwords.encode(dto.newPassword()));
        if (dto.username() != null) user.setUsername(dto.username());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        user.setIpAddress(ip);

        try {
            user = repo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with username " + dto.username() + " already exists");
        }

        return mapper.toDTO(user);
    }

    public void deleteUser(Long id) {
        User user = repo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        repo.delete(user);
    }

    public LoginResponseDTO loginUser(LoginRequestDTO dto) {
        User user = repo.findByUsername(dto.username())
            .orElseThrow(() -> new UserNotFoundException("Username and password do not match"));

        if (!passwords.matches(dto.password(), user.getPassword())) {
            throw new UserNotFoundException("Username and password do not match");
        }

        String token = jwt.issue(user.getUsername());
        return new LoginResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getRole(),
            token
        );
    }
}

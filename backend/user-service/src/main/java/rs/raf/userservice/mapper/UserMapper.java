package rs.raf.userservice.mapper;

import org.springframework.stereotype.Component;

import rs.raf.userservice.dto.user.RegisterUserRequestDTO;
import rs.raf.userservice.dto.user.UserResponseDTO;
import rs.raf.userservice.model.User;
import rs.raf.userservice.model.UserRole;

@Component
public class UserMapper {
    public User toEntity(RegisterUserRequestDTO dto) {
        return new User()
            .setUsername(dto.username())
            .setPassword(dto.password()) // TODO: hash passwords
            .setEmail(dto.email())
            .setFirstName(dto.firstName())
            .setLastName(dto.lastName())
            .setRole(UserRole.CUSTOMER);
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole().name()
        );
    }
}

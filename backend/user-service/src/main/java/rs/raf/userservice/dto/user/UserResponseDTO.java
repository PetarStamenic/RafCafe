package rs.raf.userservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import rs.raf.userservice.model.UserRole;

@Schema(description = "Data Transfer Object for user response")
public record UserResponseDTO(
    @Schema(description = "Unique identifier of the user", example = "1")
    Long id,
    @Schema(description = "Username of the user", example = "john_doe")
    String username,
    @Schema(description = "Email of the new user", example = "jdoe@example.com")
    String email,
    @Schema(description = "First name of the user", example = "John")
    String firstName,
    @Schema(description = "Last name of the user", example = "Doe")
    String lastName,
    @Schema(description = "Role of the user", example = "CUSTOMER")
    UserRole role
) {}

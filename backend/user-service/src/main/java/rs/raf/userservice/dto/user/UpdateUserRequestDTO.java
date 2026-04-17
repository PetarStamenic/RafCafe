package rs.raf.userservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for updating a user")
public record UpdateUserRequestDTO(
    @Schema(description = "Username of the user to update", example = "john_doe")
    String username,
    @Schema(description = "Old password of the user", example = "P@ssw0rd!")
    @Size(min=8)
    String oldPassword,
    @Schema(description = "New password of the user", example = "N3wP@ssw0rd!")
    @Size(min=8)
    String newPassword,
    @Schema(description = "Email of the user", example = "john@gmail.com")
    @Email(message = "Email should be valid")
    String email,
    @Schema(description = "First name of the user", example = "John")
    String firstName,
    @Schema(description = "Last name of the user", example = "Doe")
    String lastName
) {}

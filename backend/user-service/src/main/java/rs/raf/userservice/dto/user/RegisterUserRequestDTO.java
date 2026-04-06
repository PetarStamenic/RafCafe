package rs.raf.userservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for registering a new user")
public record RegisterUserRequestDTO(
    @Schema(description = "Username of the new user", example = "john_doe")
    @NotBlank
    String username,
    @Schema(description = "Password of the new user", example = "P@ssw0rd!")
    @NotBlank
    String password,
    @Schema(description = "Email of the new user", example = "jdoe@example.com")
    @NotBlank
    @Email
    String email,
    @Schema(description = "First name of the new user", example = "John")
    String firstName,
    @Schema(description = "Last name of the new user", example = "Doe")
    String lastName
) {}

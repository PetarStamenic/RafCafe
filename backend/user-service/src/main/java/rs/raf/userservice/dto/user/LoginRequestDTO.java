package rs.raf.userservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for user login request")
public record LoginRequestDTO(
    @Schema(description = "Username of the user", example = "john_doe")
    @NotBlank
    String username,
    @Schema(description = "Password of the user", example = "P@ssw0rd")
    @NotBlank
    @Size(min = 8)
    String password
) {}

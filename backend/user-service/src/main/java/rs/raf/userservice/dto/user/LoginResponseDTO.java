package rs.raf.userservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import rs.raf.userservice.model.UserRole;

@Schema(description = "DTO for user login response")
public record LoginResponseDTO(
    @Schema(description = "User ID", example = "1")
    Long id,
    @Schema(description = "Username", example = "john_doe")
    String username,
    @Schema(description = "User role", example = "CUSTOMER")
    UserRole role,
    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String jwt
) {}

package rs.raf.userservice.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    int status,
    LocalDateTime timestamp,
    String message
) {}

package rs.raf.userservice.error;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rs.raf.userservice.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(404, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildResponse(409, ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handlePasswordMismatchException(PasswordMismatchException ex) {
        return buildResponse(401, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation failed:\n");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(String.format("Field '%s': %s\n", error.getField(), error.getDefaultMessage()));
        });
        return buildResponse(400, errorMessage.toString().trim());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return buildResponse(500, "An unexpected error occurred");
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(int status, String message) {
        ErrorResponseDTO dto = new ErrorResponseDTO(status, LocalDateTime.now(), message);
        return ResponseEntity.status(status).body(dto);
    }
}

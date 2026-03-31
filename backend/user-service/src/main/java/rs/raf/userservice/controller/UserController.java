package rs.raf.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import rs.raf.userservice.dto.user.RegisterUserRequestDTO;
import rs.raf.userservice.dto.user.UserResponseDTO;
import rs.raf.userservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "204", description = "No content")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        var response = service.getAll();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Parameter(name = "id", description = "ID of the user to retrieve", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        var response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Parameter(name = "username", description = "Username of the user to retrieve", required = true)
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getByUsername(@PathVariable String username) {
        var response = service.getByUsername(username);
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration data", required = true)
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody @Valid RegisterUserRequestDTO requestDTO,
            HttpServletRequest httpRequest
    ) {
        String ip = httpRequest.getRemoteAddr();
        var response = service.registerUser(requestDTO, ip);
        return ResponseEntity.created(null).body(response);
    }
}

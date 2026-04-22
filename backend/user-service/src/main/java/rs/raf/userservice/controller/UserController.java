package rs.raf.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import rs.raf.userservice.dto.user.*;
import rs.raf.userservice.service.UserService;

@Tag(name = "User Controller", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        var response = service.getAll();
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
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration data", required = true)
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody @Valid RegisterUserRequestDTO dto,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();
        var response = service.registerUser(dto, ip);
        return ResponseEntity.status(201).body(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @Parameter(name = "id", description = "ID of the user to update", required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User update data", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDTO dto,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();
        var response = service.updateUser(id, dto, ip);
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @Parameter(name = "id", description = "ID of the user to delete", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login data", required = true)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @RequestBody @Valid LoginRequestDTO dto,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();
        var response = service.loginUser(dto, ip);
        return ResponseEntity.ok(response);
    }
}

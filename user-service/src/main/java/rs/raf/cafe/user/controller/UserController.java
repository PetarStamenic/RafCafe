package rs.raf.cafe.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.raf.cafe.user.dto.UserProfileResponse;
import rs.raf.cafe.user.dto.UserProfileUpdateRequest;
import rs.raf.cafe.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();
        UserProfileResponse response = userService.getProfile(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(Authentication authentication,
                                                              @Valid @RequestBody UserProfileUpdateRequest request) {
        String email = authentication.getName();
        UserProfileResponse response = userService.updateProfile(email, request);
        return ResponseEntity.ok(response);
    }
}

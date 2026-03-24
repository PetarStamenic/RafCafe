package rs.raf.cafe.notification.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.cafe.notification.dto.NotificationRequest;
import rs.raf.cafe.notification.dto.NotificationResponse;
import rs.raf.cafe.notification.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestParam(required = false) String email) {
        List<NotificationResponse> notifications;
        if (email != null && !email.isBlank()) {
            notifications = notificationService.getNotificationsByUser(email);
        } else {
            notifications = notificationService.getAllNotifications();
        }
        return ResponseEntity.ok(notifications);
    }
}

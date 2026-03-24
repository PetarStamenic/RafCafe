package rs.raf.cafe.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.raf.cafe.notification.dto.NotificationRequest;
import rs.raf.cafe.notification.dto.NotificationResponse;
import rs.raf.cafe.notification.model.Notification;
import rs.raf.cafe.notification.model.NotificationStatus;
import rs.raf.cafe.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .userEmail(request.getUserEmail())
                .type(request.getType())
                .subject(request.getSubject())
                .message(request.getMessage())
                .status(NotificationStatus.PENDING)
                .build();

        boolean emailSent = emailService.sendEmail(
                request.getUserEmail(),
                request.getSubject(),
                request.getMessage()
        );

        notification.setSentAt(LocalDateTime.now());
        notification.setStatus(emailSent ? NotificationStatus.SENT : NotificationStatus.FAILED);

        Notification saved = notificationRepository.save(notification);
        return mapToResponse(saved);
    }

    public List<NotificationResponse> getNotificationsByUser(String email) {
        return notificationRepository.findByUserEmail(email).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userEmail(notification.getUserEmail())
                .type(notification.getType())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .sentAt(notification.getSentAt())
                .status(notification.getStatus())
                .build();
    }
}

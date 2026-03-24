package rs.raf.cafe.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.cafe.notification.model.NotificationStatus;
import rs.raf.cafe.notification.model.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;
    private String userEmail;
    private NotificationType type;
    private String subject;
    private String message;
    private LocalDateTime sentAt;
    private NotificationStatus status;
}

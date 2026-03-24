package rs.raf.cafe.notification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.raf.cafe.notification.dto.NotificationRequest;
import rs.raf.cafe.notification.dto.NotificationResponse;
import rs.raf.cafe.notification.model.Notification;
import rs.raf.cafe.notification.model.NotificationStatus;
import rs.raf.cafe.notification.model.NotificationType;
import rs.raf.cafe.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationRequest request;
    private Notification savedNotification;

    @BeforeEach
    void setUp() {
        request = NotificationRequest.builder()
                .userEmail("user@example.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Order Confirmed")
                .message("Your order has been confirmed.")
                .build();

        savedNotification = Notification.builder()
                .id(1L)
                .userEmail("user@example.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Order Confirmed")
                .message("Your order has been confirmed.")
                .sentAt(LocalDateTime.now())
                .status(NotificationStatus.SENT)
                .build();
    }

    @Test
    void sendNotification_whenEmailSentSuccessfully_shouldReturnSentStatus() {
        when(emailService.sendEmail(eq("user@example.com"), eq("Order Confirmed"),
                eq("Your order has been confirmed."))).thenReturn(true);
        when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);

        NotificationResponse response = notificationService.sendNotification(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUserEmail()).isEqualTo("user@example.com");
        assertThat(response.getType()).isEqualTo(NotificationType.ORDER_CONFIRMATION);
        assertThat(response.getStatus()).isEqualTo(NotificationStatus.SENT);
        verify(emailService).sendEmail("user@example.com", "Order Confirmed",
                "Your order has been confirmed.");
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void sendNotification_whenEmailFails_shouldReturnFailedStatus() {
        Notification failedNotification = Notification.builder()
                .id(2L)
                .userEmail("user@example.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Order Confirmed")
                .message("Your order has been confirmed.")
                .sentAt(LocalDateTime.now())
                .status(NotificationStatus.FAILED)
                .build();

        when(emailService.sendEmail(eq("user@example.com"), eq("Order Confirmed"),
                eq("Your order has been confirmed."))).thenReturn(false);
        when(notificationRepository.save(any(Notification.class))).thenReturn(failedNotification);

        NotificationResponse response = notificationService.sendNotification(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(NotificationStatus.FAILED);
        verify(emailService).sendEmail("user@example.com", "Order Confirmed",
                "Your order has been confirmed.");
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void getNotificationsByUser_shouldReturnUserNotifications() {
        Notification notification = Notification.builder()
                .id(1L)
                .userEmail("user@example.com")
                .type(NotificationType.REGISTRATION)
                .subject("Welcome")
                .message("Welcome to RafCafe!")
                .sentAt(LocalDateTime.now())
                .status(NotificationStatus.SENT)
                .build();

        when(notificationRepository.findByUserEmail("user@example.com"))
                .thenReturn(List.of(notification));

        List<NotificationResponse> responses = notificationService
                .getNotificationsByUser("user@example.com");

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getUserEmail()).isEqualTo("user@example.com");
        assertThat(responses.get(0).getSubject()).isEqualTo("Welcome");
        verify(notificationRepository).findByUserEmail("user@example.com");
    }

    @Test
    void getAllNotifications_shouldReturnAllNotifications() {
        Notification notification1 = Notification.builder()
                .id(1L)
                .userEmail("user1@example.com")
                .type(NotificationType.REGISTRATION)
                .subject("Welcome")
                .message("Welcome!")
                .sentAt(LocalDateTime.now())
                .status(NotificationStatus.SENT)
                .build();

        Notification notification2 = Notification.builder()
                .id(2L)
                .userEmail("user2@example.com")
                .type(NotificationType.PROMOTIONAL)
                .subject("Sale")
                .message("Big sale!")
                .sentAt(LocalDateTime.now())
                .status(NotificationStatus.SENT)
                .build();

        when(notificationRepository.findAll()).thenReturn(List.of(notification1, notification2));

        List<NotificationResponse> responses = notificationService.getAllNotifications();

        assertThat(responses).hasSize(2);
        verify(notificationRepository).findAll();
    }
}

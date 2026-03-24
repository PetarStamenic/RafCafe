package rs.raf.cafe.notification.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendEmail_whenSuccessful_shouldReturnTrue() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        boolean result = emailService.sendEmail("user@example.com", "Test Subject", "Test Body");

        assertThat(result).isTrue();
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_whenExceptionThrown_shouldReturnFalse() {
        doThrow(new MailSendException("SMTP connection failed"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        boolean result = emailService.sendEmail("user@example.com", "Test Subject", "Test Body");

        assertThat(result).isFalse();
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}

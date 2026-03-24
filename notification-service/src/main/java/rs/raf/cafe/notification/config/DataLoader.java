package rs.raf.cafe.notification.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.raf.cafe.notification.model.Notification;
import rs.raf.cafe.notification.model.NotificationStatus;
import rs.raf.cafe.notification.model.NotificationType;
import rs.raf.cafe.notification.repository.NotificationRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final NotificationRepository notificationRepository;

    @Override
    public void run(String... args) {
        if (notificationRepository.count() > 0) {
            return;
        }

        notificationRepository.save(Notification.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .type(NotificationType.REGISTRATION)
                .subject("Dobrodosli u RafCafe!")
                .message("Postovana Ana, Vas nalog je uspesno kreiran. Uzivajte u nasoj ponudi!")
                .sentAt(LocalDateTime.now().minusDays(10))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("stefan.markovic@gmail.com")
                .type(NotificationType.REGISTRATION)
                .subject("Dobrodosli u RafCafe!")
                .message("Postovani Stefane, Vas nalog je uspesno kreiran. Dobrodosli u RafCafe porodicu!")
                .sentAt(LocalDateTime.now().minusDays(8))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("milica.djordjevic@gmail.com")
                .type(NotificationType.REGISTRATION)
                .subject("Dobrodosli u RafCafe!")
                .message("Postovana Milice, Vas nalog je uspesno kreiran. Posetite nas meni!")
                .sentAt(LocalDateTime.now().minusDays(7))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Potvrda narudzbine #1")
                .message("Vasa narudzbina #1 (Kapucino, Palacinka sa nutellom) je primljena. Hvala na poverenju!")
                .sentAt(LocalDateTime.now().minusDays(3))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("stefan.markovic@gmail.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Potvrda narudzbine #2")
                .message("Vasa narudzbina #2 (Domaca kafa x2, Burek sa sirom, Cheesecake) je primljena.")
                .sentAt(LocalDateTime.now().minusDays(2))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .type(NotificationType.ORDER_STATUS_CHANGE)
                .subject("Narudzbina #1 - isporucena")
                .message("Vasa narudzbina #1 je uspesno isporucena. Prijatno!")
                .sentAt(LocalDateTime.now().minusDays(3).plusHours(1))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("milica.djordjevic@gmail.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Potvrda narudzbine #3")
                .message("Vasa narudzbina #3 (Zeleni caj, Kroasan sa cokoladom, Tiramisu) je primljena i priprema se.")
                .sentAt(LocalDateTime.now().minusHours(1))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("lazar.nikolic@gmail.com")
                .type(NotificationType.ORDER_CONFIRMATION)
                .subject("Potvrda narudzbine #4")
                .message("Vasa narudzbina #4 (Klub sendvic, Cedjena pomorandza, Tiramisu) je primljena.")
                .sentAt(LocalDateTime.now().minusMinutes(15))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("jovana.stojanovic@gmail.com")
                .type(NotificationType.ORDER_STATUS_CHANGE)
                .subject("Narudzbina #5 - spremna")
                .message("Vasa narudzbina #5 je spremna za preuzimanje! Dodite na sank.")
                .sentAt(LocalDateTime.now().minusMinutes(10))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("ana.ivanovic@gmail.com")
                .type(NotificationType.ORDER_CANCELLED)
                .subject("Narudzbina #6 - otkazana")
                .message("Vasa narudzbina #6 je uspesno otkazana. Iznos ce biti vracen na Vas racun.")
                .sentAt(LocalDateTime.now().minusDays(1))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("lazar.nikolic@gmail.com")
                .type(NotificationType.PROMOTIONAL)
                .subject("Vikend akcija u RafCafe!")
                .message("Postovani Lazare, ovog vikenda svi deserti su na 20% popusta! Posetite nas i uzivajte u slatkom.")
                .sentAt(LocalDateTime.now().minusDays(1))
                .status(NotificationStatus.SENT)
                .build());

        notificationRepository.save(Notification.builder()
                .userEmail("jovana.stojanovic@gmail.com")
                .type(NotificationType.PROMOTIONAL)
                .subject("Nova sezonska ponuda!")
                .message("Postovana Jovana, probajte nase nove zimske napitke - Irski kapucino i Planinski caj vas cekaju!")
                .sentAt(LocalDateTime.now().minusHours(5))
                .status(NotificationStatus.SENT)
                .build());
    }
}

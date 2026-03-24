package rs.raf.cafe.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cafe.notification.model.Notification;
import rs.raf.cafe.notification.model.NotificationType;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserEmail(String userEmail);

    List<Notification> findByType(NotificationType type);
}

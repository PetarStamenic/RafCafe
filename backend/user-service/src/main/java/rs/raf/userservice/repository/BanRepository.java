package rs.raf.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.raf.userservice.model.Ban;

public interface BanRepository extends JpaRepository<Ban, Long> {
    boolean existsByIpAddress(String ipAddress);

    Optional<Ban> findByIpAddress(String ipAddress);
}

package rs.raf.cafe.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.cafe.user.model.Role;
import rs.raf.cafe.user.model.User;
import rs.raf.cafe.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(User.builder()
                .email("admin@rafcafe.rs")
                .password(passwordEncoder.encode("admin123"))
                .firstName("Nikola")
                .lastName("Petrovic")
                .role(Role.ADMIN)
                .loyaltyPoints(0)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("manager@rafcafe.rs")
                .password(passwordEncoder.encode("manager123"))
                .firstName("Marko")
                .lastName("Jovanovic")
                .role(Role.MANAGER)
                .loyaltyPoints(0)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("ana.ivanovic@gmail.com")
                .password(passwordEncoder.encode("ana123"))
                .firstName("Ana")
                .lastName("Ivanovic")
                .role(Role.CUSTOMER)
                .loyaltyPoints(150)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("stefan.markovic@gmail.com")
                .password(passwordEncoder.encode("stefan123"))
                .firstName("Stefan")
                .lastName("Markovic")
                .role(Role.CUSTOMER)
                .loyaltyPoints(320)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("milica.djordjevic@gmail.com")
                .password(passwordEncoder.encode("milica123"))
                .firstName("Milica")
                .lastName("Djordjevic")
                .role(Role.CUSTOMER)
                .loyaltyPoints(75)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("lazar.nikolic@gmail.com")
                .password(passwordEncoder.encode("lazar123"))
                .firstName("Lazar")
                .lastName("Nikolic")
                .role(Role.CUSTOMER)
                .loyaltyPoints(500)
                .active(true)
                .build());

        userRepository.save(User.builder()
                .email("jovana.stojanovic@gmail.com")
                .password(passwordEncoder.encode("jovana123"))
                .firstName("Jovana")
                .lastName("Stojanovic")
                .role(Role.CUSTOMER)
                .loyaltyPoints(210)
                .active(true)
                .build());
    }
}

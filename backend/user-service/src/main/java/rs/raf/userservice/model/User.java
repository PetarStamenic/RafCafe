package rs.raf.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User {
    public enum Role {
        ADMINISTRATOR,
        MANAGER,
        CUSTOMER,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String ipAddress;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}

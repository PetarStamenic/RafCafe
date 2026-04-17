package rs.raf.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.raf.userservice.model.User;
import rs.raf.userservice.repository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {
    private final UserRepository repo;

    public CustomUserService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

        return new CustomUserDetails(user);
    }
}

package rs.raf.userservice.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rs.raf.userservice.util.JWTUtil;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private CustomUserService service;

    public JWTFilter(JWTUtil jwtUtil, CustomUserService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            doFilter(request, response, filterChain);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.verify(token);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            doFilter(request, response, filterChain);
            return;
        }

        UserDetails user = service.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        doFilter(request, response, filterChain);
    }
}

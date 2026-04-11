package rs.raf.userservice.util;

import java.security.KeyPair;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
    private final KeyPair keys = Jwts.SIG.EdDSA.keyPair().build();

    public String issue(String username) {
        return Jwts.builder()
            .issuer("RAF")
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .signWith(keys.getPrivate())
            .compact();
    }

    public String verify(String jwt) {
        try {
            return Jwts.parser()
                .requireIssuer("RAF")
                .verifyWith(keys.getPublic())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}

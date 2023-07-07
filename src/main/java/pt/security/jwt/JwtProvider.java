package pt.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pt.security.services.PhatTuDetails;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    @Value("${pt.jwt.secret}")
    private String JWT_SECRET;
    @Value("${pt.jwt.expiration}")
    private int EXPIRATION;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    public String genarateToken(Authentication authentication) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + EXPIRATION);
        PhatTuDetails phatTuDetails = (PhatTuDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((phatTuDetails.getUsername()))
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(key(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserEmailFromJwtToken(String jwt) {
        //        System.out.println(email);
        return Jwts.parserBuilder().setSigningKey(key())
                .build().parseClaimsJws(jwt)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}

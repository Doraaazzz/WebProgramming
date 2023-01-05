package com.dora.weblab4.service;

import com.dora.weblab4.model.WebUser;
import com.dora.weblab4.model.WebUserName;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    private SecretKey secret;

    public JwtService(
            @Value("${com.dora.jwt.secret}")
            String secret
    ) {

        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private void tryValidateToken(String token) throws Exception {
        Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);
    }

    public String generateToken(WebUser user) {
        LocalDateTime now = LocalDateTime.now();
        Instant expirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date expiration = Date.from(expirationInstant);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .signWith(secret)
                .compact();
    }

    public JwtValidationStatus validateToken(String token) {
        try {
            tryValidateToken(token);

            return JwtValidationStatus.SUCCESSFUL;
        } catch (ExpiredJwtException e) {
            return JwtValidationStatus.TOKEN_EXPIRED;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            return JwtValidationStatus.TOKEN_INVALID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JwtValidationStatus.UNKNOWN_ERROR;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public WebUser getUser(String token) {
        Claims claims = getClaims(token);

        return new WebUserName(claims.getSubject());
    }
}

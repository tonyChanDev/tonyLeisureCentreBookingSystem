package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        final Claims claims = extractAllClaims(token);
        return String.valueOf(claims.get("role"));
    }

    public <T> T extractClaim(String token, Function<Claims, T> clamsResolver) {
        final Claims claims = extractAllClaims(token);
        return clamsResolver.apply(claims);
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return generateToken(claims, user);
    }

    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignInKeyt(), Jwts.SIG.HS512)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKeyt())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKeyt() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

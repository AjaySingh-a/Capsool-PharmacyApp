package com.capsool.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${capsool.jwt.secret}")  // ✅ Fetching Secret Key from properties
    private String secretKey;

    @Value("${capsool.jwt.expiration}") // ✅ Fetching token expiration time
    private long expirationTime;

    // ✅ Generating JWT Token with Role information (Automatically Add ROLE_ prefix)
    public String generateToken(String username, String role) {
        String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return Jwts.builder()
                .setSubject(username)  // ✅ Adding Username
                .claim("role", prefixedRole)   // 🔥 Adding Role (Automatically prefixed with ROLE_)
                .setIssuedAt(new Date())  // ✅ Token issued time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // ✅ Token expiry time
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // ✅ Signing the token
                .compact();
    }

    // ✅ Validating JWT Token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ✅ Extracting Username from JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extracting Role from JWT (Always Returns ROLE_PHARMACY_OWNER)
    public String extractRole(String token) {
        String role = extractClaim(token, claims -> claims.get("role", String.class));
        return role.startsWith("ROLE_") ? role : "ROLE_" + role;
    }

    // ✅ Checking if JWT is expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // ✅ Generic method to extract any claim from JWT
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // ✅ Private method to get Signing Key
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

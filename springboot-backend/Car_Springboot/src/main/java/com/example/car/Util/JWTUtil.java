package com.example.car.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;  
import java.security.Key;  
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JWTUtil {
	
	
	
	// Your base64 encoded secret key  
    private static final String SECRET_KEY_BASE64 = "413F44284728486250655368566D5970337336763979244226452948404D6351";  
    
    // Method to extract username from the token  
    public String extractUserName(String token) {  
        return extractClaim(token, Claims::getSubject);  
    }  

    // Method to generate a token  
    public String generateToken(UserDetails userDetails) {  
        return generateToken(new HashMap<>(), userDetails);  
    }  

    // Method to validate the token  
    public boolean isTokenValid(String token, UserDetails userDetails) {  
        final String username = extractUserName(token);  
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);  
    }  

    // Method to extract claims  
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {  
        final Claims claims = extractAllClaims(token);  
        return claimsResolvers.apply(claims);  
    }  

    // Method to generate a token with extra claims  
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {  
        return Jwts.builder()  
                .setClaims(extraClaims)  
                .setSubject(userDetails.getUsername())  
                .setIssuedAt(new Date(System.currentTimeMillis()))  
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 minutes  
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  
                .compact();  
    }  

    // Method to generate a refresh token  
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {  
        return Jwts.builder()  
                .setClaims(extraClaims)  
                .setSubject(userDetails.getUsername())  
                .setIssuedAt(new Date(System.currentTimeMillis()))  
                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // 7 days  
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  
                .compact();  
    }  

    // Method to check if the token is expired  
    private boolean isTokenExpired(String token) {  
        return extractExpiration(token).before(new Date());  
    }  

    // Method to extract all claims from the token  
    private Claims extractAllClaims(String token) {  
        return Jwts.parserBuilder()  
                .setSigningKey(getSigningKey())  
                .build()  
                .parseClaimsJws(token)  
                .getBody();  
    }  

    // Method to extract expiration date from the token  
    private Date extractExpiration(String token) {  
        return extractClaim(token, Claims::getExpiration);  
    }  

    // Method to get the signing key  
    private Key getSigningKey() {  
        // Decode the base64 encoded secret key  
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY_BASE64);  
        // Create a signing key from the byte array  
        return Keys.hmacShaKeyFor(keyBytes);  
    }  

}

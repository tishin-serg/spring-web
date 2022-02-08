package ru.tishin.springweb.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    // ключ для генерации токена
    @Value("${jwt.secret}")
    private String secret;

    // время жизни токена
    @Value("${jwt.lifetime}")
    private Integer jwtLifeTime;

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        List<String> rolesList =
//                userDetails.getAuthorities().stream()
//                        .map(grantedAuthority -> grantedAuthority.getAuthority())
//                        .collect(Collectors.toList());
//        claims.put("roles", rolesList);
//
//        Date issueDate = new Date();
//        Date expiredDate = new Date(issueDate.getTime() + jwtLifeTime);
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(issueDate)
//                .setExpiration(expiredDate)
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }


    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public List<String> getRoles(String token) {
        return getClaimsFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}


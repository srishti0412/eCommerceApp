package com.ecommerce.magnik.security.jwt;

import com.ecommerce.magnik.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private UserDetailsImpl userDetails;

    @Value("${app.jwtCookieName}")
    private String jwtCookie;


    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix
        }
        return null;
    }
//
//
    public String generateJwtToken(UserDetailsImpl userDetails) {

        String username = userDetails.getUsername();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();


    }

    public String getJwtFromCookies(HttpServletRequest request) {

        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies()).forEach(c ->
                    System.out.println("Cookie: " + c.getName() + " = " + c.getValue()));
        }

        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            System.out.println("Found Cookie: " + cookie.getValue());
            return cookie.getValue();
        } else {
            System.out.println("JWT Cookie not found");
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername(),userPrincipal.getAuthorities());

        System.out.println("JWT in generateJwtCookies: "+jwt);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60)
                .httpOnly(true)
                .secure(false)
                .build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie()
    {
        ResponseCookie cookie=ResponseCookie.from(jwtCookie,null).path("/api").maxAge(0).build();
        return cookie;
    }

    private String generateTokenFromUsername(String username, Collection<? extends GrantedAuthority> authorities) {

        List<String>roles=authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build().parseClaimsJws(token)
                .getBody().getSubject();
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
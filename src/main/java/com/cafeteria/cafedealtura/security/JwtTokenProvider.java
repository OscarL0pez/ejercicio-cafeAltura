package com.cafeteria.cafedealtura.security;

import com.cafeteria.cafedealtura.common.constants.ApiConstants;
import com.cafeteria.cafedealtura.domain.user.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Proveedor de tokens JWT para la autenticación.
 * Maneja la generación y validación de tokens JWT.
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Genera un token JWT para un usuario.
     * 
     * @param user Usuario para el que se genera el token
     * @return Token JWT
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ApiConstants.TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("roles", user.getRoles().stream()
                        .map(role -> role.getName())
                        .toList())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Obtiene el email del usuario desde un token JWT.
     * 
     * @param token Token JWT
     * @return Email del usuario
     */
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Valida un token JWT.
     * 
     * @param authToken Token JWT a validar
     * @return true si el token es válido
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Firma JWT inválida");
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT malformado");
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT no soportado");
        } catch (IllegalArgumentException ex) {
            logger.error("Claims JWT vacíos");
        }
        return false;
    }
}
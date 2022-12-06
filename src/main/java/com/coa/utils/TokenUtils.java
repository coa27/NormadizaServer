package com.coa.utils;

import com.coa.exception.JWTError;
import com.coa.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class TokenUtils {

    @Value("${jwt.access_token_secret}")
    private String TOKEN_SECRET;

    @Value("${jwt.duration_valid_token_min}")
    private int TOKEN_DURATION;

    public Boolean validarToken(String token){
        try {

            Jwts.parserBuilder().setSigningKey(key())
                    .build()
                    .parse(token);
            return true;

        }catch (SignatureException ex) {
            throw new JWTError("Invalid Jwt signature");
        } catch (MalformedJwtException ex) {
            throw new JWTError("Invalid Jwt token");
        } catch (ExpiredJwtException ex) {
            throw new JWTError("Expired Jwt token");
        } catch (UnsupportedJwtException ex) {
            throw new JWTError("Unsupported Jwt token");
        } catch (IllegalArgumentException ex) {
            throw new JWTError("Jwt claims string is empty");
        }
    }

    public String getUsuarioOrEmailDelToken(String token){
        return Jwts.parserBuilder().setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().get("usuario").toString();
    }

    public String generarToken(Authentication authentication){
        int tiempoExpiracion = TOKEN_DURATION * 1000;
        Date expiracionFecha = new Date(System.currentTimeMillis() + tiempoExpiracion);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .claim("usuario", userDetails.getUsername())
                .setIssuer("cristian_jwt_api")
                .setExpiration(expiracionFecha)
                .signWith(key())
                .compact();
    }

    public String generarToken(String usuario){
        int tiempoExpiracion = TOKEN_DURATION * 1000;
        Date expiracionFecha = new Date(System.currentTimeMillis() + tiempoExpiracion);

        return Jwts.builder()
                .claim("usuario", usuario)
                .setIssuer("cristian_jwt_api")
                .setExpiration(expiracionFecha)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(TOKEN_SECRET));
    }

}

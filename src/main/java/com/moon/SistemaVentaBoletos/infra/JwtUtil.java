package com.moon.SistemaVentaBoletos.infra;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Key SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256); //almacenar en una variable de entorno
    public String generarToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)) //Duracion10H
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public String extraerEmail(String token){
        return obtenerClaims(token).getSubject();
    }
    public boolean esTokenValido(String token,String email){
        String emailExtraido = extraerEmail(token);
        return (emailExtraido.equals(email) && !esTokenExpirado(token));
    }
    private Claims obtenerClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseSignedClaims(token).getBody();
    }
    private boolean esTokenExpirado(String token){
        return obtenerClaims(token).getExpiration().before(new Date());
    }
}

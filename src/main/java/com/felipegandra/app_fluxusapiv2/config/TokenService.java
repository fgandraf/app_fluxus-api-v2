package com.felipegandra.app_fluxusapiv2.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.felipegandra.app_fluxusapiv2.modules.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${private.key}")
    private String privateKey;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            return JWT
                    .create()
                    .withIssuer("fluxus")
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
        } catch (JWTCreationException error){
            throw new RuntimeException("Error generating token", error);
        }
    }


    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            return JWT.require(algorithm)
                    .withIssuer("fluxus")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException error){
            return "";
        }
    }

}
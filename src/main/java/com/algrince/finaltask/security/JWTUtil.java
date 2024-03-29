package com.algrince.finaltask.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Generates JWT-token and performs its validation
 */

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    @Value("${issuer}")
    private String issuer;

    @Value("${subject}")
    private String subject;

    @Value("${claim}")
    private String claim;

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        Date generationDate = new Date();

        return JWT.create()
                .withSubject(subject)
                .withClaim(claim, username)
                .withIssuedAt(generationDate)
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(claim).asString();
    }
}

package com.adp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JWTHelper {

  public static String createToken(String name, long userId, String role,  RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
    
    try {
      Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
      Instant now = Instant.now();

      String token = JWT.create()
          .withSubject(name)
          .withClaim("userId", userId)
          .withClaim("role", role)
          .withIssuer("issuer")
          .withIssuedAt(now)
          .withExpiresAt(now.plus(1, ChronoUnit.HOURS)) // 1 hour expiration
          .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      return null;
    }
  }

}

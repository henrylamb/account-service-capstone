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

  public static String createToken(String name, RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
    
    try {
      Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
      Instant now = Instant.now();

      String token = JWT.create()
          .withSubject(name)
          .withIssuer("issuer")
          .withIssuedAt(now)
          .withExpiresAt(now.plus(1, ChronoUnit.HOURS)) // 1 hour expiration
          .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      return null;
    }
  }

  // public static boolean verifyToken(String token, RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {

  //   try {
  //     Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
  //     JWTVerifier verifier = JWT.require(algorithm)
  //         // specify any specific claim validations
  //         .withIssuer("issuer")
  //         // reusable verifier instance
  //         .build();

  //       DecodedJWT decodedJWT = verifier.verify(token);
  //       //check decodedJWT

  //     return true;
  //   } catch (JWTVerificationException exception) {
  //     // Invalid signature/claims
  //     return false;
  //   }
  // }
}

package com.adp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
//BE CAREFUL WHICH JWT IMPORT YOU USE - THERE ARE 2 DIFFERENT ONES
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

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

    // This will check that the JWT was signed by the private key (using the public key)
  public static JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

   // We extract the claims from the header
  public static Map<String, Object> extractClaims(String token, JwtDecoder jwtDecoder) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getClaims();
  }

  public static JwtAuthenticationConverter jwtAuthenticationConverter() {
      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
      converter.setJwtGrantedAuthoritiesConverter((jwt) -> {
          // Extract role claim from JWT and map to Spring Security authority
          String role = jwt.getClaimAsString("role");
          return Collections.singletonList(new SimpleGrantedAuthority(role.toUpperCase()));
      });
      converter.setPrincipalClaimName("userId");
      return converter;
  }

  public static Long getUserIdFromAuthContext(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //Dunno why but the principal is getting set as a jwt.
    Jwt jwt = (Jwt) authentication.getPrincipal();
    return (Long)jwt.getClaims().get("userId");
  }

}

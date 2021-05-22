package com.curesio.ehealth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.curesio.ehealth.constants.SecurityConstants;
import com.curesio.ehealth.models.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.curesio.ehealth.constants.SecurityConstants.*;

@Service
public class JwtTokenProvider {

    @Value("${jwt.token-secret}")
    private String secret;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public String generateJwtToken(UserPrincipal userPrincipal) {
        List<String> claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withClaim(AUTHORITIES, claims)
                .withSubject(userPrincipal.getUsername())
                .withIssuer(ISSUER)
                .withAudience(AUDIENCE)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLISECONDS))
                .sign(Algorithm.HMAC512(secret));
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) throws JWTVerificationException {
        return getClaimsFromToken(token)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getSubjectFromToken(String token) throws JWTVerificationException {
        return getJwtVerifier()
                .verify(token)
                .getSubject();
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                null,    // not required because we have already verified the token
                authorities
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public boolean isTokenValid(String username, String token) throws JWTVerificationException {
        return StringUtils.isNotEmpty(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws JWTVerificationException {
        Date expiryDate = getJwtVerifier()
                .verify(token)
                .getExpiresAt();
        return expiryDate.before(new Date());
    }

    private List<String> getClaimsFromToken(String token) {
       try {
           return getJwtVerifier()
                   .verify(token)
                   .getClaim(AUTHORITIES)
                   .asList(String.class);
       } catch (JWTVerificationException e) {
           LOGGER.error(Arrays.toString(e.getStackTrace()));
           throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED_MSG);
       }
    }

    private JWTVerifier getJwtVerifier() {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
    }

    private List<String> getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal
                .getAuthorities()
                .stream()
                .map(g -> String.valueOf(g.getAuthority()))
                .collect(Collectors.toList());
    }
}

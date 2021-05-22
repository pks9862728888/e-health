package com.curesio.ehealth.filters;

import com.curesio.ehealth.utils.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.curesio.ehealth.constants.SecurityConstants.OPTIONS_HTTP_METHOD;
import static com.curesio.ehealth.constants.SecurityConstants.TOKEN_PREFIX;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // If the authorization token is bearer token then process token else throw error
            if (StringUtils.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith(TOKEN_PREFIX)) {
                String token = StringUtils.substring(authorizationHeader, TOKEN_PREFIX.length());
                String username = jwtTokenProvider.getSubjectFromToken(token);

                if (jwtTokenProvider.isTokenValid(username, token) &&
                        SecurityContextHolder.getContext().getAuthentication() != null) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(
                            username,
                            jwtTokenProvider.getAuthoritiesFromToken(token),
                            request
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}

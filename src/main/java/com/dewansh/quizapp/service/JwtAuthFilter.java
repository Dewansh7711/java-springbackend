package com.dewansh.quizapp.service;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
        // private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

        @Autowired
        private JwtUtil jwtUtil;

        @Override
        protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer")){
                log.debug(authHeader);

                String token = authHeader.substring(7); // Get the token part

                if (jwtUtil.validateToken(token)){
                    log.info("JWT token is valid");
                    String username = jwtUtil.extractUsername(token);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    log.warn("Invalid JWT token");
                }
            }

            filterChain.doFilter(request, response);
        }



}

package com.pragma.carrito.adapters.securityconfig.jwtconfiguration;


;
import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationResponse;
import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.spi.UserFeignClientPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;


    @Component
    @AllArgsConstructor
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final UserFeignClientPort userFeignClientPort;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            final String token;


            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            token = authHeader.substring(7);


            AuthorizationResponse authorizationResponse = userFeignClientPort.validateToken(token);
            if(!authorizationResponse.isPresent()) {
                throw new ElementNotFoundException(Constants.EMAIL);
            }else{
                Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(authorizationResponse.getRole()));


                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        authorizationResponse,
                        null,
                        authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);




                filterChain.doFilter(request, response);
            }}}
package com.tonyleisurecentre.bookingsystem.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonyleisurecentre.bookingsystem.enums.Role;
import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter::doFilterInternal: " + request.getRequestURI());
        if (request.getRequestURI().startsWith("/public")) {
            System.out.println("JwtAuthenticationFilter::doFilterInternal: no need authentication");
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        System.out.println("doFilterInternal::authHeader: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            GenericResponse<Object> customResponse = new GenericResponse<>();
            customResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            var errMsg = new ErrMsg();
            errMsg.setDescription("Without token");
            customResponse.setErrorMessages(List.of(errMsg));

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(convertObjectToJson(customResponse));
            //filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

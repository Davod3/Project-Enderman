package com.project.enderman.security;

import com.project.enderman.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter extends OncePerRequestFilter {

    private UserService userService;

    private static final String ALLOW_ORIGIN = "http://localhost:3000";
    private static final String ALLOW_METHODS = "GET, POST, PUT, DELETE, OPTIONS, PATCH";
    private static final String ALLOW_HEADERS = "X-API-USER, X-API-KEY, Content-Type";
    private static final String MAX_AGE = "3600";

    public AuthenticationFilter(UserService userService) {

        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Setup cors headers
        response.setHeader("Access-Control-Allow-Origin", ALLOW_ORIGIN);
        response.setHeader("Access-Control-Allow-Methods", ALLOW_METHODS);
        response.setHeader("Access-Control-Allow-Headers", ALLOW_HEADERS);
        response.setHeader("Access-Control-Max-Age", MAX_AGE);

        // Handle CORS headers for preflight requests
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK); // Set HTTP 200 OK status for preflight
            return;
        }

        try {

            Authentication authentication = AuthenticationService.getAuthentication(request, userService);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = response.getWriter();
            writer.print(e.getMessage());
            writer.flush();
            writer.close();

        }

        filterChain.doFilter(request,response);

    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> preAuthTenantContextInitializerFilterRegistration(AuthenticationFilter filter) {

        System.out.println("FilterRegister called called");

        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

}

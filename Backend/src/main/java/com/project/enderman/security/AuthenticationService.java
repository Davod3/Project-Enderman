package com.project.enderman.security;

import com.project.enderman.entities.User;
import com.project.enderman.repositories.UserRepository;
import com.project.enderman.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    private static final String AUTH_USER_HEADER_NAME = "X-API-USER";

    private static final String AUTH_TOKEN = "yeet"; // Just for testing purposes

    public static Authentication getAuthentication(HttpServletRequest request, UserService userService) {

        String apiUser = request.getHeader(AUTH_USER_HEADER_NAME);
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if(apiUser == null || apiKey == null) {
            throw new BadCredentialsException("Invalid authentication parameters");
        }

        User user = userService.getUser(apiUser);

        //user.getToken()
        if(user == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);

    }
}

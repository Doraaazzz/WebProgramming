package com.dora.weblab4.controller.auth;

import com.dora.weblab4.model.WebUser;
import com.dora.weblab4.service.JwtService;
import com.dora.weblab4.service.JwtValidationStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private JwtService jwtService;

    public AuthHandlerInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }

        return null;
    }

    private void authorizationRequired(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void forbidden(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private void unknownError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getToken(request);

        if (token != null) {
            JwtValidationStatus status = jwtService.validateToken(token);

            switch (status) {
                case SUCCESSFUL: {
                    SecurityContextHolder.clearContext();
                    SecurityContext context = SecurityContextHolder.getContext();
                    WebUser user = jwtService.getUser(token);

                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(user, null);

                    context.setAuthentication(authentication);
                    return true;
                }
                case TOKEN_EXPIRED:
                    authorizationRequired(response);
                    break;
                case TOKEN_INVALID:
                    forbidden(response);
                    break;
                default:
                    unknownError(response);
            }
        } else {
            authorizationRequired(response);
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            if (method.hasMethodAnnotation(RequiresAuth.class)) {
                return filter(request, response);
            }
        }

        return true;
    }

}

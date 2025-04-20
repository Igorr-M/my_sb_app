package com.example.my_sb_app.service;

import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private UserRepository userRepository;
 
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid username or password.";
        String username = request.getParameter("username");
     
        if (exception instanceof org.springframework.security.authentication.BadCredentialsException) {
         
            User user = this.userRepository.findByUsername(username);
            if (user == null) {
                errorMessage = "Invalid username.";
            } else {
                errorMessage = "Invalid password.";
            }
        }
     
        response.sendRedirect("/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8) + "&username=" +
                URLEncoder.encode(username, StandardCharsets.UTF_8));
    }
    
}

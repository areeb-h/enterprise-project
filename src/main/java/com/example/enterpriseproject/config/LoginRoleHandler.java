package com.example.enterpriseproject.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class LoginRoleHandler extends SimpleUrlAuthenticationSuccessHandler {

    //method to handle login
    @Override
    protected void handle (HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) return;
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    //method which handles the logic for where to go after a user logs in depending on their roles
    protected String determineTargetUrl(Authentication authentication){
        String url = "/login?error=true";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities){
            roles.add(a.getAuthority());
        }
        if(roles.contains("ADMIN")){
            url = "/admin/dashboard";
        }else if(roles.contains("CUSTOMER")) {
            url = "/customer/dashboard";
        } else if (roles.contains("DRIVER")) {
            url = "/driver/dashboard";
        }
        return url;
    }
}

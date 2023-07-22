package com.example.enterpriseproject.config;

import com.example.enterpriseproject.service.UserServiceImplementation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private LoginRoleHandler loginRoleHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Component
    public static class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            setDefaultFailureUrl("/login?error=" + exception.getLocalizedMessage());
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    // create UserdetailService bean
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImplementation();
    }

    // create BcryptPassword encorder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // create authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // bean to handle requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // every user will be able to access these urls
                        .requestMatchers(request -> request.getServletPath().startsWith("/images/")).permitAll() // in resources/static/
                        .requestMatchers(request -> request.getServletPath().startsWith("/css/")).permitAll() // in resources/static/
                        .requestMatchers("/", "/home", "/register", "/login").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER")
                        .requestMatchers("/driver/**").hasAnyAuthority("DRIVER")
                        .anyRequest().authenticated())
                .csrf().disable().formLogin((form) -> form
                        // all the users will be able to access login form
                        .loginPage("/login")
                        .permitAll()
                        // if login failed this loginFailureHandler class will decide what happens
                        .failureHandler(loginFailureHandler)
                        // if login is successfull this loginRoleHandler class will decide what happens
                        // next
                        .successHandler(loginRoleHandler)
                        .usernameParameter("email")
                        .passwordParameter("password"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/"))
                .sessionManagement((sessionManagement) -> sessionManagement // checks if there is a session and redirects to /login with a parameter of session=0
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?session=0"))
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
        http.authenticationProvider(authenticationProvider());
        http.headers().frameOptions().sameOrigin();

        return http.build();

    }
}
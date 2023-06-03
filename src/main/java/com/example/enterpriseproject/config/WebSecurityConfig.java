package com.example.enterpriseproject.config;

import com.example.enterpriseproject.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private LoginRoleHandler loginRoleHandler;

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
                        .requestMatchers("/", "/home", "/register").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER")
                        .requestMatchers("/driver/**").hasAnyAuthority("DRIVER")
                        .anyRequest().authenticated())
                /*
                 * .csrf().disable().formLogin()
                 * //all the users will be able to access login form
                 * .loginPage("/login")
                 * .permitAll()
                 * .failureUrl("/login?error=true")
                 * //if login is successfull this loginRoleHandler class will decide what
                 * happens next
                 * .successHandler(loginRoleHandler)
                 * .usernameParameter("username")
                 * .passwordParameter("password").and()
                 */
                .csrf().disable().formLogin((form) -> form
                        // all the users will be able to access login form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        // if login is successfull this loginRoleHandler class will decide what happens
                        // next
                        .successHandler(loginRoleHandler)
                        .usernameParameter("email")
                        .passwordParameter("password"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/"))
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
        http.authenticationProvider(authenticationProvider());
        http.headers().frameOptions().sameOrigin();

        return http.build();

    }

}
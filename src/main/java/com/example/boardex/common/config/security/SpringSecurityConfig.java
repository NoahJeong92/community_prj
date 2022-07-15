package com.example.boardex.common.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.formLogin()
                .loginPage("/auth/signIn")
                .failureUrl("/auth/signInError")
                .loginProcessingUrl("/auth/signIn")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(new SignInSuccessHandler())
            .and()
                .csrf()
                .disable()
            .logout()
                .logoutSuccessUrl("/auth/signIn");
    }

}

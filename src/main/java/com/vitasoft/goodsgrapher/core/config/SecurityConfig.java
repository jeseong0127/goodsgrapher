package com.vitasoft.goodsgrapher.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().and()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .antMatchers("/api/auth/oauth", "/api/auth/app-version").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().hasRole("USER");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v3/api-docs", "/swagger-resources/**",
                "/swagger-ui/index.html", "/webjars/**", "/swagger/**"
        );
    }
}

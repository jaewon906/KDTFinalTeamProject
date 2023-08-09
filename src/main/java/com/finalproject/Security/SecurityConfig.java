package com.finalproject.Security;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenConfig tokenConfig;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors->cors.disable())
                .csrf(csrf->csrf.disable())
                .formLogin(form -> form.disable())
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers("/api/user/logOut").hasRole("USER")
                                 .requestMatchers("/api/user/signUp/**").permitAll()
                                .requestMatchers("/api/user/findMyInfo/**").permitAll()
                                .requestMatchers("/api/user/logIn").permitAll())
                .addFilterBefore(new JwtFilter(tokenConfig, new ModelMapper()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

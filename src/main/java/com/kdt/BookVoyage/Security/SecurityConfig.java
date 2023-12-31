package com.kdt.BookVoyage.Security;

import com.kdt.BookVoyage.Common.CookieConfig;
import com.kdt.BookVoyage.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenConfig tokenConfig;
    private final TokenDecoder tokenDecoder;
    private final CookieConfig cookieConfig;
    private final MemberRepository memberRepository;
    private final CustomCSRFRequestHandler customCSRFRequestHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/user/logIn").permitAll() //로그인
                                .requestMatchers("/api/admin/login").permitAll() // 관리자 로그인
                                .requestMatchers("/api/user/signUp/**").permitAll() //회원 가입
                                .requestMatchers("/api/user/logOut").permitAll() //로그아웃
                                .requestMatchers("/api/user/findMyInfo/**").permitAll() //내정보 찾기
                                .requestMatchers("/api/user/dormantAccount").permitAll() //휴면계정
                                .requestMatchers("/api/board/**").hasRole("USER")
                                .requestMatchers("/api/book/**").hasRole("USER")
                                .requestMatchers("/api/user/board/**").hasRole("USER") //게시글 관련
                                .requestMatchers("/api/user/myPage/**").hasRole("USER") //내 페이지 관련
                                .requestMatchers("/api/user/withdrawal").hasRole("USER") //회원탈퇴
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/admin/autoLogin").hasRole("ADMIN") //로그인
                )

                .addFilterBefore(new JwtFilter(tokenConfig,tokenDecoder, cookieConfig, new ModelMapper(), memberRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}

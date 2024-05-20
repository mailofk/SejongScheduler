package com.sejong.sejongHelp.config;

import com.sejong.sejongHelp.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {


//    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/api/join").permitAll()
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
                );

        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("studentId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/api/login").failureHandler(loginFailHandler())
                        .permitAll()
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        })
                        .deleteCookies("JSESSIONID", "remember-me")
                );


        http
                .csrf((auth) -> auth.disable());
        //protection을 통해 GET요청을 제외한 상태를 변화시킬 수 있는 POST, PUT, DELETE 요청으로부터 보호한다.



//        http
//                .sessionManagement((auth) -> auth
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true));
//        // 설정된 최대 세션 수를 넘어서게 되면, 현재 사용자 인증을 실패 처리함
//        // 만약 false로 할 시, 이전 사용자의 세션이 만료됨

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        //사용자 인증 성공 시, 세션 자체는 그대로 두고 세션 아이디만 변경



        return http.build();
    }

//    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }

//    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.sejong.sejongHelp.jwt;

import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        //헤더가 존재하지 않거나, 잘못된 형식으로 되어있을 시 진입
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1]; //Bearer로 시작하는 부분 제외한 부분을 token으로 가져오는 과정

        //토큰 만료되었을 경우 진입
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);

            return;
        }

        String studentId = jwtUtil.getUserName(token);
        Member member = new Member(studentId, "temp", "temp", "temp");

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

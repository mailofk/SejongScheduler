package com.sejong.sejongHelp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e)
            throws IOException, ServletException {

        String errorMessage;
        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            log.info(String.valueOf(e)); //로그로 에러 내역 출력
            errorMessage="아이디 또는 비밀번호가 맞지 않습니다.";
        }else if (e instanceof UsernameNotFoundException){
            errorMessage="존재하지 않는 아이디 입니다.";
        }
        else{
            errorMessage="시스템 오류로 로그인이 실패하였습니다.";
        }

        //한글 인코딩 작업
        errorMessage= URLEncoder.encode(errorMessage,"UTF-8");
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
    }
}

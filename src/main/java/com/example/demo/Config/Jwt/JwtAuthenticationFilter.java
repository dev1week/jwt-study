package com.example.demo.Config.Jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//스프링 시큐리티에서 해당 필터가 있음
//로그인 요청해서 username, password 전송하면 해당 필터가 동작함


//원래는 SecurityConfig에서
//    .formLogin()
//            .loginPage("/loginForm")
//            .loginProcessingUrl("/login") // 로그인 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행합니다.
//            .defaultSuccessUrl("/");
//로 설정하지만 jwt에서는 .formLogin().disable() 로 해당 기능을 꺼놓기 때문에 따로 구현해줘야한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //로그인요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        System.out.println("로그인 시도중");

        //username password를 받아서

        //정상인지 로그인 시도
            //principaldetailsService 호출 => loadUserByName()실행

        //principal details를 세션에 담음 (권한 관리를 위함)

        //jwt 토큰을 만들어서 반환한다.

        return super.attemptAuthentication(request, response);
    }
}

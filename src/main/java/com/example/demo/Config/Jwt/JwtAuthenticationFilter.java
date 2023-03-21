package com.example.demo.Config.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Config.Auth.PrincipalDetails;
import com.example.demo.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


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
        //json 파싱하기
        ObjectMapper om = new ObjectMapper();
        try {
            User user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());


            //PrincipalDetailsService의 loadUserByUsername()함수가 실행됨
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);


            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            //반환시 auth 객체가 session에 저장됨 => 로그인이 되었다는 뜻
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //정상인지 로그인 시도
            //principaldetailsService 호출 => loadUserByName()실행

        //principal details를 세션에 담음 (권한 관리를 위함)

        //jwt 토큰을 만들어서 반환한다.

    }
    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 sucessfulAuthentication 함수가 실행됨
    //jwt 토큰을 만들어서 request 요청한 사용자에게 jwt 토큰을 response 해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        //로그인이 되어있다는 토큰을 만들어보자
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("1"));

        response.addHeader("Authorization", "Bearer "+jwtToken);
    }
}

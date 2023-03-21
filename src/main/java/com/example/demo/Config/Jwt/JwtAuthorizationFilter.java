package com.example.demo.Config.Jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Config.Auth.PrincipalDetails;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    private UserRepository userRepository;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

    }
    //인증이나 권한이 필요한 요청이 있을 때 해당 필터를 타게됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 요청입니다.");
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwt header : "+jwtHeader);


        //jwt 토큰 검증을 하여 정상적인 사용자인지 확인한다.
        //헤더가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        //서명 확인
        String username = JWT.require(Algorithm.HMAC512("1")).build().verify(jwtToken)
                .getClaim("username").asString();
        //db에서 확인
        if(username!=null){
            User userEntity = userRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            //인증이 정상적으로 되었기 때문에 강제로 객체를 만들어도 된다. 때문에 비밀번호는 null로 넣는다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            //강제로 시큐리티 세션에 접근하여 Auth 객체를 저장함
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        chain.doFilter(request, response);
    }
}

package com.example.demo.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter1 implements Filter{

    //cos 토큰을 만들려면
        //id pw가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 응답을 해준다.
        //요청할 때마다 header에 authrization에 value 토큰을 가지고 온다.
        //이 토큰이 내가 만든 토큰이 맞는지만 검증하면된다 rsa, sh256
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String headerAuth = req.getHeader("Authorization");
        if(headerAuth.equals("COS")){
            chain.doFilter(request, response);
            System.out.println(headerAuth);
        }else{
            System.out.println("인증안됨");
        }



    }
}

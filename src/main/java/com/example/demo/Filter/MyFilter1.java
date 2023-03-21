package com.example.demo.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter1 implements Filter{
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

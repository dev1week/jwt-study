package com.example.demo.Config;

import com.example.demo.Filter.MyFilter1;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    //주의 : 해당방식은 시큐리티 필터 이후에 실행됨
    @Bean
    public FilterRegistrationBean<MyFilter1>filter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); //낮은 번호순으로 실행된다.
        return bean;
    }
    @Bean
    public FilterRegistrationBean<MyFilter1>filter2(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(1); //낮은 번호순으로 실행된다.
        return bean;
    }
}

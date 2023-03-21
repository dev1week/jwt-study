package com.example.demo.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //config 설정
        CorsConfiguration config = new CorsConfiguration();


        config.setAllowCredentials(true);// 내 서버가 응답을 할 때 json을 자바 스크립트에서 처리할 수 있게끔
        config.addAllowedOrigin("*");     //모든 ip에 응답을 허용하겠다.
        config.addAllowedHeader("*");     //모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*");     //모든 http메서드 요청을 허용하겠다.
        //소스에 설장한 config를 등록, /api/**로 요청이 들어올 경우 해당 설정(config) 적용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}

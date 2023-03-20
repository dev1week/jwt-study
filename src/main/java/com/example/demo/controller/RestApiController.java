package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {
    @GetMapping("home")
    public String home(){
        return "<h1>home</h1>";
    }
}

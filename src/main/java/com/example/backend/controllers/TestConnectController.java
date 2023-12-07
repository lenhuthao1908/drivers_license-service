package com.example.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev")
public class TestConnectController {
    @GetMapping("/test")
    public String test(){
        return "Application connect database!!!";
    }


}

package com.project.enderman.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api")
public class RestApiController {

    @GetMapping("/servers")
    public String getServerList() {
        return "Teste";
    }

    @GetMapping("/login")
    public String login() {
        return "Loggin in...";
    }
}

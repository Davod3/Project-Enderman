package com.project.enderman.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api")
public class LoginController {

    @PostMapping("/test_token")
    public void testToken(){
        /*
            Empty on purpose. This endpoint only exists so that clients can check
            if the provided api token works
         */
    }
}

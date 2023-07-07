package pt.model.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {
    @GetMapping("test")
    public String testMethod(){
        return "Public content";
    }
}

package com.project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/details")
    public ResponseEntity<String> showDetails(){
        return new ResponseEntity<String>("MASUK ADMIN",HttpStatus.OK);
    }
}

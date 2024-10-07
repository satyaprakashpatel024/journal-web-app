package com.edigest.journalwebapp.controller;

import com.edigest.journalwebapp.entity.Users;
import com.edigest.journalwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUserEntry(@RequestBody Users userEntry){
        try{
            this.userService.saveNewUser(userEntry);
            return new ResponseEntity<Users>(userEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Duplicate user Name : ",HttpStatus.BAD_REQUEST);
        }
    }
}

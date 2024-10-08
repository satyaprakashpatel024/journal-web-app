package com.edigest.journalwebapp.controller;

import com.edigest.journalwebapp.cache.AppCache;
import com.edigest.journalwebapp.entities.Users;
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

    @Autowired
    private AppCache appCache;

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUserEntry(@RequestBody Users userEntry){
        try{
            this.userService.saveAdmin(userEntry);
            return new ResponseEntity<Users>(userEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Duplicate user Name : ",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdminEntry(@RequestBody Users userEntry){
        try{
            this.userService.saveAdmin(userEntry);
            System.out.println("admin user created..");
            return new ResponseEntity<Users>(userEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Duplicate user Name : ",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/restart")
    public String restartAppCache(){
        appCache.init();
        return "Cache Cleared..";
    }
}

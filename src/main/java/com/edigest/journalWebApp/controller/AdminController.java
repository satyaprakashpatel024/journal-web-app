package com.edigest.journalWebApp.controller;

import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> all = userService.getAll();
        if(all!=null && all.size()>0){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("NO USER FOUND...", HttpStatus.NOT_FOUND);
    }
}

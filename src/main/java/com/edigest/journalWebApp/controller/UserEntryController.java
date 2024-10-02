package com.edigest.journalWebApp.controller;

import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> data = this.userEntryService.getAll();
        if(!data.isEmpty() && data != null){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found..",HttpStatus.NOT_FOUND);
    }


    @GetMapping("/user/{userName}")
    public ResponseEntity<Users> getUserByUserName(@PathVariable String userName){
        Users userEntry = userEntryService.findByUserName(userName);
        if(userEntry!=null){
            return new ResponseEntity<Users>(userEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{userName}")
    public ResponseEntity<?> deleteUserByUserName(@PathVariable String userName){
        Users user= userEntryService.deleteUserByUserName(userName);
        if(user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUserByUserName(@RequestBody Users user){
        Authentication authentivation = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentivation.getName();
        Users oldUser = userEntryService.findByUserName(userName);
        if(oldUser != null){
            oldUser.setUserName(user.getUserName());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());

            userEntryService.saveEntity(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }
}

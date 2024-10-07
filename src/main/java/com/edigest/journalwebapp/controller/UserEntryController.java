package com.edigest.journalwebapp.controller;

import com.edigest.journalwebapp.apiresponse.WeatherApiResponse;
import com.edigest.journalwebapp.entity.Users;
import com.edigest.journalwebapp.repository.UserEntryRepository;
import com.edigest.journalwebapp.services.UserService;
import com.edigest.journalwebapp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private WeatherService weatherService;

    //    get user details if user is authenticated
    @GetMapping("/get")
    public ResponseEntity<?> getUserByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users userEntry = userService.findByUserName(userName);
        if(userEntry!=null){
            return new ResponseEntity<Users>(userEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
    }

    //    update the user details after authentication
    @PutMapping("/update")
    public ResponseEntity<?> updateUserByUserName(@RequestBody Users user){
        Authentication authentivation = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentivation.getName();
        Users userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setEmail(user.getEmail());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found...",HttpStatus.NOT_FOUND);
    }

    //    delete user on the basis of user name
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user= userService.deleteUserByUserName(userName);
        if(user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/greet")
    public ResponseEntity<?> greetingMethod(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherApiResponse response = weatherService.getWeather("Mumbai");
        String messge = "";
        if(response!=null  && response.getCurrent() != null){
            int x = response.getCurrent().getFeelslike();
            messge+=", todays Weather feels like "+x+"deg Celcius";
        }
        return new ResponseEntity<>("Hello! "+userName+messge,HttpStatus.OK);
    }
}

package com.edigest.journalWebApp.controller;

import com.edigest.journalWebApp.Entity.JournalEntry;
import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.services.JournalEntryService;
import com.edigest.journalWebApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping("/register-admin")
    public ResponseEntity<?> createAdmin(@RequestBody Users admin){
        try {
            Users data = userService.saveAdmin(admin);
            return new ResponseEntity<>(data,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("ERROR WHILE REGISTERING ADMIN USER",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-journal")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<JournalEntry> data = user.getJournalEntries();
        if(data != null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found..",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-journal")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        String msg = "";
        try{
            msg += this.journalEntryService.saveEntry(journalEntry,userName);
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            collect.get(0).getId();
            Optional<JournalEntry> journalEntity = journalEntryService.getJournalById(myid);
            if(journalEntity.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntity.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteJournalById(myid,userName);
        if(removed)
        return new ResponseEntity<>("Data deleted ......",HttpStatus.ACCEPTED);
        return new ResponseEntity<>("Data not found with given id ......",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateJournalById( @PathVariable ObjectId myid,@RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            collect.get(0).getId();
            Optional<JournalEntry> journalEntity = journalEntryService.getJournalById(myid);
            if(journalEntity.isPresent()){
                JournalEntry old = journalEntity.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                String msg = journalEntryService.saveEntry(old);
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Data not found with given id...",HttpStatus.NOT_FOUND);
    }
}














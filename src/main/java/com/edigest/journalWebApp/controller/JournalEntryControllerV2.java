package com.edigest.journalWebApp.controller;

import com.edigest.journalWebApp.Entity.JournalEntry;
import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.services.JournalEntryService;
import com.edigest.journalWebApp.services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        Users user = userEntryService.findByUserName(userName);
        List<JournalEntry> data = user.getJournalEntries();
        if(data != null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found..",HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName){
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
        Optional<JournalEntry> journalEntity = journalEntryService.getJournalById(myid);
        if(journalEntity.isPresent()){
            return new ResponseEntity<JournalEntry>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myid}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName,@PathVariable ObjectId myid){
        String msg = journalEntryService.deleteJournalById(myid,userName);
        return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
    }

    @PutMapping("/id/{userName}/{myid}")
    public ResponseEntity<?> updateJournalById( @PathVariable ObjectId myid,@RequestBody JournalEntry newEntry, @PathVariable String userName){
        JournalEntry oldEntity = journalEntryService.getJournalById(myid).orElse(null);
        if(oldEntity != null){
            oldEntity.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntity.getTitle());
            oldEntity.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntity.getContent());
            String msg = journalEntryService.saveEntity(oldEntity);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}














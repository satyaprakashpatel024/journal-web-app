package com.edigest.journalWebApp.services;

import com.edigest.journalWebApp.Entity.JournalEntry;
import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            Users user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry entry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(entry);
            userService.saveUser(user);
        }
        catch (Exception e){
            throw e;
        }
    }

    public String saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
            return "Journal Entry Updated Successfully.....";
        } catch (Exception e) {
            return "Failed to Update Journal Entry.......";
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalById(ObjectId id, String userName){
        try{
            Users user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                System.out.println("Journal Entry Deleted Successfully.......");
                return true;
            }else {
                System.out.println("Journal Entry Not Found with given id .......");
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Failed to delete Journal Entry........");
            e.printStackTrace();
            throw new RuntimeException("Failed to delete Journal Entry........");
        }
    }

}

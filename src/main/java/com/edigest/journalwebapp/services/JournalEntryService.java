package com.edigest.journalwebapp.services;

import com.edigest.journalwebapp.entities.JournalEntry;
import com.edigest.journalwebapp.entities.Users;
import com.edigest.journalwebapp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
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
                log.info("Journal Entry Deleted Successfully.......");
                return true;
            }else {
                log.error("Journal Entry Not Found with given id .......");
                return false;
            }
        }
        catch (Exception e){
            log.error("Failed to delete Journal Entry........");
            e.printStackTrace();
            throw new RuntimeException("Failed to delete Journal Entry........");
        }
    }

}

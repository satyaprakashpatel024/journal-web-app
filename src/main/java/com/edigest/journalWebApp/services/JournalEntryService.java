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
    private UserEntryService userEntryService;

    @Transactional
    public String saveEntry(JournalEntry journalEntry, String userName){
        try {
            Users user = userEntryService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry entry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(entry);
            userEntryService.saveEntity(user);
            return "Data saved Successfully.....";
        }
        catch (Exception e){
            return "Data saved failed.......";
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public String deleteJournalById(ObjectId id, String userName){
        try {
            Users user = userEntryService.findByUserName(userName);
            user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            userEntryService.saveEntity(user);
            journalEntryRepository.deleteById(id);
            return "Data deleted Successfully.....";
        }
        catch (Exception e){
            return "Failed to delete Journal Entry.......";
        }
    }

    public String saveEntity(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
            return "Journal Entry Updated Successfully.....";
        }catch (Exception e){
            return "Failed to Update Journal Entry.......";
        }
    }
}

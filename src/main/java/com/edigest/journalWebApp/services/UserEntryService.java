package com.edigest.journalWebApp.services;

import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntity(Users userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userEntry);
    }

    public void saveUserEntity(Users userEntry){
        userEntryRepository.save(userEntry);
    }


    public List<Users> getAll(){
        return userEntryRepository.findAll();
    }

    public Optional<Users> getUserById(ObjectId id){
        return userEntryRepository.findById(id);
    }

    public void deleteUserById(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    public Users findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }

    public Users deleteUserByUserName(String userName) {
        return userEntryRepository.deleteByUserName(userName);
    }
}

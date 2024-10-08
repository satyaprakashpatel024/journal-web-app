package com.edigest.journalwebapp.services;

import com.edigest.journalwebapp.entity.Users;
import com.edigest.journalwebapp.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser(Users userEntry){
        try {
            userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
            userEntry.setRoles(Arrays.asList("USER"));
            userEntryRepository.save(userEntry);
            return true;
        } catch (Exception e) {
            log.warn("duplicate user entry for {}", userEntry.getUserName());
            throw e;
        }
    }

    public void saveUser(Users userEntry){
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

    public Users deleteUserByUserName(String userName) {
        return userEntryRepository.deleteByUserName(userName);
    }

    public Users findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }

    public Users saveAdmin(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        return userEntryRepository.save(user);
    }
}

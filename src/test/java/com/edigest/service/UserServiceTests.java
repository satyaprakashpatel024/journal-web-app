package com.edigest.service;

import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.JournalApplication;
import com.edigest.journalWebApp.repository.UserEntryRepository;
import com.edigest.journalWebApp.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest(classes = JournalApplication.class)
public class UserServiceTests {

    @Autowired
    private UserEntryRepository userRepository;
    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(Users user) {
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @Test
    public void findByUserName(){
        assertNotNull(userRepository.findByUserName("admin"));
    }

    @Disabled
    @Test
    public void checkMultiplication(){
        assertEquals(10,5*2);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "5,4,20",
            "4,7,28"
    })
    public void testParameterised(int a,int b,int expected){
        assertEquals(expected,a*b);
    }
}

package com.edigest.service;

import com.edigest.journalWebApp.JournalApplication;
import com.edigest.journalWebApp.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTests {

    @Test
    public void findByUserName(){
        assertEquals(4,2+2);
    }

    @Test
    public void checkMultiplication(){
        assertEquals(10,5*2);
    }
}

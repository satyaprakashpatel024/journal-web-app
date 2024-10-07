package com.edigest.service;

import com.edigest.journalWebApp.Entity.Users;
import com.edigest.journalWebApp.repository.UserEntryRepository;
import com.edigest.journalWebApp.services.UserDetailServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {
    @InjectMocks
    private UserDetailServiceImp userDetailServiceImp;

    @Mock
    private UserEntryRepository userEntryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Disabled
    @Test
    public void loadUserByUsernameTest() {
        when(userEntryRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(Users.builder().userName("dummy").email("dummy@gmail.com").password("12345678").roles(new ArrayList<>()).build());
        UserDetails user = userDetailServiceImp.loadUserByUsername("admin");
        Assertions.assertNotNull(user);
    }
}

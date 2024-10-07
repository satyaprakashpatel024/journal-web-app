package com.edigest.service;

import com.edigest.journalwebapp.entity.Users;
import com.edigest.journalwebapp.repository.UserEntryRepository;
import com.edigest.journalwebapp.services.UserDetailServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

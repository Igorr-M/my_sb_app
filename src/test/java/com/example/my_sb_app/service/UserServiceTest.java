package com.example.my_sb_app.service;

import com.example.my_sb_app.entity.Role;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private SmptMailSender mailSender;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("some@ukr.net");

        boolean isUserCreated = userService.addUser(user);
        assertTrue(isUserCreated);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to MySbApp."));
    }

    @Test
    public void addUserFailTest() {
        User user = new User();
        user.setUsername("Igor");

        Mockito.doReturn(new User()).when(userRepository).findByUsername("Igor");

        boolean isUserCreated = userService.addUser(user);
        assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void activateUserCode() {
        User user = new User();
        user.setActivationCode("bingo");
        Mockito.doReturn(user).when(userRepository).findByActivationCode("activate");
        boolean isUserActivated = userService.activateUserCode("activate");
        assertTrue(isUserActivated);
        assertNull(user.getActivationCode());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUserCode("activate");
        assertFalse(isUserActivated);
        Mockito.verify(userRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(User.class));
    }
}
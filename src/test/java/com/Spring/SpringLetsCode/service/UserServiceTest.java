package com.Spring.SpringLetsCode.service;

import com.Spring.SpringLetsCode.Model.Role;
import com.Spring.SpringLetsCode.Model.User;
import com.Spring.SpringLetsCode.Repo.RepoUser;
import com.Spring.SpringLetsCode.SpringLetsCodeApplication;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringLetsCodeApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private RepoUser repoUser;
    @MockBean
    private NotificationService mailSender;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("testMail@gmail.com");
        boolean isUserCreated = userService.addUser(user);
        assertTrue(isUserCreated);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        assertEquals(user.getEmail(),"testMail@gmail.com");
        Mockito.verify(repoUser,Mockito.times(1)).save(user); //repoUser був визваний один раз по методу save із аргументом user
        Mockito.verify(mailSender,Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Sweater."));
    }

    @Test
    public void addUserFailTest() {
        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(user)
                .when(repoUser)
                .findByUsername("John"); // повертаємо нового користовача коли repoUser визиваэ метод findByUsername і знаходить John

        boolean isUserCreated = userService.addUser(user);

        assertFalse(isUserCreated);
        Mockito.verify(repoUser,Mockito.times(0)).save(ArgumentMatchers.anyObject()); //repoUser був визваний один раз по методу save із аргументом user
        Mockito.verify(mailSender,Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("bingo!");

        Mockito.doReturn(user)
                .when(repoUser)
                .findByActivationCode("activate");

        boolean isUserActivate = userService.activateUser("activate");

        assertTrue(isUserActivate);
        assertNull(user.getActivationCode());
        Mockito.verify(repoUser,Mockito.times(1)).save(user); //repoUser був визваний один раз по методу save із аргументом user
    }

    @Test
    public void activateUserFalseTest() {
        boolean isUserActivate = userService.activateUser("activate me");

        assertFalse(isUserActivate);
        Mockito.verify(repoUser,Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}
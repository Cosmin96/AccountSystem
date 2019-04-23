package service;


import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    List<User> users;
    User user;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User(1L, "Username", "First", "Last"),
                new User(2L, "Username", "First", "Last"));
        user = new User(1L, "Username", "First", "Last");
    }

    @Test
    public void getAllUsersShouldReturnUserList() {
        when(userRepository.getAllUsers()).thenReturn(users);
        List<User> userList = userService.getAllUsers();

        assertEquals(userList.size(), 2);
        assertEquals(userList.get(0).getId(), (Long) 1L);
        assertEquals(userList.get(1).getId(), (Long) 2L);
    }

    @Test
    public void getUserShouldReturnUser() {
        when(userRepository.getUser(1L)).thenReturn(user);
        User user = userService.getUser(1L);

        assertEquals(user.getId(), (Long) 1L);
    }

    @Test
    public void addUserShouldAddUserToDatabase() {
        userService.addUser(user);
        verify(userRepository, times(1)).addUser(user);
    }
}
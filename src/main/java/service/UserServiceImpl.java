package service;

import exception.CustomException;
import model.User;
import repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<User> getUser(Long id) {
        return userRepository.getUser(id);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }
}

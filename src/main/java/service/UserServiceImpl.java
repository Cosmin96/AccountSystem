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

    public List<User> getAllUsers() throws CustomException {
        return userRepository.getAllUsers();
    }

    public List<User> getUser(Long id) throws CustomException {
        return userRepository.getUser(id);
    }

    public void addUser(User user) throws CustomException {
        userRepository.addUser(user);
    }
}

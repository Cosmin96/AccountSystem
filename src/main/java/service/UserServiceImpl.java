package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() throws Exception {
        return this.userRepository.getAllUsers();
    }

    public List<User> getUser(Long id) throws Exception {
        return this.userRepository.getUser(id);
    }

    public void addUser(User user) throws Exception {
        this.userRepository.addUser(user);
    }
}

package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() { }

    public List<User> getAllUsers() throws Exception {
        return new UserRepository().getAllUsers();
    }

    public List<User> getUser(Long id) throws Exception {
        return new UserRepository().getUser(id);
    }

    public void addUser(User user) throws Exception {
        new UserRepository().addUser(user);
    }
}

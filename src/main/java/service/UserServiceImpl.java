package service;

import exception.CustomException;
import model.User;
import repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() { }

    public List<User> getAllUsers() throws CustomException {
        return new UserRepository().getAllUsers();
    }

    public List<User> getUser(Long id) throws CustomException {
        return new UserRepository().getUser(id);
    }

    public void addUser(User user) throws CustomException {
        new UserRepository().addUser(user);
    }
}

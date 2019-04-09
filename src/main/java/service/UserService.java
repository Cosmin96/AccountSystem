package service;

import model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public List<User> getUser(Long id);
    public void addUser(User user);
}

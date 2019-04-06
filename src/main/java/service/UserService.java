package service;

import model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers() throws Exception;
    public List<User> getUser(Long id) throws Exception;
    public void addUser(User user) throws Exception;
}

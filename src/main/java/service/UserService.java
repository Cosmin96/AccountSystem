package service;

import exception.CustomException;
import model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers() throws CustomException;
    public List<User> getUser(Long id) throws CustomException;
    public void addUser(User user) throws CustomException;
}

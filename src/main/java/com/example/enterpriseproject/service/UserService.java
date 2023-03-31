package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.User;
import java.util.List;

//interface for userService
public interface UserService {
    public void save(User user);
    public List<Object> isUserPresent(User user);

}

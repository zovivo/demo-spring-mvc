package com.service.impl;

import com.input.create.UserFormCreate;
import com.model.User;
import com.repository.impl.UserRepository;
import com.service.BaseService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserService extends BaseService<UserRepository, User, Long> {

    Logger logger = Logger.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserRepository getRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            users = findAll();
        } catch (NullPointerException e) {
            logger.info(e);
        }
        return users;
    }

    public User create(UserFormCreate userFormCreate) {
        User user = new User(userFormCreate);
        user = userRepository.insert(user);
        return user;
    }

}

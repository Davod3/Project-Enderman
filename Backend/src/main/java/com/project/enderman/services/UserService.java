package com.project.enderman.services;

import com.project.enderman.entities.User;
import com.project.enderman.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired private UserRepository userRepo;

    public String addUser(String username) {

        User user = new User(username);

        this.userRepo.save(user);

        return user.getToken();

    }

    public User getUser(String username) {

        List<User> result = this.userRepo.findByUsername(username);

        return result.size()>0?result.get(0):null;

    }

    public List<User> getUsers() {

        return this.userRepo.findAll();
    }
}

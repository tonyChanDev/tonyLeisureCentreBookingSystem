package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.models.User;
import com.tonyleisurecentre.bookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id, String errMsg) throws NotFoundException {
        Optional<User> users = userRepository.findById(id);
        if (users.isEmpty()) {
            throw new NotFoundException(errMsg);
        }
        return users.stream().findFirst().get();
    }
}

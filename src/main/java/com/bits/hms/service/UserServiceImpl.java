package com.bits.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.hms.entity.User;
import com.bits.hms.repository.UserRepository;

/**
 * @author: Naichuan Zhang
 * @create: 29-Nov-2019
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public boolean validateUser(String user, String password) {
        User username = userRepository.findByUsername(user);

        return (user.equals(username.getUsername()));
    }

    @Override
    public void updateUserBalance(Long userId, Double currentBalance) {

        userRepository.updateUserBalance(currentBalance, userId);
    }
}

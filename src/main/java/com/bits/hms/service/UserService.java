package com.bits.hms.service;

import com.bits.hms.entity.User;

/**
 * @author: Naichuan Zhang
 * @create: 29-Nov-2019
 **/

public interface UserService {

    User findByUsername(String username);
    void save(User user);
    User findByUserId(Long userId);
    boolean validateUser(String username, String password);
    void updateUserBalance(Long userId, Double currentBalance);
}

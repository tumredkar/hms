package com.bits.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bits.hms.entity.User;

/**
 * @author: Naichuan Zhang
 * @create: 06-Nov-2019
 **/

// in order to execute update operation
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("update User user set user.balance=?1 where user.userId=?2")
    void updateUserBalance(Double balance, Long userId);
}

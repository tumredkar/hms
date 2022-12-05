package com.bits.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bits.hms.entity.Booking;
import com.bits.hms.entity.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findBookingByUser(User user);
}
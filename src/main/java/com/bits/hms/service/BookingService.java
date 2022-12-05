package com.bits.hms.service;

import com.bits.hms.entity.Booking;
import com.bits.hms.entity.User;

/**
 * @author: Naichuan Zhang
 * @create: 30-Nov-2019
 **/

public interface BookingService {

    void insertBooking(String bookingDate, Integer numberOfGuests, Double bookingTotal, String username);
    Booking findBookingByUser(User user);
}

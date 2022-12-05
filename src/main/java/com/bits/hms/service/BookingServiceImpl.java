package com.bits.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.hms.entity.Booking;
import com.bits.hms.entity.User;
import com.bits.hms.repository.BookingRepository;

/**
 * @author: John Long
 * @create: 28-Nov-2019
 **/

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Override
    public void insertBooking(String bookingDate, Integer numberOfGuests,
                              Double bookingTotal, String username) {
        // TODO: Add business logic
        Booking booking = new Booking();
        booking.setBookingDate(bookingDate);
        booking.setNumberOfGuests(numberOfGuests);
        booking.setUser(userService.findByUsername(username));
        booking.setBookingTotal(bookingTotal);
        bookingRepository.save(booking);
    }

    @Override
    public Booking findBookingByUser(User user) {
        Long userId = user.getUserId();
        return bookingRepository
                .findBookingByUser(userService.findByUserId(userId));
    }
}

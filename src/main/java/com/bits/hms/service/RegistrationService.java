package com.bits.hms.service;

public interface RegistrationService {
    boolean validateRegistration(String firstName, String lastName, String username, String password, String emailAddress);
}

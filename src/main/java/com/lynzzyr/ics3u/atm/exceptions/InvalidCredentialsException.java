package com.lynzzyr.ics3u.atm.exceptions;

/** Exception if passcode is incorrect. */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

package com.lynzzyr.ics3u.atm.exceptions;

/** Exception if attempting to write new account in database which already exists. */
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String message) {
        super(message);
    }
}

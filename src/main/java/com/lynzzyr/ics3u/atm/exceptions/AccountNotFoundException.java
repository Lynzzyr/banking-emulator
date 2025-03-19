package com.lynzzyr.ics3u.atm.exceptions;

/** Exception if account does not exist in database. */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

package com.lynzzyr.ics3u.atm.exceptions;

/** Exception if incoming account write stream is identical to that in the database. */
public class AccountChangeException extends RuntimeException {
    public AccountChangeException(String message) {
        super(message);
    }
}

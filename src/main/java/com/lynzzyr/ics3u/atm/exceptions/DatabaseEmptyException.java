package com.lynzzyr.ics3u.atm.exceptions;

/** Exception if there are no accounts in the database. */
public class DatabaseEmptyException extends RuntimeException {
    public DatabaseEmptyException(String message) {
        super(message);
    }
}

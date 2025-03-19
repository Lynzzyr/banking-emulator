package com.lynzzyr.ics3u.atm;

/** Base interface for all accounts. */
public interface AccountBase {
    /**
     * Writes current instance to database. If current account already exists as an entry, will rewrite with updated information.
     * 
     * Throws an AccountChangeException if there are no changes to write.
     */
    public void writeToDatabase();

    
}

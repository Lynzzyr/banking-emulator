package com.lynzzyr.ics3u.atm.accounts;

import java.util.ArrayList;
import java.util.Optional;

import org.json.simple.JSONObject;

import com.lynzzyr.ics3u.atm.Utils;
import com.lynzzyr.ics3u.atm.exceptions.AccountChangeException;
import com.lynzzyr.ics3u.atm.exceptions.AccountNotFoundException;

/** A chequing account. */
public class ChequingAccount implements AccountBase {
    private String username;            // Username
    private String passcode;            // Passcode
    private ArrayList<String> history;  // Transaction history
    private double value;               // Amount

    private ChequingAccount(String username, String passcode, ArrayList<String> history, double value) {
        this.username = username;
        this.passcode = passcode;
        this.history = history;
        this.value = value;
    }

    /** Gets the account username. */
    @Override
    public String getUsername() {
        return username;
    }

    /** Gets the account passcode. */
    @Override
    public String getPasscode() {
        return passcode;
    }

    /** Gets the account type number. */
    @Override
    public int getTypeNum() {
        return 1; // chequing accounts are type 1
    }

    /** Gets the account amount. */
    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Optional<Double> getInterest() {return null;}

    @Override
    public Optional<Integer> getFreeWithdraws() {return null;}

    /** Sets the account amount. */
    @Override
    public void setValue(double amount) {
        value = amount;
    }

    /**
     * Writes and returns new database of current ChequingAccount instance. If already exists as an entry, will rewrite with updated information.
     * 
     * Throws an AccountChangeException if there are no changes to write.
     */
    @Override
    public JSONObject writeDatabase(JSONObject db) {
        // TODO do this
        return new JSONObject();
    }

    @Override
    public void addLog(String log) {
        // TODO do this
    }

    @Override
    public void refresh() {
        // TODO do this
    }

    /**
     * Creates and returns new instance of ChequingAccount with specified details.
     * 
     * The history and amount are normally randomly generated, however they may be specified beforehand for convenience, such as for account conversion.
     */
    public static ChequingAccount createNew(String username, String passcode, Optional<ArrayList<String>> history, Optional<Double> value) {
        return new ChequingAccount(
            username, passcode, history.orElse(new ArrayList<>()),
            value.orElse(Utils.roundMoney(500.0 + (2000.0 * Math.random()))) // Random value if not specified
        );
    }

    /**
     * Returns instance of ChequingAccount with information from database with specified username.
     * 
     * Throws an InvalidCredentialsException if passcode is incorrect, throws an AccountNotFoundException if account does not exist.
     */
    public static ChequingAccount createFromDatabase(JSONObject database, String username, String passcode) {
        // Username check
        if (!database.containsKey(username)) {
            throw new AccountNotFoundException("This account does not exist!");
        }

        // Fetch user data
        JSONObject entry = (JSONObject) database.get(username);
        // TODO finish this
    }
}

package com.lynzzyr.ics3u.atm.accounts;

import java.util.ArrayList;
import java.util.Optional;

import org.json.simple.JSONObject;

import com.lynzzyr.ics3u.atm.Globals;
import com.lynzzyr.ics3u.atm.Utils;
import com.lynzzyr.ics3u.atm.exceptions.AccountNotFoundException;
import com.lynzzyr.ics3u.atm.exceptions.InvalidCredentialsException;

/** A chequing account. The history and amount may be presupplied for convenience. */
public class ChequingAccount implements AccountBase {
    private String username;            // Username
    private String passcode;            // Passcode
    private ArrayList<String> history;  // Transaction history
    private double value;               // Amount

    // New history
    private ChequingAccount(String username, String passcode, double value) {
        this.username = username;
        this.passcode = passcode;
        history = new ArrayList<>();
        this.value = value;
    }

    // Provides history
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
    public Optional<Double> getInterest() {return Optional.empty();}

    @Override
    public Optional<Integer> getFreeWithdraws() {return Optional.empty();}

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

    /** Creates and returns new instance of ChequingAccount with specified credentials. Will assign a random amount. */
    public static ChequingAccount createNew(String username, String passcode) {
        return new ChequingAccount(
            username, passcode,
            Utils.roundMoney(500.0 + (2000.0 * Math.random())) // Random value between 500 and 2500
        );
    }

    /** Creates and returns new instance of ChequingAccount with specified credentials plus details for convenience. */
    public static ChequingAccount createNew(String username, String passcode, ArrayList<String> history, double value) {
        return new ChequingAccount(
            username, passcode, new ArrayList<>(), value
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

        // Passcode check, return instance
        if (
            Utils.vigenere(entry.get("test_string_a").toString(), passcode, false).equals(username + Globals.TEST_A) &&
            Utils.vigenere(entry.get("test_string_b").toString(), passcode, false).equals(Globals.TEST_B)
        ) {
            // Decrypt history
            ArrayList<String> history = new ArrayList<>();
            for (String log : (ArrayList<String>) entry.get("history")) {
                history.add(log);
            }
            return new ChequingAccount(
                username, passcode, history,
                Double.parseDouble(Utils.vigenere(entry.get("value").toString(), passcode, false)) // Amount
            );
        } else {
            throw new InvalidCredentialsException("Incorrect passcode!");
        }
    }
}

package com.lynzzyr.ics3u.atm.accounts;

import java.util.Optional;

import org.json.simple.JSONObject;

/** Base interface for all accounts. */
public interface AccountBase {
    public String getUsername();

    public String getPasscode();

    public int getTypeNum();

    public double getValue();

    public Optional<Double> getInterest();

    public Optional<Integer> getFreeWithdraws();

    public void setValue(double amount);

    public JSONObject writeDatabase(JSONObject db);

    public void addLog(String log);

    public void refresh();
}

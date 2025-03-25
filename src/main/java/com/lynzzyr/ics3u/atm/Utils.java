package com.lynzzyr.ics3u.atm;


import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.lynzzyr.ics3u.atm.accounts.AccountBase;
import com.lynzzyr.ics3u.atm.exceptions.AccountChangeException;

/** Class for utility methods. */
public class Utils {
    /** Rounds to 2 decimal points for for financial numbers. */
    public static double roundMoney(double amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Performs an alphanumeric Vigenere cipher encryption or decryption and returns resulting text.
     * The cipher character set is comprised, in the following order, of all lowercase letters then all digits from 0 to 9.
     * Any characters outside of this set will remain unmodified in the output text but will still use an index of the key.
     * 
     * @param encrypt Default true. If decryption is desired set to false.
     */
    public static String vigenere(String text, String key, boolean doEncrypt) {
        String out = "";

        // Loop for every character in input text
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            char k = Character.toLowerCase(key.charAt(i % key.length()));

            // Main turnary, may pass foreign chars
            out = (Globals.VIG_SET.contains(Character.getNumericValue(c))) ? out + Character.toString(Globals.VIG_SET.get(
                (Globals.VIG_SET.indexOf(Character.getNumericValue(c)) + ((doEncrypt ? 1 : -1) * Globals.VIG_SET.indexOf(Character.getNumericValue(k)))) % Globals.VIG_SET.size()
            )) : out + c;
        }
        return out;
    }

    /** Common method to attempt an account save to the database. */
    public static void saveSession(AccountBase session) {
        // Load, write, update
        try {
            JSONObject db = (JSONObject) new JSONParser().parse(new FileReader("database.json"));
            try {
                db = session.writeDatabase(db);
            } catch (AccountChangeException e) {}
            try (PrintWriter pw = new PrintWriter("database.json")) {
                pw.write(db.toJSONString());
            }
        } catch (IOException | ParseException e) {} // :)
    }

    /** Method for hidden action to compound savings accounts with months. Will R/W to database and session low level. */
    public static void compoundSavings(AccountBase session) {
        // Get months input
        int months = 0;
        while (true) {
            try (Scanner scan = new Scanner(System.in)) {
                months = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {} // continue
        }

        // Database handling
        try {
            JSONObject db = (JSONObject) new JSONParser().parse(new FileReader("database.json"));
            for (String user : (ArrayList<String>) new ArrayList<>(db.keySet())) {
                // If current savings account is current key
                if (session != null && session.getTypeNum() == 2 && session.getUsername().equals(user)) {
                    session.setValue(roundMoney(session.getValue() * (1 + session.getInterest().get() / 100 * months)));
                    saveSession(session);
                } else {
                    JSONObject dbUser = (JSONObject) db.get(user);
                    if (dbUser.get("type").toString().equals("SAVINGS")) {
                        dbUser.put("months", dbUser.containsKey("months") ? Integer.parseInt(dbUser.get("months").toString()) + months : months);
                        db.put(user, dbUser.toJSONString());
                    }
                }
            }
            try (PrintWriter pw = new PrintWriter("database.json")) {
                pw.write(db.toJSONString());
            }
        } catch (IOException | ParseException e) {}
    }
}

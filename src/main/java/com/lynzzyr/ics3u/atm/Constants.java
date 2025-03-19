package com.lynzzyr.ics3u.atm;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    // Credit to https://patorjk.com/software/taag for ASCII art generator
    String initArt = """
  ___ ____ ____    ____              _    
 |_ _/ ___/ ___|  | __ )  __ _ _ __ | | __
  | | |   \\___ \\  |  _ \\ / _` | '_ \\| |/ /
  | | |___ ___) | | |_) | (_| | | | |   < 
 |___\\____|____/  |____/ \\__,_|_| |_|_|\\_\\
                                          
""";

    // Unicode decimal values of the Vigenere char set, this makes cipher much simpler
    public static final List<Integer> vigSet = Arrays.asList(
        // Lowercase letters "a" to "z"
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106,
        107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
        117, 118, 119, 120, 121, 122,
        // Digits 0 to 9
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57
    );
}

package com.mdowds.assembler;

import java.util.HashMap;

public class Lookups {
    public static HashMap<String, String> dest() {
        HashMap<String, String> lookup = new HashMap<String, String>();
        lookup.put("M", "001");
        lookup.put("D", "010");
        lookup.put("MD", "011");
        lookup.put("A", "100");
        lookup.put("AM", "101");
        lookup.put("AD", "110");
        lookup.put("AMD", "111");

        return lookup;
    }

    public static HashMap<String, String> comp() {
        HashMap<String, String > lookup = new HashMap<String, String>();
        lookup.put("0", "101010");
        lookup.put("1", "111111");
        lookup.put("-1", "111010");
        lookup.put("D", "001100");
        lookup.put("A", "110000");
        lookup.put("!D", "001101");
        lookup.put("!A", "110001");
        lookup.put("-D", "001111");
        lookup.put("-A", "110011");
        lookup.put("D+1", "011111");
        lookup.put("A+1", "110111");
        lookup.put("D-1", "001110");
        lookup.put("A-1", "110010");
        lookup.put("D+A", "000010");
        lookup.put("D-A", "010011");
        lookup.put("A-D", "000111");
        lookup.put("D&A", "000000");
        lookup.put("D|A", "010101");

        return lookup;
    }

    public static HashMap<String, String> jump() {
        HashMap<String, String> lookup = new HashMap<String, String>();
        lookup.put("JGT", "001");
        lookup.put("JEQ", "010");
        lookup.put("JGE", "011");
        lookup.put("JLT", "100");
        lookup.put("JNE", "101");
        lookup.put("JLE", "110");
        lookup.put("JMP", "111");

        return lookup;
    }
}

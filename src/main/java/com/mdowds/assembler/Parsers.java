package com.mdowds.assembler;

import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;

public class Parsers {

    public static String parseDInstruction(String line) {
        String[] splitLine = line.split("=");

        String a = parseA(splitLine[1]);
        String comp = parseComp(splitLine[1]);
        String dest = parseDest(splitLine[0]);

        return "111" + a + comp + dest + "000";
    }

    public static String parseDJumpInstruction(String line) {
        String[] splitLine = line.split(";");

        String a = parseA(splitLine[0]);
        String comp = parseComp(splitLine[0]);
        String jump = parseJump(splitLine[1]);

        return "111" + a + comp + "000" + jump;
    }

    public static String parseAInstruction(String line) {
        int value = Integer.parseInt(line);
        String binaryValue = Integer.toBinaryString(value);
        return "0" + StringUtils.leftPad(binaryValue, 15, "0");
    }

    private static String parseDest(String dest) {
        return lookup(dest, Lookups.dest());
    }

    private static String parseComp(String comp) {
        return lookup(comp.replace("M","A"), Lookups.comp());
    }

    private static String parseJump(String jump) {
        return lookup(jump, Lookups.jump());
    }

    private static String parseA(String comp) {
        return comp.contains("M") ? "1" : "0";
    }

    private static String lookup(String value, HashMap<String, String> lookupTable) {
        return lookupTable.get(value);
    }
}

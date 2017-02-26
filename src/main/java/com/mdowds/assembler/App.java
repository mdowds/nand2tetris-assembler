package com.mdowds.assembler;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class App {

    enum Instruction {
        A, D, DJUMP, NULL
    }

    public static void main( String[] args ) {
        try {
            String fileName = args[0];
            List<String> linesIn = IO.readFile(fileName);
            List<String> linesOut = parseLines(linesIn);
            IO.writeFile(fileName, linesOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> parseLines(List<String> linesIn) {
        List<String> linesOut = new ArrayList<String>();

        for (String line : linesIn) {
            Instruction instruction = determineInstruction(line);
            String machineCode = parseInstruction(line, instruction);
            linesOut.add(machineCode);
        }

        return linesOut;
    }

    private static String parseInstruction(String line, Instruction instruction) {
        switch (instruction) {
            case A: return Parsers.parseAInstruction(line);
            case D: return Parsers.parseDInstruction(line);
            case DJUMP: return Parsers.parseDJumpInstruction(line);
        }

        throw new InvalidParameterException("Line does not contain a valid instruction: " + line);
    }

    private static Instruction determineInstruction(String line) {
        if (line.startsWith("@")){
            return Instruction.A;
        } else if(matchRegex("[AMD]+=[AMD+\\-01!&|]+", line)) {
            return Instruction.D;
        } else if(matchRegex("[D0];J(MP|GT|LE|EQ|GE|LT|NE)", line)) {
            return Instruction.DJUMP;
        } else {
            return Instruction.NULL;
        }
    }

    private static boolean matchRegex(String pattern, String line) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
}

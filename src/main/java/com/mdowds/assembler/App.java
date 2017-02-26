package com.mdowds.assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;

public class App {

    private static String aRegex = "@\\d+";
    private static String aSymbolRegex = "@\\S+";
    private static String dRegex = "[AMD]+=[AMD+\\-01!&|]+";
    private static String dJumpRegex = "[D0];J(?:MP|GT|LE|EQ|GE|LT|NE)";

    enum Instruction {
        A, ASYMBOL, D, DJUMP, NULL
    }

    public static void main( String[] args ) {
        try {
            String fileName = args[0];
            List<String> linesIn = IO.readFile(fileName);

            HashMap<String, String> symbolTable = buildSymbolTable(linesIn);
            List<String> linesOut = parseLines(linesIn, symbolTable);

            IO.writeFile(fileName, linesOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> parseLines(List<String> linesIn, HashMap<String, String> symbolTable) {
        List<String> linesOut = new ArrayList<String>();
        int nextFreeAddress = 16;

        for (String line : linesIn) {
            Instruction instructionType = determineInstruction(line);
            String machineCode;

            if(instructionType != Instruction.NULL) {
                String instruction = extractInstruction(line, instructionPattern(instructionType));

                if(instructionType == Instruction.ASYMBOL) {

                    String address = instruction.substring(1);
                    if(symbolTable.containsKey(address)){
                        machineCode = Parsers.parseAInstruction(symbolTable.get(address));
                    } else {
                        symbolTable.put(address, Integer.toString(nextFreeAddress));
                        machineCode = Parsers.parseAInstruction(symbolTable.get(address));
                        nextFreeAddress++;
                    }

                } else {
                    machineCode = parseInstruction(instruction, instructionType);
                }

                linesOut.add(machineCode);
            }
        }

        return linesOut;
    }

    private static String parseInstruction(String instruction, Instruction type) {
        switch (type) {
            case A: return Parsers.parseAInstruction(instruction.substring(1));
            case D: return Parsers.parseDInstruction(instruction);
            case DJUMP: return Parsers.parseDJumpInstruction(instruction);
            default: return "";
        }
    }

    private static Instruction determineInstruction(String line) {
        if (lineMatchesPattern(aRegex, line)) {
            return Instruction.A;
        } else if (lineMatchesPattern(aSymbolRegex, line)) {
            return Instruction.ASYMBOL;
        } else if(lineMatchesPattern(dRegex, line)) {
            return Instruction.D;
        } else if(lineMatchesPattern(dJumpRegex, line)) {
            return Instruction.DJUMP;
        } else {
            return Instruction.NULL;
        }
    }

    private static boolean isInstruction(String line) {
        return determineInstruction(line) != Instruction.NULL;
    }

    private static boolean lineMatchesPattern(String pattern, String line) {
        Matcher matcher = matchRegex(pattern, line);
        return matcher.find();
    }

    private static Matcher matchRegex(String pattern, String line) {
        Pattern regex = Pattern.compile(pattern);
        return regex.matcher(line);
    }

    private static HashMap<String, String> buildSymbolTable(List<String> lines) {
        HashMap<String, String> symbolTable = Lookups.symbolTable();

        int lineNo = 0;

        for (String line : lines) {

            if(isInstruction(line)) {
                lineNo++;
            } else {
                Matcher matcher = matchRegex("\\(([\\S]*)\\)", line);
                if(matcher.find()) {
                    symbolTable.put(matcher.group(1), Integer.toString(lineNo));
                }
            }
        }

        return symbolTable;
    }

    private static String instructionPattern(Instruction type) {
        switch (type) {
            case A: return aRegex;
            case ASYMBOL: return aSymbolRegex;
            case D: return dRegex;
            case DJUMP: return dJumpRegex;
            default: return "";
        }
    }

    private static String extractInstruction(String line, String pattern) {
        Matcher matcher = matchRegex(pattern, line);
        boolean find = matcher.find();
        return matcher.group(0);
    }
}

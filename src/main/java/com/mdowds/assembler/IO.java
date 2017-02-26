package com.mdowds.assembler;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IO {

    public static List<String> readFile(String fileName) throws IOException {
        File file = new File(fileName + ".asm");
        return FileUtils.readLines(file, "UTF-8");
    }

    public static void writeFile(String fileName, List<String> lines) throws IOException {
        File file = new File(fileName + ".hack");
        FileUtils.writeLines(file, lines);
        System.out.println("File created at " + fileName + ".hack");
    }
}

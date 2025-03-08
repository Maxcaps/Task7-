package org.example.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleReader implements Reader {
    private static final Logger logger = LogManager.getLogger(ConsoleReader.class);

    @Override
    public List<String> read() throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        logger.info("Enter the coordinates of the points in x,y format (or a blank line to stop typing)");
        String line;
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) { // Читаем строки до пустой строки
            lines.add(line.trim());
        }
        return lines;
    }
}

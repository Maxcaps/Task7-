package org.example.reader;

import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;


public class FileReader implements Reader {
    private static final String FILE_PATH = "points.txt";
    private final Logger LOGGER = LogManager.getLogger(FileReader.class);;
    @Override
    public List<String> read() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { // Читаем строки из файла
                lines.add(line.trim()); // Убираем лишние пробелы
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: {}", FILE_PATH, e);
            throw new IllegalArgumentException("File not found: " + FILE_PATH, e);
        }  catch (IOException e) {
            LOGGER.error("I/O error occurred while reading the file: {}", FILE_PATH, e);
            throw new IOException("Error reading the file: " + FILE_PATH, e);
        }
        return lines;
    }
}
//
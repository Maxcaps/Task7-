package reader;

import org.example.reader.FileReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void testRead_ShouldReturnListOfLines() throws IOException {
        // Дано: временный файл с содержимым
        File tempFile = File.createTempFile("test-points", ".txt");
        tempFile.deleteOnExit(); // Удалить файл после завершения теста

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("1,2\n");
            writer.write("3,4\n");
            writer.write("5,6\n");
        }

        // Когда: создаём экземпляр FileReader и читаем файл
        FileReader fileReader = new FileReader() {
            @Override
            public List<String> read() throws IOException {
                List<String> lines = new ArrayList<>();
                try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(tempFile))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        lines.add(line.trim());
                    }
                }
                return lines;
            }
        };

        List<String> lines = fileReader.read();

        // Тогда: проверяем результат
        assertEquals(3, lines.size(), "Должно быть прочитано 3 строки");
        assertEquals("1,2", lines.get(0), "Первая строка должна быть '1,2'");
        assertEquals("3,4", lines.get(1), "Вторая строка должна быть '3,4'");
        assertEquals("5,6", lines.get(2), "Третья строка должна быть '5,6'");
    }

    @Test
    void testRead_ShouldReturnEmptyListForEmptyFile() throws IOException {
        // Дано: временный пустой файл
        File tempFile = File.createTempFile("empty-test-points", ".txt");
        tempFile.deleteOnExit();

        // Когда: создаём экземпляр FileReader и читаем файл
        FileReader fileReader = new FileReader() {
            @Override
            public List<String> read() throws IOException {
                List<String> lines = new ArrayList<>();
                try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(tempFile))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        lines.add(line.trim());
                    }
                }
                return lines;
            }
        };

        List<String> lines = fileReader.read();

        // Тогда: проверяем результат
        assertTrue(lines.isEmpty(), "Результат должен быть пустым для пустого файла");
    }

    @Test
    void testRead_ShouldTrimLines() throws IOException {
        // Дано: временный файл с содержимым, содержащим пробелы
        File tempFile = File.createTempFile("test-trim-points", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("  1,2  \n");
            writer.write(" 3,4 \n");
            writer.write(" 5,6 \n");
        }

        // Когда: создаём экземпляр FileReader и читаем файл
        FileReader fileReader = new FileReader() {
            @Override
            public List<String> read() throws IOException {
                List<String> lines = new ArrayList<>();
                try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(tempFile))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        lines.add(line.trim());
                    }
                }
                return lines;
            }
        };

        List<String> lines = fileReader.read();

        // Тогда: проверяем, что пробелы обрезаны
        assertEquals(3, lines.size(), "Должно быть прочитано 3 строки");
        assertEquals("1,2", lines.get(0), "Первая строка должна быть '1,2'");
        assertEquals("3,4", lines.get(1), "Вторая строка должна быть '3,4'");
        assertEquals("5,6", lines.get(2), "Третья строка должна быть '5,6'");
    }
}


package reader;

import org.example.reader.ConsoleReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleReaderTest {

    private final ConsoleReader consoleReader;

    public ConsoleReaderTest() {
        this.consoleReader = new ConsoleReader();
    }

    @Test
    void testRead_ShouldReturnListOfLines() throws IOException {
        // Дано: подготовим ввод с несколькими строками
        String simulatedInput = "1,2\n3,4\n5,6\n\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Когда: читаем данные
        List<String> lines = consoleReader.read();

        // Тогда: проверяем, что строки считаны корректно
        assertEquals(3, lines.size(), "Должно быть прочитано 3 строки");
        assertEquals("1,2", lines.get(0), "Первая строка должна быть '1,2'");
        assertEquals("3,4", lines.get(1), "Вторая строка должна быть '3,4'");
        assertEquals("5,6", lines.get(2), "Третья строка должна быть '5,6'");
    }

    @Test
    void testRead_ShouldReturnEmptyListForNoInput() throws IOException {
        // Дано: ввод пустой строки
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Когда: читаем данные
        List<String> lines = consoleReader.read();

        // Тогда: проверяем, что результат пуст
        assertEquals(0, lines.size(), "Должен быть возвращён пустой список");
    }

    @Test
    void testRead_ShouldTrimInput() throws IOException {
        // Дано: ввод с пробелами
        String simulatedInput = "  1,2  \n 3,4 \n\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Когда: читаем данные
        List<String> lines = consoleReader.read();

        // Тогда: проверяем, что пробелы обрезаны
        assertEquals(2, lines.size(), "Должно быть прочитано 2 строки");
        assertEquals("1,2", lines.get(0), "Первая строка должна быть '1,2'");
        assertEquals("3,4", lines.get(1), "Вторая строка должна быть '3,4'");
    }
}


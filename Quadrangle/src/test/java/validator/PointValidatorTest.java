package validator;

import org.example.validator.PointValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointValidatorTest {

    private final PointValidator pointValidator = new PointValidator();

    @Test
    void testValidate_ShouldReturnTrueForValidInput() {
        // Дано: корректный ввод
        String validInput = "3,5";

        // Когда: вызываем метод validate
        boolean result = pointValidator.validate(validInput);

        // Тогда: результат должен быть true
        assertTrue(result, "Должно возвращаться true для корректного ввода '3,5'");
    }

    @Test
    void testValidate_ShouldReturnFalseForInvalidInput() {
        // Дано: некорректные строки
        String invalidInput1 = "3.5,5"; // Используется точка
        String invalidInput2 = "3,5,6"; // Слишком много координат
        String invalidInput3 = "3a,5";  // Содержит букву
        String invalidInput4 = "";      // Пустая строка

        // Когда: проверяем метод validate
        assertFalse(pointValidator.validate(invalidInput1), "Должно возвращаться false для '3.5,5'");
        assertFalse(pointValidator.validate(invalidInput2), "Должно возвращаться false для '3,5,6'");
        assertFalse(pointValidator.validate(invalidInput3), "Должно возвращаться false для '3a,5'");
        assertFalse(pointValidator.validate(invalidInput4), "Должно возвращаться false для пустой строки");
    }

    @Test
    void testValidate_ShouldReturnFalseForNullInput() {
        // Дано: null
        String nullInput = null;

        // Когда: вызываем метод validate
        boolean result = pointValidator.validate(nullInput);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для null");
    }
}


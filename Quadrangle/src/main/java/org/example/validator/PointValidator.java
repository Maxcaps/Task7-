package org.example.validator;

public class PointValidator {
    public boolean validate(String input) {
        // Проверка на соответствие шаблону
        return input != null && input.matches("\\d+,\\d+");
    }
}

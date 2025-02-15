package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;

import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QuadrangleParameters {
    private static final Logger LOGGER = Logger.getLogger(QuadrangleParameters.class.getName());

    private double area;
    private double perimeter;
    private QuadrangleType type;
    private boolean isConvex;

    public QuadrangleParameters(Quadrangle quadrangle, QuadrangleCalculator quadrangleCalculator) {
        this.area = quadrangleCalculator.calculateArea(quadrangle);
        this.perimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        this.type = quadrangleCalculator.findQuadrangleType(quadrangle);
        this.isConvex = quadrangleCalculator.isConvex(quadrangle);
    }

    @JsonCreator
    public QuadrangleParameters(
            @JsonProperty("area") double area,
            @JsonProperty("perimeter") double perimeter,
            @JsonProperty("type") QuadrangleType type,
            @JsonProperty("convex") boolean isConvex) {
        this.area = area;
        this.perimeter = perimeter;
        this.type = type;
        this.isConvex = isConvex;
    }

    public QuadrangleParameters() {
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public void setType(QuadrangleType type) {
        this.type = type;
    }

    public void setConvex(boolean convex) {
        isConvex = convex;
    }

    public double getArea() { return area; }
    public double getPerimeter() { return perimeter; }
    public QuadrangleType getType() { return type; }
    public boolean isConvex() { return isConvex; }



    @Override
    public String toString() {
        return String.format("type=%s, area=%.2f, perimeter=%.2f, convex=%b", type, area, perimeter, isConvex);
    }

    //  Метод для сохранения в JSON с логированием
    public void serializeToJson(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Путь к папке target/
            String projectPath = System.getProperty("user.dir") + "/target";
            File file = new File(projectPath, filename);

            // Создаем папку target, если её нет
            new File(projectPath).mkdirs();

            // Записываем объект в JSON-файл
            objectMapper.writeValue(file, this);
            LOGGER.info("Объект сохранен в JSON-файл: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при сохранении в JSON", e);
        }
    }


    // Метод для загрузки из JSON с логированием
    public static QuadrangleParameters deserializeFromJson(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Читаем JSON из target/
            String projectPath = System.getProperty("user.dir") + "/target";
            File file = new File(projectPath, filename);

            return objectMapper.readValue(file, QuadrangleParameters.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при чтении JSON-файла", e);
            return null;
        }
    }
}

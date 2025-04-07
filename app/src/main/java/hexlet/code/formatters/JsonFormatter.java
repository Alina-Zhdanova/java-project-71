package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.Change;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public final class JsonFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var changesJson = new LinkedHashMap<String, Object>();

        for (var line : changes) {

            switch (line.status()) {

                case NOT_CHANGED -> { }

                case CHANGED -> {
                    var change = new LinkedHashMap<String, Object>();
                    change.put("-", line.pastValue());
                    change.put("+", line.presentValue());

                    changesJson.put(line.key(), change);
                }

                case DELETED -> {
                    // если "-", но соседние ключи не равны, значит элемент удалён
                    var change = new LinkedHashMap<String, Object>();
                    change.put("-", line.pastValue());

                    changesJson.put(line.key(), change);
                }

                case ADDED -> {
                    // и если "+", то значение было добавлено
                    var change = new LinkedHashMap<String, Object>();
                    change.put("+", line.presentValue());

                    changesJson.put(line.key(), change);
                }

                default -> throw new Error("Unknown status!");

            }
        }

        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/test/resources/fixtures/output.json"), changesJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(changesJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

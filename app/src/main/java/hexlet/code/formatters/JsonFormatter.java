package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.Change;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public final class JsonFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var informationAboutChanges = changes.stream()
            .filter(change -> !Objects.equals(change.getChange(), "Not changed"))
            .toList();
        var changesJson = new LinkedHashMap<String, Object>();

        var i = 0;
        while (i < informationAboutChanges.size()) {

            switch (informationAboutChanges.get(i).getChange()) {
                case "Changed" -> {

                    var change = new LinkedHashMap<String, Object>();
                    change.put("-", informationAboutChanges.get(i).getPastValue());
                    change.put("+", informationAboutChanges.get(i).getPresentValue());

                    changesJson.put(informationAboutChanges.get(i).getKey(), change);

                    i = i + 1;

                }
                case "Deleted" -> {

                    // если "-", но соседние ключи не равны, значит элемент удалён
                    var change = new LinkedHashMap<String, Object>();
                    change.put("-", informationAboutChanges.get(i).getPastValue());

                    changesJson.put(informationAboutChanges.get(i).getKey(), change);

                    i = i + 1;

                }
                case "Added" -> {

                    // и если "+", то значение было добавлено
                    var change = new LinkedHashMap<String, Object>();
                    change.put("+", informationAboutChanges.get(i).getPresentValue());

                    changesJson.put(informationAboutChanges.get(i).getKey(), change);

                    i = i + 1;
                }
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

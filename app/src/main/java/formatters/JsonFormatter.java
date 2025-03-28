package formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.Change;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class JsonFormatter implements FormatterInterface {

    private static String getPath(String fileName) {
        return String.valueOf(Paths.get("src", "test", "resources", "fixtures", fileName)
            .toAbsolutePath().normalize());
    }

    @Override
    public String formatter(List<Change> changes) {

        var informationAboutChanges = changes.stream()
            .filter(change -> !Objects.equals(change.getChange(), " "))
            .toList();
        var changesJson = new LinkedHashMap<String, Object>();

        var i = 0;
        while (i < informationAboutChanges.size()) {

            if (informationAboutChanges.get(i).getChange().equals("-")) {

                if (i != informationAboutChanges.size() - 1) {

                    // проверяем, равны ли ключи, если ключи равны, значит значение было изменено
                    if (informationAboutChanges.get(i).getKey().equals(informationAboutChanges.get(i + 1).getKey())) {

                        var change = new LinkedHashMap<String, Object>();
                        change.put("-", informationAboutChanges.get(i).getPastValue());
                        change.put("+", informationAboutChanges.get(i + 1).getPastValue());

                        changesJson.put(informationAboutChanges.get(i).getKey(), change);

                        i = i + 2;

                    } else {

                        // если "-", но соседние ключи не равны, значит элемент удалён
                        var change = new LinkedHashMap<String, Object>();
                        change.put("-", informationAboutChanges.get(i).getPastValue());

                        changesJson.put(informationAboutChanges.get(i).getKey(), change);

                        i = i + 1;

                    }
                }
            } else {

                // и если "+", то значение было добавлено
                var change = new LinkedHashMap<String, Object>();
                change.put("+", informationAboutChanges.get(i).getPastValue());

                changesJson.put(informationAboutChanges.get(i).getKey(), change);

                i = i + 1;
            }
        }

        var pathFile = getPath("OutputFormatter.json");
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
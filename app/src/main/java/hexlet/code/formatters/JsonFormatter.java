package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.Change;

import java.util.List;

public final class JsonFormatter implements FormatterInterface {

    @Override
    public String format(List<Change> changes) {

        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(changes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

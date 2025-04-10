package hexlet.code.formatters;

import hexlet.code.Change;

import java.io.IOException;
import java.util.List;

public interface FormatterInterface {
    String format(List<Change> changes) throws IOException;
}

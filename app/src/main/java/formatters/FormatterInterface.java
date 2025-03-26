package formatters;

import hexlet.code.Change;

import java.io.IOException;
import java.util.List;

public interface FormatterInterface {
    String formatter(List<Change> changes) throws IOException;
}

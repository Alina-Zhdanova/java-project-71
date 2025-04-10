package hexlet.code.formatters;

import hexlet.code.Change;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PlainFormatter implements FormatterInterface {

    private static Object formatValue(Object value) {
        if (value instanceof Map || value instanceof Iterable || value instanceof Array) {
            return "[complex value]";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value;
    }

    @Override
    public String formatter(List<Change> changes) {

        var changesPlain = new ArrayList<String>();

        for (var line : changes) {

            switch (line.status()) {

                case NOT_CHANGED -> { }

                case CHANGED -> {
                    var pastValue = line.pastValue();
                    var presentValue = line.presentValue();
                    var formatPastValue = formatValue(pastValue);
                    var formatPresentValue = formatValue(presentValue);

                    var change = "Property '" + line.key() + "' was updated. From " + formatPastValue
                        + " to " + formatPresentValue;
                    changesPlain.add(change);
                }

                case DELETED -> {
                    var change = "Property '" + line.key() + "' was removed";
                    changesPlain.add(change);
                }

                case ADDED -> {
                    var value = line.presentValue();
                    var formatValue = formatValue(value);

                    var change = "Property '" + line.key() + "' was added with value: " + formatValue;
                    changesPlain.add(change);
                }

                default -> throw new RuntimeException("Unknown status!");

            }
        }
        return String.join("\n", changesPlain);
    }
}

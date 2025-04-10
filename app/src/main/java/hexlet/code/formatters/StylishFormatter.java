package hexlet.code.formatters;

import hexlet.code.Change;

import java.util.List;

public final class StylishFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var result = new StringBuilder("{\n");

        for (Change change : changes) {
            switch (change.status()) {
                case NOT_CHANGED -> {
                    result.append("    ");
                    result.append(change.key());
                    result.append(": ");
                    result.append(change.pastValue());
                    result.append("\n");
                }
                case CHANGED -> {
                    result.append("  - ");
                    result.append(change.key());
                    result.append(": ");
                    result.append(change.pastValue());
                    result.append("\n");
                    result.append("  + ");
                    result.append(change.key());
                    result.append(": ");
                    result.append(change.presentValue());
                    result.append("\n");
                }
                case DELETED -> {
                    result.append("  - ");
                    result.append(change.key());
                    result.append(": ");
                    result.append(change.pastValue());
                    result.append("\n");
                }
                case ADDED -> {
                    result.append("  + ");
                    result.append(change.key());
                    result.append(": ");
                    result.append(change.presentValue());
                    result.append("\n");
                }
                default -> throw new RuntimeException("Unknown status!");
            }
        }
        result.append("}");
        return result.toString();
    }
}

package hexlet.code.formatters;

import hexlet.code.Change;

import java.util.List;

public final class StylishFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var result = new StringBuilder("{\n");

        for (Change change : changes) {
            switch (change.getChange()) {
                case "Not changed" -> {
                    result.append("    ");
                    result.append(change.getKey());
                    result.append(": ");
                    result.append(change.getPastValue());
                    result.append("\n");
                }
                case "Changed" -> {
                    result.append("  - ");
                    result.append(change.getKey());
                    result.append(": ");
                    result.append(change.getPastValue());
                    result.append("\n");
                    result.append("  + ");
                    result.append(change.getKey());
                    result.append(": ");
                    result.append(change.getPresentValue());
                    result.append("\n");
                }
                case "Deleted" -> {
                    result.append("  - ");
                    result.append(change.getKey());
                    result.append(": ");
                    result.append(change.getPastValue());
                    result.append("\n");
                }
                case "Added" -> {
                    result.append("  + ");
                    result.append(change.getKey());
                    result.append(": ");
                    result.append(change.getPresentValue());
                    result.append("\n");
                }
                default -> throw new Error("Unknown status!");
            }
        }
        result.append("}");
        return result.toString();
    }
}

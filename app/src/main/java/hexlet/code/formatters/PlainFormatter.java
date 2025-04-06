package hexlet.code.formatters;

import hexlet.code.Change;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PlainFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var informationAboutChanges = changes.stream()
            .filter(change -> !Objects.equals(change.getChange(), "Not changed"))
            .toList();
        var changesPlain = new ArrayList<String>();

        var i = 0;
        while (i < informationAboutChanges.size()) {

            // если значение изменено
            switch (informationAboutChanges.get(i).getChange()) {

                case "Changed" -> {
                    // проверяем, является ли первое значение [complex value], если да, то следуют 2 случая
                    if (informationAboutChanges.get(i).getPastValue() instanceof Map
                        || informationAboutChanges.get(i).getPastValue() instanceof Iterable
                        || informationAboutChanges.get(i).getPastValue() instanceof Array) {

                        // 1 случай - второе значение тоже является [complex value]
                        if (informationAboutChanges.get(i).getPresentValue() instanceof Map
                            || informationAboutChanges.get(i).getPresentValue() instanceof Iterable
                            || informationAboutChanges.get(i).getPresentValue() instanceof Array) {
                            // добавляем в лист строку про изменение с [complex value] на [complex value]
                            var change = "Property '" + informationAboutChanges.get(i).getKey()
                                + "' was updated. From [complex value] to [complex value]";
                            changesPlain.add(change);
                            // увеличиваем счётчик
                            i = i + 1;

                        } else {
                            // 2 случай - второе значение не является вложенным
                            // но проверяем, является ли оно строкой
                            if (informationAboutChanges.get(i).getPresentValue() instanceof String) {
                                // если да, то добавляем в лист строку с '' вокруг 2 значения
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From [complex value] to '"
                                    + informationAboutChanges.get(i).getPresentValue() + "'";
                                changesPlain.add(change);
                                i = i + 1;

                            } else {
                                // если не строка, то добавляем строку об изменении без ''
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From [complex value] to "
                                    + informationAboutChanges.get(i).getPresentValue();
                                changesPlain.add(change);
                                i = i + 1;
                            }
                        }

                        // рассматриваем также случаи, в которых первое значение не является вложенным
                    } else {
                        // 1 - первое - не комплексное, второе [complex value]
                        if (informationAboutChanges.get(i).getPresentValue() instanceof Map
                            || informationAboutChanges.get(i).getPresentValue() instanceof Iterable
                            || informationAboutChanges.get(i).getPresentValue() instanceof Array) {
                            // если первое значение - строка, то добавляем в лист строку с '' вокруг 1 значения
                            if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                    + "' to  [complex value]";
                                changesPlain.add(change);
                                i = i + 1;

                            } else {
                                // если не строка, то добавляем строку об изменении без ''
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                    + " to [complex value]";
                                changesPlain.add(change);
                                i = i + 1;
                            }

                        } else {
                            // 2 - первое и второе значения не являются комплексными
                            // 1 и 2 значения - строки, значит вокруг каждого ставим ''
                            if (informationAboutChanges.get(i).getPastValue() instanceof String
                                && informationAboutChanges.get(i).getPresentValue() instanceof String) {
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                    + "' to '" + informationAboutChanges.get(i).getPresentValue() + "'";
                                changesPlain.add(change);
                                i = i + 1;

                            } else if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                    + "' to " + informationAboutChanges.get(i).getPresentValue();
                                changesPlain.add(change);
                                i = i + 1;

                            } else if (informationAboutChanges.get(i).getPresentValue() instanceof String) {
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                    + " to '" + informationAboutChanges.get(i).getPresentValue() + "'";
                                changesPlain.add(change);
                                i = i + 1;

                            } else {
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                    + " to " + informationAboutChanges.get(i).getPresentValue();
                                changesPlain.add(change);
                                i = i + 1;
                            }
                        }
                    }
                }

                case "Deleted" -> {
                    // если "-", но соседние ключи не равны, значит элемент удалён
                    var change = "Property '" + informationAboutChanges.get(i).getKey() + "' was removed";
                    changesPlain.add(change);
                    i = i + 1;
                }

                case "Added" -> {
                    // проверяем [complex value] или нет
                    if (informationAboutChanges.get(i).getPresentValue() instanceof Map
                        || informationAboutChanges.get(i).getPresentValue() instanceof Iterable
                        || informationAboutChanges.get(i).getPresentValue() instanceof Array) {
                        var change = "Property '" + informationAboutChanges.get(i).getKey()
                            + "' was added with value: [complex value]";
                        changesPlain.add(change);
                        i = i + 1;

                    } else {
                        // и если не [complex value], то делаем проверку на строку
                        if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                            var change = "Property '" + informationAboutChanges.get(i).getKey()
                                + "' was added with value: '" + informationAboutChanges.get(i).getPresentValue() + "'";
                            changesPlain.add(change);
                            i = i + 1;

                        } else {
                            var change = "Property '" + informationAboutChanges.get(i).getKey()
                                + "' was added with value: " + informationAboutChanges.get(i).getPresentValue();
                            changesPlain.add(change);
                            i = i + 1;
                        }
                    }
                }
                default -> throw new Error("Unknown status!");
            }
        }
        return String.join("\n", changesPlain);
    }
}

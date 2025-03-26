package formatters;

import hexlet.code.Change;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter implements FormatterInterface {

    @Override
    public String formatter(List<Change> changes) {

        var informationAboutChanges = changes.stream()
            .filter(change -> !Objects.equals(change.getChange(), " "))
            .toList();
        var changesPlain = new ArrayList<String>();

        var i = 0;
        while (i < informationAboutChanges.size()) {

            // если изменение со знаком "-"
            if (informationAboutChanges.get(i).getChange().equals("-")) {

                if (i != informationAboutChanges.size() - 1) {

                    // то проверяем, равны ли ключи, если ключи равны, значит значение было изменено
                    if (informationAboutChanges.get(i).getKey().equals(informationAboutChanges.get(i + 1).getKey())) {

                        // проверяем, является ли первое значение [complex value], если да, то следуют 2 случая
                        if (informationAboutChanges.get(i).getPastValue() instanceof Map
                            || informationAboutChanges.get(i).getPastValue() instanceof Iterable
                            || informationAboutChanges.get(i).getPastValue() instanceof Array) {

                            // 1 случай - второе значение тоже является [complex value]
                            if (informationAboutChanges.get(i + 1).getPastValue() instanceof Map
                                || informationAboutChanges.get(i + 1).getPastValue() instanceof Iterable
                                || informationAboutChanges.get(i + 1).getPastValue() instanceof Array) {
                                // добавляем в лист строку про изменение с [complex value] на [complex value]
                                var change = "Property '" + informationAboutChanges.get(i).getKey()
                                    + "' was updated. From [complex value] to [complex value]";
                                changesPlain.add(change);
                                // увеличиваем счётчик на 2, так как обработали 2 значения
                                i = i + 2;
                            } else {
                                // 2 случай - второе значение не является вложенным
                                // но проверяем, является ли оно строкой
                                if (informationAboutChanges.get(i + 1).getPastValue() instanceof String) {
                                    // если да, то добавляем в лист строку с '' вокруг 2 значения
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From [complex value] to '"
                                        + informationAboutChanges.get(i + 1).getPastValue() + "'";
                                    changesPlain.add(change);
                                    // увеличиваем счётчик на 2
                                    i = i + 2;
                                } else {
                                    // если не строка, то добавляем строку об изменении без ''
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From [complex value] to "
                                        + informationAboutChanges.get(i + 1).getPastValue();
                                    changesPlain.add(change);
                                    // увеличиваем счётчик на 2
                                    i = i + 2;
                                }
                            }
                            // рассматриваем также случаи, в которых первое значение не является вложенным
                            // так же два случая
                        } else {
                            // 1 - первое - не комплексное, второе [complex value]
                            if (informationAboutChanges.get(i + 1).getPastValue() instanceof Map
                                || informationAboutChanges.get(i + 1).getPastValue() instanceof Iterable
                                || informationAboutChanges.get(i + 1).getPastValue() instanceof Array) {
                                // если первое значение - строка, то добавляем в лист строку с '' вокруг 1 значения
                                if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                        + "' to  [complex value]";
                                    changesPlain.add(change);
                                    // увеличиваем счётчик на 2
                                    i = i + 2;
                                } else {
                                    // если не строка, то добавляем строку об изменении без ''
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                        + " to [complex value]";
                                    changesPlain.add(change);
                                    // увеличиваем счётчик на 2
                                    i = i + 2;
                                }
                            } else {
                                // 2 - первое и второе значения не являются комплексными
                                // 1 и 2 значения - строки, значит вокруг каждого ставим ''
                                if (informationAboutChanges.get(i).getPastValue() instanceof String
                                    && informationAboutChanges.get(i + 1).getPastValue() instanceof String) {
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                        + "' to '" + informationAboutChanges.get(i + 1).getPastValue() + "'";
                                    changesPlain.add(change);
                                    i = i + 2;
                                } else if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From '" + informationAboutChanges.get(i).getPastValue()
                                        + "' to " + informationAboutChanges.get(i + 1).getPastValue();
                                    changesPlain.add(change);
                                    i = i + 2;
                                } else if (informationAboutChanges.get(i + 1).getPastValue() instanceof String) {
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                        + " to '" + informationAboutChanges.get(i + 1).getPastValue() + "'";
                                    changesPlain.add(change);
                                    i = i + 2;
                                } else {
                                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                                        + "' was updated. From " + informationAboutChanges.get(i).getPastValue()
                                        + " to " + informationAboutChanges.get(i + 1).getPastValue();
                                    changesPlain.add(change);
                                    i = i + 2;
                                }
                            }
                        }
                    } else {
                        // если "-", но соседние ключи не равны, значит элемент удалён
                        var change = "Property '" + informationAboutChanges.get(i).getKey() + "' was removed";
                        changesPlain.add(change);
                        i = i + 1;
                    }
                }
            } else {
                // и если "+", то значение было добавлено
                // проверяем [complex value] или нет
                if (informationAboutChanges.get(i).getPastValue() instanceof Map
                    || informationAboutChanges.get(i).getPastValue() instanceof Iterable
                    || informationAboutChanges.get(i).getPastValue() instanceof Array) {
                    var change = "Property '" + informationAboutChanges.get(i).getKey()
                        + "' was added with value: [complex value]";
                    changesPlain.add(change);
                    i = i + 1;
                } else {
                    // и если не [complex value], то делаем проверку на строку
                    if (informationAboutChanges.get(i).getPastValue() instanceof String) {
                        var change = "Property '" + informationAboutChanges.get(i).getKey()
                            + "' was added with value: '" + informationAboutChanges.get(i).getPastValue() + "'";
                        changesPlain.add(change);
                        i = i + 1;
                    } else {
                        var change = "Property '" + informationAboutChanges.get(i).getKey()
                            + "' was added with value: " + informationAboutChanges.get(i).getPastValue();
                        changesPlain.add(change);
                        i = i + 1;
                    }
                }
            }
        }

        var stringChangesPlain = String.join("\n", changesPlain);
        var result = new StringBuilder(stringChangesPlain);
        return result.toString();
    }

//        var result = new StringBuilder();
//
//        changesPlain.forEach((change) -> {
//            result.append(change);
//            result.append("\n");
//        });
//
//        return result.toString();
//    }
}

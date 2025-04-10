package hexlet.code;

import hexlet.code.parsers.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChangesGenerator {
    public static List<Change> getListOfChanges(Map<String, Object> mapFile1, Map<String, Object> mapFile2) {
        // получаем два сета ключей
        var keysFile1 = mapFile1.keySet();
        var keysFile2 = mapFile2.keySet();

        // делаем сет ключей из первой и второй мапы
        var keysFile = new HashSet<>(keysFile1);
        keysFile.addAll(keysFile2);

        // сортируем сет ключей в алфавитном порядке
        var keys = keysFile.stream()
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));

        // дальше по идее я реализую сравнение значений мап, сохраняя результат в виде списка с объектами нового класса
        var changes = new ArrayList<Change>();

        // есть 3 случая:
        for (var key : keys) {

            // 1. Ключ есть в обоих мапах - значение осталось прежним/изменилось
            if (keysFile1.contains(key) && keysFile2.contains(key)) {
                if (Objects.equals(mapFile1.get(key), (mapFile2.get(key)))) {
                    var change = new Change(Status.NOT_CHANGED, key, mapFile1.get(key), mapFile2.get(key));
                    changes.add(change);
                } else {
                    var change = new Change(Status.CHANGED, key, mapFile1.get(key), mapFile2.get(key));
                    changes.add(change);
                }
                continue;
            }

            if (keysFile1.contains(key)) {
                var changeMinus = new Change(Status.DELETED, key, mapFile1.get(key), "There is no value");
                changes.add(changeMinus);
                continue;
            }

            if (keysFile2.contains(key)) {
                var changePlus = new Change(Status.ADDED, key, "There is no value", mapFile2.get(key));
                changes.add(changePlus);
            }
        }
        return changes;
    }
}

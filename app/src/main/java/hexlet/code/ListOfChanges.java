package hexlet.code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListOfChanges {
    public static List<Change> getListOfChanges(Map<String, Object> mapFile1, Map<String, Object> mapFile2) {
        // получаем два сета ключей
        var keysFile1 = mapFile1.keySet();
        var keysFile2 = mapFile2.keySet();

        // делаем сет ключей из первой и второй мапы, но чтобы они не повторялись
        var keysFile = new HashSet<>(keysFile1);

        for (var key : keysFile2) {
            if (keysFile.contains(key)) {
                continue;
            }
            keysFile.add(key);
        }

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
                    var change = new Change(" ", key, mapFile1.get(key));
                    changes.add(change);
                } else {
                    var changeMinus = new Change("-", key, mapFile1.get(key));
                    var changePlus = new Change("+", key, mapFile2.get(key));
                    changes.add(changeMinus);
                    changes.add(changePlus);
                }

                // 2. Ключ есть только в первой мапе - значение удалено
            } else if (keysFile1.contains(key)) {
                var changeMinus = new Change("-", key, mapFile1.get(key));
                changes.add(changeMinus);

                // 3. Ключ есть только во второй мапе - значение добавлено
            } else if (keysFile2.contains(key)) {
                var changePlus = new Change("+", key, mapFile2.get(key));
                changes.add(changePlus);
            }
        }
        return changes;
    }
}

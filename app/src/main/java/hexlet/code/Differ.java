package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Differ {

    private static Path pathFile(String filePath) {
        return Paths.get(filePath) //?
                    .toAbsolutePath()
                    .normalize();
    }

    private static String readFile(String filePath) throws IOException {
        var path = pathFile(filePath);
        return Files.readString(path).trim();
    }

    private static Map<String, Object> parseFile(String filePath) throws IOException {
        var file = readFile(filePath);
        var mapper = new ObjectMapper();
        var typeRef = new TypeReference<HashMap<String, Object>>() { };
        return mapper.readValue(file, typeRef);
    }


    public static String generate(String filePath1, String filePath2) throws IOException {

        // получаем две мапы
        var mapFile1 = parseFile(filePath1);
        var mapFile2 = parseFile(filePath2);

        // получаем два сета ключей
        var keysFile1 = mapFile1.keySet();
        var keysFile2 = mapFile2.keySet();

        // делаем сет ключей из первой и второй мапы, но чтобы они не повторялись
        var keysFile = new HashSet<String>(keysFile1);

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
                if (mapFile1.get(key).equals(mapFile2.get(key))) {
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

        var result = new StringBuilder("{\n");
        changes.forEach((change) -> {
            result.append("  ");
            result.append(change.getChange());
            result.append(" ");
            result.append(change.getKey());
            result.append(": ");
            result.append(change.getPastValue());
            result.append("\n");
        });
        result.append("}");

        return result.toString();
    }
}

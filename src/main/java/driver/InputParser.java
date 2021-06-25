package driver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Beverage;
import entities.CoffeeMachine;
import entities.Ingredient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class InputParser {

    private static final String MACHINE = "machine";
    private static final String OUTLETS = "outlets";
    private static final String COUNT = "count_n";
    private static final String TOTAL_ITEMS_QUANTITY = "total_items_quantity";
    private static final String BEVERAGES = "beverages";

    public static CoffeeMachine parseFile(String filename) throws IOException {
        // reading file and loading json a string
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(classloader.getResource(filename)).getFile());
        String data = new String(Files.readAllBytes(file.toPath()));

        // loading the json string as map
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> json = gson.fromJson(data, mapType);
        Map<String, Object> dataMap = (Map<String, Object>) json.get(MACHINE);

        // creating beverages
        Map<String, Map<String, Double>> beverageMap = (Map<String, Map<String, Double>>) dataMap.get(BEVERAGES);
        List<Beverage> beverages = beverageMap.entrySet().stream()
                .map(e -> new Beverage(e.getKey(), new Ingredient(e.getValue())))
                .collect(Collectors.toList());

        // creating coffee machine
        Integer count = (((Map<String, Double>) dataMap.get(OUTLETS)).get(COUNT)).intValue();
        Ingredient ingredient = new Ingredient(((Map<String, Double>) dataMap.get(TOTAL_ITEMS_QUANTITY)));
        return new CoffeeMachine(count, ingredient, beverages);
    }

}



package entities;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Ingredient {
    Map<String, Double> ingredientMap;

    Double getQuantity(String ingredient) {
        return ingredientMap.getOrDefault(ingredient, 0D);
    }

    Set<String> getIngredients() {
        return ingredientMap.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    void addIngredient(String ingredient, Double quantity) {
        ingredientMap.putIfAbsent(ingredient, 0D);
        ingredientMap.put(ingredient, ingredientMap.get(ingredient) + quantity);
    }

    void removeIngredient(String ingredient, Double quantity) throws Exception {
        if (ingredientMap.containsKey(ingredient) && ingredientMap.get(ingredient) >= quantity) {
            ingredientMap.put(ingredient, ingredientMap.get(ingredient) - quantity);
        }
        else {
            throw new Exception("Not enough quantity");
        }
    }
}

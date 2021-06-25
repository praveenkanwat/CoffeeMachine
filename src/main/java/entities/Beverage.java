package entities;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Beverage {

    private String beverageName;
    private Ingredient beverageIngredient;
}

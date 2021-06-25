package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@AllArgsConstructor
@Getter
public class CoffeeMachine {

    private int outlets;
    private Ingredient remainingIngredient;
    private List<Beverage> beverages;

    public void execute() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(this.getOutlets());
        List<Future<String>> outputFutures = new ArrayList<>();

        //creating futures for all the beverages
        for (Beverage beverage: this.getBeverages()) {
            outputFutures.add(executorService.submit(() ->
            {
                // Runnable
                synchronized (this) {
                    List<String> unavailableBeverages = new ArrayList<>();
                    for(String ingredient: beverage.getBeverageIngredient().getIngredients()) {
                        if (this.getRemainingIngredient().getQuantity(ingredient) <
                                beverage.getBeverageIngredient().getQuantity(ingredient)) {
                            unavailableBeverages.add(ingredient);
                        }
                    }

                    //check if all the items are available
                    if (unavailableBeverages.size() > 0) {
                        return beverage.getBeverageName() + " cannot be prepared because unavailable/not sufficient items " + unavailableBeverages;
                    }
                    for(String ingredient: beverage.getBeverageIngredient().getIngredients()) {
                        this.getRemainingIngredient().removeIngredient(ingredient, beverage.getBeverageIngredient().getQuantity(ingredient));
                    }
                }

                // preparing beverage
                Thread.sleep(1000);

                return beverage.getBeverageName() + " is prepared";
            }));
        }

        // printing the results
        for (Future<String> future: outputFutures) {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }

}

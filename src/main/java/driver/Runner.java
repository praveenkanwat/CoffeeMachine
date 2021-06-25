package driver;

import entities.CoffeeMachine;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Runner {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        CoffeeMachine coffeeMachine = InputParser.parseFile("input.json");
        coffeeMachine.execute();
    }
}

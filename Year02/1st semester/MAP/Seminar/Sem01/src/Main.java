import container.Strategy;
import model.Task;
import runners.StrategyTaskRunner;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        if (args.length < 1) {
            System.out.println("Provide a strategy");
            return;
        }
        Strategy strategy;
        if (Objects.equals(args[0], "LIFO")) {
            strategy = Strategy.LIFO;
        } else {
            strategy = Strategy.FIFO;
        }
        TestRunner.run(strategy);
    }
}
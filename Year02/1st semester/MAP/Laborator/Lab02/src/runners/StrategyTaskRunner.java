package runners;

import container.Container;
import factory.TaskContainerFactory;
import model.Task;
import container.Strategy;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        if (!container.isEmpty()) {
            container.remove().execute();
        }
    }

    @Override
    public void executeAll() {
        while (!container.isEmpty()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTasks() {
        return !container.isEmpty();
    }
}

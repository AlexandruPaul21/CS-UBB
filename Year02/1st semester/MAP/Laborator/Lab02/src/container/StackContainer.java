package container;

import model.Task;
import utils.Constants;

public class StackContainer extends AbstractContainer {
    public StackContainer() {
        super();
    }

    @Override
    public Task remove() {
        --size;
        return tasks[size];
    }
}

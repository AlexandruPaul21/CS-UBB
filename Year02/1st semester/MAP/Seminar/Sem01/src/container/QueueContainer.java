package container;

import model.Task;
import utils.Constants;

import java.lang.reflect.Array;

public class QueueContainer extends AbstractContainer {
    public QueueContainer() {
        super();
    }

    @Override
    public Task remove() {
        Task ret = tasks[0];
        for (int i = 1; i < size; ++i) {
            tasks[i - 1] = tasks[i];
        }
        --size;
        return ret;
    }
}

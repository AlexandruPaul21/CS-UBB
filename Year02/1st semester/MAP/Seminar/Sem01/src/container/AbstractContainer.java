package container;

import model.Task;
import utils.Constants;

public abstract class AbstractContainer implements Container{
    protected Task[] tasks;
    protected int size;

    public AbstractContainer() {
        tasks = new Task[Constants.INITIAL_TASK_SIZE];
        size = 0;
    }

    @Override
    public abstract Task remove();

    public void add(Task task) {
        if (tasks.length == size) {
            Task[] t = new Task[tasks.length * 2];
            System.arraycopy(tasks,0,t,0,tasks.length);
            tasks = t;
        }
        tasks[size] = task;
        ++size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

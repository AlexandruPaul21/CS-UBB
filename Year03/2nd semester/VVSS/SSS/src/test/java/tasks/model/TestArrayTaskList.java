package tasks.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

public class TestArrayTaskList {

    Task task;

    @BeforeEach
    public void setUp() {
        task = Mockito.mock(Task.class);
        Mockito.when(task.getTask()).thenReturn(new Task("Title 1", new Date(1), new Date(2), 3));
        Mockito.when(task.getTask()).thenReturn(new Task("Title 1", new Date(1), new Date(2), 3));
    }

    @Test
    public void Test1_TestAdd() {
        ArrayTaskList list = new ArrayTaskList();
        list.add(task.getTask());
        list.add(task.getTask());

        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void Test2_TestRemove() {
        ArrayTaskList list = new ArrayTaskList();
        list.add(task.getTask());

        Assertions.assertEquals(1, list.size());

        list.remove(task.getTask());
        Assertions.assertEquals(0, list.size());
    }

}

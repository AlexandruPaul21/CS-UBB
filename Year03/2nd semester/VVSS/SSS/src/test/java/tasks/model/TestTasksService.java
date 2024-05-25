package tasks.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.services.TasksService;

import java.util.Date;
import java.util.List;

public class TestTasksService {

    Task task;

    ArrayTaskList taskList;

    TasksService tasksService;

    @BeforeEach
    public void setUp() {
        task = Mockito.mock(Task.class);
        taskList = Mockito.mock(ArrayTaskList.class);
        tasksService = new TasksService(taskList);

        Mockito.when(task.getTask()).thenReturn(new Task("Title", new Date(0), new Date(1), 2));
    }

    @Test
    public void Test1_getObservableList() {
        Mockito.when(taskList.getAll()).thenReturn(List.of(new Task("Title", new Date(0), new Date(1), 2)));
        Assertions.assertEquals(1, tasksService.getObservableList().size());
        Assertions.assertEquals(task.getTask(), tasksService.getObservableList().get(0));
    }

    @Test
    public void Test2_getIntervalInHours() {
        Mockito.when(task.getRepeatInterval()).thenReturn(1000);
        Assertions.assertEquals("00:16", tasksService.getIntervalInHours(task));
    }
}

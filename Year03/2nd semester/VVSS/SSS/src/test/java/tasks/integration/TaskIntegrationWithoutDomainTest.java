package tasks.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

public class TaskIntegrationWithoutDomainTest {
    Task task;

    ArrayTaskList taskList;

    TasksService tasksService;

    @BeforeEach
    public void setUp() {
        task = Mockito.mock(Task.class);
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);

        Mockito.when(task.getTask()).thenReturn(new Task("Title", new Date(0), new Date(1), 2));
    }

    @Test
    public void Test1_getObservableList() {
        taskList.add(task.getTask());
        Assertions.assertEquals(1, tasksService.getObservableList().size());
        Assertions.assertEquals(task.getTask(), tasksService.getObservableList().get(0));
    }

    @Test
    public void Test2_getIntervalInHours() {
        Mockito.when(task.getRepeatInterval()).thenReturn(1000);
        Assertions.assertEquals("00:16", tasksService.getIntervalInHours(task));
    }
}

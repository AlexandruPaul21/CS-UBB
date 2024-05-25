package tasks.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

public class TaskIntegrationWithDomainTest {
    Task task = new Task("a", new Date(0), new Date(1), 1000);

    ArrayTaskList taskList;

    TasksService tasksService;

    @BeforeEach
    public void setUp() {
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);
    }

    @Test
    public void Test1_getObservableList() {
        taskList.add(task.getTask());
        Assertions.assertEquals(1, tasksService.getObservableList().size());
        Assertions.assertEquals(task.getTask(), tasksService.getObservableList().get(0));
    }

    @Test
    public void Test2_getIntervalInHours() {
        Assertions.assertEquals("00:16", tasksService.getIntervalInHours(task));
    }
}

package tasks.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

class TasksOperationsTest {
    @Test
    public void Test1_taskIsEmpty() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(80, 04, 17, 12,30), new Date(80, 04, 26, 12,30));

        // Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getTitle(), new Task("Empty", new Date()).getTitle());
    }

    @Test
    public void F02_incoming_tasksIsNotEmptyAndHasDatesInInterval_returnExpectedTasks() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 15, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 20, 12,30)));
        tasks.add(new Task("TASK3", new Date(80, 04, 25, 12,30)));
        // end valid
        tasks.add(new Task("TASK4", new Date(80, 04, 30, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(80, 04, 17, 12,30), new Date(80, 04, 26, 12,30));

        // Assert
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void F02_incoming_tasksIsNotEmptyAndDoesntHaveTasksInInterval_returnOne() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 15, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 20, 12,30)));
        tasks.add(new Task("TASK3", new Date(80, 04, 25, 12,30)));
        // end valid
        tasks.add(new Task("TASK4", new Date(80, 04, 30, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(50, 04, 21, 12,30), new Date(50, 04, 22, 12,30));

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(result.get(0).getTitle(), new Task("Empty", new Date()).getTitle());
    }

    @Test
    public void F02_incoming_IntervalIsNull_returnOne() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 15, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 20, 12,30)));
        tasks.add(new Task("TASK3", new Date(80, 04, 25, 12,30)));
        // end valid
        tasks.add(new Task("TASK4", new Date(80, 04, 30, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(null, null);

        // Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getTitle(), new Task("Empty", new Date()).getTitle());
    }


    @Test
    public void F02_incoming_StartlIsNull_returnOne() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 15, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 20, 12,30)));
        tasks.add(new Task("TASK3", new Date(80, 04, 25, 12,30)));
        // end valid
        tasks.add(new Task("TASK4", new Date(80, 04, 30, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(null, new Date(50, 04, 21, 12,30));

        // Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getTitle(), new Task("Empty", new Date()).getTitle());
    }

    @Test
    public void F02_incoming_EndIsNull_returnOne() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 15, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 20, 12,30)));
        tasks.add(new Task("TASK3", new Date(80, 04, 25, 12,30)));
        // end valid
        tasks.add(new Task("TASK4", new Date(80, 04, 30, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(50, 04, 21, 12,30), null);

        // Assert
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getTitle(), new Task("Empty", new Date()).getTitle());
    }

    @Test
    public void F02_incoming_tasksHasOneElementAndHasDatesInInterval_returnExpectedTasks() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 20, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(80, 04, 17, 12,30), new Date(80, 04, 26, 12,30));

        // Assert
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void F02_incoming_tasksHasTwoElementsAndHasDatesInInterval_returnExpectedTasks() {
        // Arrange
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("TASK1", new Date(80, 04, 20, 12,30)));
        // valid
        tasks.add(new Task("TASK2", new Date(80, 04, 21, 12,30)));

        TasksOperations top = new TasksOperations(tasks);

        // Act
        ArrayList<Task> result = (ArrayList<Task>) top.incoming(new Date(80, 04, 17, 12,30), new Date(80, 04, 26, 12,30));

        // Assert
        Assertions.assertEquals(2, result.size());
    }
}

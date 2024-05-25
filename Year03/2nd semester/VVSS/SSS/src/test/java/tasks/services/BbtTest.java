package tasks.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.Date;

public class BbtTest {
    private TasksService service;

    @BeforeEach
    public void setUp() {
        ArrayTaskList taskList = new ArrayTaskList();
        service = new TasksService(taskList);
        service.clear();
        Task task = new Task("Test data", new Date(), new Date(), 1);
        service.saveTask(task);
    }

    // ECP
    @Test
    public void saveTaskWithValidTitle() {
        Task task = new Task("Sample", new Date(), new Date(), 1);

        Assertions.assertDoesNotThrow(() -> service.saveTask(task));
        Assertions.assertEquals(2, service.getTasks().size());
    }

    @Test
    public void saveTaskWithInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Task task = new Task("bzhwbkwuzbzgntpfcdkrcnniztzzvjhitfwxrtphqjrgemnxuauyjuanvuieeeadgacfvxjamgypqxtkvaeidiyphmpabbrhhqaehhjmphecaemfmnvnqyryuxtpzzuifpnjmqwepbprcvuyhzfywrctikgyrhtuuawxdzkxjbcbqczcbeyvegkffmrafpxzruxfxjpmxrdftpuxihxrnkthwfjytyrgugxufqhmkezttkdkznzubdiygtbwvdxrghkzeetayzjduayfmqrgihmknknvhvvdmixmhzftjctw", new Date(), new Date(), 1);
            service.saveTask(task);
        });
        Assertions.assertEquals(1, service.getTasks().size());
    }

    // BVA
    @Test
    public void saveTaskWithValidTitleBva() {
        Task task = new Task("T", new Date(), new Date(), 1);

        Assertions.assertDoesNotThrow(() -> {
            service.saveTask(task);
        });

        Assertions.assertEquals(2, service.getTasks().size());
        Assertions.assertEquals(service.getTasks().getTask(1), task);
    }

    @Test
    public void saveTaskWithInvalidTitleUpperBound() {
        Assertions.assertThrows(Exception.class, () -> {
            Task task = new Task("wLhIUEnLeKNcsrv7oFQqpr2gznG41jQdaWE5MYp1x2Z88PEuaCegF3dSr3ScuuwzzFfT5Fj6Zah8etTUXrRiUau9qfLJZxqwqMoaDv6TT6mG8V2V20BrOlYlV1w2A50sREW5YFRA5N4cY1UMF7NMZN5KTeSCWskqlz5gKbdFbQaMj6P2ZZ3xqHlBH4eDfbrpKD6RJB3i4rASxpJ3RSNNMX0rG4uYJrnuCWlkFiEtjzd3yhVrkIlIwIpw4U9oH6Yt", new Date(), new Date(), 1);
            service.saveTask(task);
        });
        Assertions.assertEquals(1, service.getTasks().size());
    }

}
